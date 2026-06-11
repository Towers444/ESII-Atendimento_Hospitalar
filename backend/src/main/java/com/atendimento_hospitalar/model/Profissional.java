package com.atendimento_hospitalar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "profissionais")
public class Profissional {

    // Lista mockada de categorias permitidas
    @Transient // Esta anotação diz ao JPA para não salvar essa lista como uma coluna no banco
    public static final List<String> CATEGORIAS_PERMITIDAS = Arrays.asList("Psicólogo", "Fisioterapeuta", "Médico");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 20)
    private String telefone;

    @Column(length = 200)
    private String endereco;

    @NotBlank(message = "A categoria é obrigatória")
    @Column(nullable = false, length = 50)
    private String categoria;

    // Construtor vazio exigido pelo JPA
    public Profissional() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCategoria() { return categoria; }
    
    public void setCategoria(String categoria) {
        if (!CATEGORIAS_PERMITIDAS.contains(categoria)) {
            throw new IllegalArgumentException("Categoria inválida. Valores permitidos: " + CATEGORIAS_PERMITIDAS);
        }
        this.categoria = categoria;
    }
}