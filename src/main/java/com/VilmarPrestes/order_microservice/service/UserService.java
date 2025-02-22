package com.VilmarPrestes.order_microservice.service;
import com.VilmarPrestes.order_microservice.model.User;
import com.VilmarPrestes.order_microservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProducer userProducer; // RabbitMQ Producer Injection

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> createUsers(List<User> users) {
        List<User> savedUsers = userRepository.saveAll(users);

        // Send message to RabbitMQ
        savedUsers.forEach(user -> userProducer.sendMessage("New user registered: " + user.getName()));

        return savedUsers;
    }

    public Optional<User> updateUser(Long id, User data) {
        return userRepository.findById(id).map(user -> {
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            user.setBirthday(data.getBirthday());
            return userRepository.save(user);
        });
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }


}
