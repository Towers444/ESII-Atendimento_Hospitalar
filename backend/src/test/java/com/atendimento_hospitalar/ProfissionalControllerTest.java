package com.atendimento_hospitalar;

import com.atendimento_hospitalar.controller.ProfissionalController;
import com.atendimento_hospitalar.model.Profissional;
import com.atendimento_hospitalar.repository.ProfissionalRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfissionalController.class)
public class ProfissionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfissionalRepository repository;

    @Test
    public void deveCriarProfissionalComSucesso() throws Exception {
        // 1. Prepara o objeto fictício que o banco "retornaria"
        Profissional mockProfissional = new Profissional();
        mockProfissional.setId(1L);
        mockProfissional.setNome("Dra. Ana");
        mockProfissional.setCategoria("Médico");

        Mockito.when(repository.save(any(Profissional.class))).thenReturn(mockProfissional);

        // 2. Simula o JSON que o Frontend enviaria
        String jsonRequest = "{\"nome\":\"Dra. Ana\",\"categoria\":\"Médico\",\"telefone\":\"11988887777\"}";

        // 3. Executa a requisição POST e valida as respostas
        mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Dra. Ana"));
    }
}