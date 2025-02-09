package com.VilmarPrestes.order_microservice.controller;

import com.VilmarPrestes.order_microservice.model.User;
import com.VilmarPrestes.order_microservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController  // Define que esta classe é um controlador REST
@RequestMapping("/users") // Define o endpoint base
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Método para listar todos os usuários
    @GetMapping
    public List<User> listarUsers() {
        return userRepository.findAll();
    }

    // Método para buscar um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUser(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para criar um novo usuário
    @PostMapping
    public ResponseEntity<User> criarUser(@RequestBody User user) {
        try {
            User novoUser = userRepository.save(user);
            return ResponseEntity.ok(novoUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Método para atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<User> atualizarUser(@PathVariable("id") Long id, @RequestBody User dados) {
        Optional<User> userExistente = userRepository.findById(id);
        if (userExistente.isPresent()) {
            User user = userExistente.get();
            user.setNome(dados.getNome());
            user.setEmail(dados.getEmail());
            return ResponseEntity.ok(userRepository.save(user));
        }
        return ResponseEntity.notFound().build();
    }

    // Método para excluir um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUser(@PathVariable("id") Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método para excluir todos os usuários
    @DeleteMapping
    public ResponseEntity<Void> excluirTodosUsuarios() {
        userRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
