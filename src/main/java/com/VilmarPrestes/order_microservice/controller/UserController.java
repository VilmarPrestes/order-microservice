package com.VilmarPrestes.order_microservice.controller;

import com.VilmarPrestes.order_microservice.model.User;
import com.VilmarPrestes.order_microservice.repository.UserRepository;
import com.VilmarPrestes.order_microservice.service.UserProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProducer userProducer; // Injeção do RabbitMQ Producer

    @GetMapping
    public List<User> listarUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUser(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        try {
            User savedUser = userRepository.save(user);

            // Envia mensagem ao RabbitMQ
            userProducer.sendMessage("Novo usuário cadastrado: " + savedUser.getNome());

            return ResponseEntity.status(201).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUser(@PathVariable("id") Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> excluirTodosUsuarios() {
        userRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
