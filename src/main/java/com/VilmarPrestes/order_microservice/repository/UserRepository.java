package com.VilmarPrestes.order_microservice.repository;

import com.VilmarPrestes.order_microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // O JpaRepository fornece métodos como save(), findAll(), findById(), delete()
}
