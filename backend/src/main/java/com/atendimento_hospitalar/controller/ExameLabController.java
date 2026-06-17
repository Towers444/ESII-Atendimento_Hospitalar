package com.atendimento_hospitalar.controller;

import com.atendimento_hospitalar.model.Atendimento;
import com.atendimento_hospitalar.model.ExameLab;
import com.atendimento_hospitalar.repository.AtendimentoRepository;
import com.atendimento_hospitalar.repository.ExameLabRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exames")
@CrossOrigin(origins = "*")
public class ExameLabController {

    @Autowired
    private ExameLabRepository exameLabRepository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    // CRIAR (POST)
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody ExameLab exame) {

        Long atendimentoId = exame.getAtendimento().getId();

        Optional<Atendimento> atendimentoOpt = atendimentoRepository.findById(atendimentoId);

        if (atendimentoOpt.isEmpty()) { 
            return ResponseEntity.badRequest().body("Atendimento não encontrado");
        }

        exame.setAtendimento(atendimentoOpt.get());

        ExameLab salvo = exameLabRepository.save(exame);

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // LER TODOS (GET)
    @GetMapping
    public ResponseEntity<List<ExameLab>> listar() {
        return ResponseEntity.ok(exameLabRepository.findAll());
    }

    // LER POR ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ExameLab> buscar(@PathVariable Long id) {

        Optional<ExameLab> exame = exameLabRepository.findById(id);

        return exame.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ATUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody ExameLab dadosAtualizados) {

        return exameLabRepository.findById(id).map(exame -> {

            Long atendimentoId =dadosAtualizados.getAtendimento().getId();

            Optional<Atendimento> atendimentoOpt = atendimentoRepository.findById(atendimentoId);

            if (atendimentoOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Atendimento não encontrado");
            }

            exame.setNome(dadosAtualizados.getNome());
            exame.setDescricao(dadosAtualizados.getDescricao());
            exame.setAtendimento(atendimentoOpt.get());

            ExameLab atualizado = exameLabRepository.save(exame);

            return ResponseEntity.ok(atualizado);

        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (exameLabRepository.existsById(id)) {
            exameLabRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}