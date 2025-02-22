package com.VilmarPrestes.order_microservice.model;


import jakarta.persistence.*; //ja add tudo da tabela - importante
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity  // Indica que esta classe é uma entidade do banco
@Table(name = "users") // Nome da tabela no banco
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id  // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento
    private Long id;

    @Column(nullable = false) // Define que este campo não pode ser nulo
    @NotBlank
    private String name;

    @Column(nullable = false, unique = true) // Define que o email é único
    @NotBlank
    private String email;

    private String password;

    private Integer birthday;


}
