package com.atendimento_hospitalar.controller;

import com.atendimento_hospitalar.model.Atendimento;
import com.atendimento_hospitalar.model.Profissional;
import com.atendimento_hospitalar.repository.AtendimentoRepository;
import com.atendimento_hospitalar.repository.ProfissionalRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/atendimentos")
@CrossOrigin(origins = "*")
public class AtendimentoController {

    
    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    private boolean receitaCompativel(String categoria, String receita) {

        switch (categoria) {
            case "Médico":
                return receita.equals("Remédio");

            case "Fisioterapeuta":
                return receita.equals("Atividade Física");

            case "Psicólogo":
                return receita.equals("Atividade Mental");
                
            default:
                return false;
        }

    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Atendimento atendimento) {

        try {

            Long profissionalId = atendimento.getProfissional().getId();
            Optional<Profissional> profissioalOpt = profissionalRepository.findById(profissionalId);

            if(profissioalOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Profissional não encontrado");
            }

            Profissional profissional = profissioalOpt.get();

            if(!receitaCompativel(profissional.getCategoria(), atendimento.getReceitaSaude())) {
                return ResponseEntity.badRequest().body("Receita incompatível com a categoria do profissional.");
            }

            atendimento.setProfissional(profissional);
            Atendimento salvo = atendimentoRepository.save(atendimento);

            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @GetMapping
    public ResponseEntity<List<Atendimento>> listar() {
        return ResponseEntity.ok(atendimentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atendimento> buscar(@PathVariable Long id) {

        Optional<Atendimento> atendimento = atendimentoRepository.findById(id);

        return atendimento.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar (@PathVariable Long id, @Valid @RequestBody Atendimento dadosAtualizados) {
            
        return atendimentoRepository.findById(id).map(atendimento -> {

            try {

                Long profissionalId = dadosAtualizados.getProfissional().getId();

                Optional<Profissional> profissionalOpt = profissionalRepository.findById(profissionalId);

                if(profissionalOpt.isEmpty()) {
                    return ResponseEntity.badRequest().body("Profissional não encontrado");
                }

                Profissional profissional = profissionalOpt.get(); 

                if(!receitaCompativel(profissional.getCategoria(), dadosAtualizados.getReceitaSaude())) {
                    return ResponseEntity.badRequest().body("Receita incompatível com categoria do profissional");
                }

                atendimento.setData(dadosAtualizados.getData());
                atendimento.setHorario(dadosAtualizados.getHorario());
                atendimento.setProblemaTexto(dadosAtualizados.getProblemaTexto());
                atendimento.setReceitaSaude(dadosAtualizados.getReceitaSaude());
                atendimento.setProfissional(profissional);

                Atendimento atualizado = atendimentoRepository.save(atendimento);

                return ResponseEntity.ok(atualizado);

            } catch (IllegalArgumentException e) {
                
                return ResponseEntity.badRequest().body(e.getMessage());

            }

        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if(atendimentoRepository.existsById(id)) {
            atendimentoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}