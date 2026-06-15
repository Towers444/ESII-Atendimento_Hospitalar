package com.atendimento_hospitalar.repository;

import com.atendimento_hospitalar.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends JpaRepository <Atendimento, Long> {
    // Métodos personalizados podem ser adicionados aqui futuramente, se necessário
} 