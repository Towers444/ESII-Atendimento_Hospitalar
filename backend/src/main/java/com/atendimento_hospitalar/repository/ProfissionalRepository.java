package com.atendimento_hospitalar.repository;

import com.atendimento_hospitalar.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    // Métodos personalizados podem ser adicionados aqui futuramente, se necessário
}