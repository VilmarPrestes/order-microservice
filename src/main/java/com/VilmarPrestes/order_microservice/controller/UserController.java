package com.VilmarPrestes.order_microservice.controller;

import com.VilmarPrestes.order_microservice.model.User;
import com.VilmarPrestes.order_microservice.repository.UserRepository;
import com.VilmarPrestes.order_microservice.service.UserProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserProducer userProducer; // RabbitMQ Producer Injection

    @GetMapping
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<List<User>> createUser(@RequestBody @Valid List<User> users) {
        try {
            List<User> savedUsers = userRepository.saveAll(users);

            // Send message to RabbitMQ
            savedUsers.forEach(user -> userProducer.sendMessage("New user registered: " + user.getName()));

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User data) {
        Optional<User> userExists = userRepository.findById(id);
        if (userExists.isPresent()) {
            User user = userExists.get();
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            user.setBirthday(data.getBirthday());
            return ResponseEntity.ok(userRepository.save(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
