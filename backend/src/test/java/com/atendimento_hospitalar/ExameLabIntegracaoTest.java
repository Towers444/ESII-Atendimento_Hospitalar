package com.atendimento_hospitalar;

import com.atendimento_hospitalar.model.Atendimento;
import com.atendimento_hospitalar.model.ExameLab;
import com.atendimento_hospitalar.model.Profissional;
import com.atendimento_hospitalar.repository.AtendimentoRepository;
import com.atendimento_hospitalar.repository.ExameLabRepository;
import com.atendimento_hospitalar.repository.ProfissionalRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExameLabIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExameLabRepository exameLabRepository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @BeforeEach
    public void setup() {
        exameLabRepository.deleteAll();
        atendimentoRepository.deleteAll();
        profissionalRepository.deleteAll();
    }

    @Test
    public void deveSalvarExameNoBancoDeDados() throws Exception {

        // Cria profissional
        Profissional profissional = new Profissional();
        profissional.setNome("Dr. João");
        profissional.setCategoria("Médico");
        profissional.setTelefone("31999999999");
        profissional.setEndereco("Rua A");

        profissional = profissionalRepository.save(profissional);

        // Cria atendimento
        Atendimento atendimento = new Atendimento();
        atendimento.setData(LocalDate.of(2026, 6, 15));
        atendimento.setHorario(LocalTime.of(14, 0));
        atendimento.setProblemaTexto("Dor de cabeça");
        atendimento.setReceitaSaude("Remédio");
        atendimento.setProfissional(profissional);

        atendimento = atendimentoRepository.save(atendimento);

        // JSON enviado para a API
        String jsonRequest = """
        {
            "nome":"Hemograma",
            "descricao":"Exame de sangue completo",
            "atendimento":{
                "id":%d
            }
        }
        """.formatted(atendimento.getId());

        // Chamada real da API
        mockMvc.perform(post("/api/exames").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isCreated());

        // Verifica persistência no banco
        assertEquals(1, exameLabRepository.count());

        ExameLab exameSalvo = exameLabRepository.findAll().get(0);

        assertEquals("Hemograma", exameSalvo.getNome());
        assertEquals("Exame de sangue completo", exameSalvo.getDescricao());
        assertEquals(atendimento.getId(), exameSalvo.getAtendimento().getId());
    }
}