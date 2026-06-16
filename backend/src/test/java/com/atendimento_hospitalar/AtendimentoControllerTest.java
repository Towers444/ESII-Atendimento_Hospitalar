package com.atendimento_hospitalar;

import com.atendimento_hospitalar.controller.AtendimentoController;
import com.atendimento_hospitalar.model.Atendimento;
import com.atendimento_hospitalar.model.Profissional;
import com.atendimento_hospitalar.repository.AtendimentoRepository;
import com.atendimento_hospitalar.repository.ProfissionalRepository;

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

@WebMvcTest(AtendimentoController.class) 
public class AtendimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtendimentoRepository atendimentoRepository;

    @MockBean
    private ProfissionalRepository profissionalRepository;

    @Test
    public void deveCriarAtendimentoComSucesso() throws Exception {

        Profissional medico = new Profissional();
        medico.setId(1L);
        medico.setNome("Dr. João");
        medico.setCategoria("Médico");

        Atendimento atendimento = new Atendimento();
        atendimento.setId(1L);

        Mockito.when(profissionalRepository.findById(1L)).thenReturn(Optional.of(medico));

        Mockito.when(atendimentoRepository.save(any(Atendimento.class))).thenReturn(atendimento);

        String jsonRequest = """
            {
                "data":"2026-06-15",
                "horario":"14:00:00",
                "problemaTexto":"Dor de cabeça",
                "receitaSaude":"Remédio",
                "profissional":{
                    "id":1
                }
            }
        """;

        mockMvc.perform(post("/api/atendimentos").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isCreated());

    }

    @Test
    public void deveRejeitarReceitaIncompativel() throws Exception {

        Profissional medico = new Profissional();
        medico.setId(1L);
        medico.setNome("Dr. João");
        medico.setCategoria("Médico");

        Mockito.when(profissionalRepository.findById(1L)).thenReturn(Optional.of(medico));

        String jsonRequest = """
        {
            "data":"2026-06-15",
            "horario":"14:00:00",
            "problemaTexto":"Dor de cabeça",
            "receitaSaude":"Atividade Física",
            "profissional":{
                "id":1
            }
        }
        """;

        mockMvc.perform(post("/api/atendimentos").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isBadRequest());

    }

}
