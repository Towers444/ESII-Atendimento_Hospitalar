package com.atendimento_hospitalar.repository;

import com.atendimento_hospitalar.model.ExameLab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameLabRepository extends JpaRepository<ExameLab, Long> {
}