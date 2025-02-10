package com.VilmarPrestes.order_microservice.model;


import jakarta.persistence.*; //ja add tudo da tabela - importante
import jakarta.validation.constraints.NotBlank;

@Entity  // Indica que esta classe é uma entidade do banco
@Table(name = "users") // Nome da tabela no banco
public class User {
    @Id  // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento
    private Long id;

    @Column(nullable = false) // Define que este campo não pode ser nulo
    @NotBlank
    private String nome;

    @Column(nullable = false, unique = true) // Define que o email é único
    @NotBlank
    private String email;

    // Construtor padrão
    public User() {}

    // Construtor com parâmetros
    public User(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
