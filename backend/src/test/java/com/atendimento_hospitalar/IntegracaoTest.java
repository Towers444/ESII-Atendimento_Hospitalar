package com.atendimento_hospitalar;

import com.atendimento_hospitalar.model.Profissional;
import com.atendimento_hospitalar.repository.ProfissionalRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfissionalRepository repository;

    @BeforeEach
    public void setup() {
        // Limpa o banco de dados antes de cada teste para evitar falsos positivos
        repository.deleteAll();
    }

    @Test
    public void deveSalvarProfissionalNoBancoDeDados() throws Exception {
        String jsonRequest = "{\"nome\":\"Carlos\",\"categoria\":\"Psicólogo\",\"endereco\":\"Rua A, 123\"}";

        // 1. Faz a requisição POST real para a API
        mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated());

        // 2. Vai diretamente no banco de dados verificar se foi salvo
        assertEquals(1, repository.count());
        
        Profissional salvo = repository.findAll().get(0);
        assertEquals("Carlos", salvo.getNome());
        assertEquals("Psicólogo", salvo.getCategoria());
    }
}