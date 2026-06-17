package com.atendimento_hospitalar;

import com.atendimento_hospitalar.controller.ExameLabController;
import com.atendimento_hospitalar.model.Atendimento;
import com.atendimento_hospitalar.model.ExameLab;
import com.atendimento_hospitalar.repository.AtendimentoRepository;
import com.atendimento_hospitalar.repository.ExameLabRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExameLabController.class)
public class ExameLabControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExameLabRepository exameLabRepository;

    @MockBean
    private AtendimentoRepository atendimentoRepository;

    @Test
    public void criarExameComSucesso() throws Exception {

        Atendimento atendimento = new Atendimento();
        atendimento.setId(1L);

        ExameLab exame = new ExameLab();
        exame.setId(1L);
        exame.setNome("Hemograma");
        exame.setDescricao("Exame de sangue");

        Mockito.when(atendimentoRepository.findById(1L)).thenReturn(Optional.of(atendimento));

        Mockito.when(exameLabRepository.save(any(ExameLab.class))).thenReturn(exame);

        String jsonRequest = """
        {
            "nome":"Hemograma",
            "descricao":"Exame de sangue",
            "atendimento":{
                "id":1
            }
        }
        """;

        mockMvc.perform(post("/api/exames").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isCreated());
    }

    @Test
    public void rejeitarAtendimentoInexistente() throws Exception {

        Mockito.when(atendimentoRepository.findById(999L)).thenReturn(Optional.empty());

        String jsonRequest = """
        {
            "nome":"Hemograma",
            "descricao":"Exame de sangue",
            "atendimento":{
                "id":999
            }
        }
        """;

        mockMvc.perform(post("/api/exames").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isBadRequest());
    }
}