package com.atendimento_hospitalar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "atendimentos")
public class Atendimento {

    // Lista mockada de receitas permitidas
    @Transient // Esta anotação diz ao JPA para não salvar essa lista como uma coluna no banco
    public static final List<String> RECEITAS_PERMITIDAS =
        Arrays.asList(
            "Remédio",
            "Atividade Física",
            "Atividade Mental"
        );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "O horário é obrigatório")
    @Column(nullable = false)
    private LocalTime horario;

    @NotBlank(message =  "A descrição do problema é obrigatória")
    @Column(nullable = false, length = 500)
    private String problemaTexto;

    @NotBlank(message = "A receita de saúde é obrigatória")
    @Column(nullable = false, length = 100)
    private String receitaSaude;

    @NotNull(message = "O profissional é obrigatório")
    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    // @OneToMany(mappedBy = "atendimento")
    // private List<Exame> exames;

    // Construtor vazio exigido pelo JPA
    public Atendimento() {}

    //Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) {this.data = data; }

    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) {this.horario = horario; }
    
    public String getProblemaTexto() { return problemaTexto; }
    public void setProblemaTexto(String problemaTexto) { this.problemaTexto = problemaTexto; }

    public String getReceitaSaude() { return receitaSaude; }
    public void setReceitaSaude(String receitaSaude) { 
        if(!RECEITAS_PERMITIDAS.contains(receitaSaude)) {
            throw new IllegalArgumentException("Receita de saúde inválida. Valores permitidos: " + RECEITAS_PERMITIDAS);
        }
        this.receitaSaude = receitaSaude;
    }

    public Profissional getProfissional() { return profissional; }
    public void setProfissional(Profissional profissional) { this.profissional = profissional; }

}