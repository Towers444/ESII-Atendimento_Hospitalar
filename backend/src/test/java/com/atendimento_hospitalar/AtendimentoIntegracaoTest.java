package com.atendimento_hospitalar;

import com.atendimento_hospitalar.model.Profissional;
import com.atendimento_hospitalar.repository.AtendimentoRepository;
import com.atendimento_hospitalar.repository.ExameLabRepository;
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
public class AtendimentoIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ExameLabRepository exameLabReposity;

    @BeforeEach
    public void setup() {
        exameLabReposity.deleteAll();
        atendimentoRepository.deleteAll();
        profissionalRepository.deleteAll();
    }

    @Test
    public void deveSalvarAtendimentoNoBancoDeDados() throws Exception {

        // Cria um profissional real no banco
        Profissional medico = new Profissional();

        medico.setNome("Dr. João");
        medico.setCategoria("Médico");
        medico.setEndereco("Rua A");

        medico = profissionalRepository.save(medico);

        // JSON enviado para a API
        String jsonRequest =
            """
            {
                "data":"2026-06-16",
                "horario":"14:00:00",
                "problemaTexto":"Dor de cabeça",
                "receitaSaude":"Remédio",
                "profissional":{
                    "id": %d
                }
            }
            """.formatted(medico.getId());

        // Faz o POST real
        mockMvc.perform(post("/api/atendimentos").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isCreated());

        // Verifica se realmente salvou no banco
        assertEquals(1, atendimentoRepository.count());
    }
}