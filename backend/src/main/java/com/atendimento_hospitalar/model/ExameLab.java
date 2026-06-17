package com.atendimento_hospitalar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "exames_lab")
public class ExameLab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do exame é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A descrição do exame é obrigatória")
    @Column(nullable = false, length = 500)
    private String descricao;

    @NotNull(message = "O atendimento é obrigatório")
    @ManyToOne
    @JoinColumn(name = "atendimento_id", nullable = false)
    private Atendimento atendimento;

    // Construtor vazio exigido pelo JPA
    public ExameLab() {}

    //Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Atendimento getAtendimento() { return atendimento; }
    public void setAtendimento(Atendimento atendimento) { this.atendimento = atendimento; }
}