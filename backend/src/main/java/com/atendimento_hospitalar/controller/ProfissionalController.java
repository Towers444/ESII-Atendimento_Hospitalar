package com.atendimento_hospitalar.controller;

import com.atendimento_hospitalar.model.Profissional;
import com.atendimento_hospitalar.repository.ProfissionalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profissionais")
@CrossOrigin(origins = "*") // Permite que o frontend React se conecte sem erro de CORS
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository repository;

    // CRIAR (POST)
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Profissional profissional) {
        try {
            // A validação da categoria acontece automaticamente no setCategoria
            Profissional salvo = repository.save(profissional);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // LER TODOS (GET)
    @GetMapping
    public ResponseEntity<List<Profissional>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    // LER POR ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscar(@PathVariable Long id) {
        Optional<Profissional> profissional = repository.findById(id);
        if (profissional.isPresent()) {
            return ResponseEntity.ok(profissional.get());
        }
        return ResponseEntity.notFound().build();
    }

    // ATUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Profissional dadosAtualizados) {
        return repository.findById(id).map(profissional -> {
            try {
                profissional.setNome(dadosAtualizados.getNome());
                profissional.setTelefone(dadosAtualizados.getTelefone());
                profissional.setEndereco(dadosAtualizados.getEndereco());
                profissional.setCategoria(dadosAtualizados.getCategoria());
                
                Profissional atualizado = repository.save(profissional);
                return ResponseEntity.ok(atualizado);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}