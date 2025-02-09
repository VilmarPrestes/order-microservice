package com.VilmarPrestes.order_microservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue userQueue() {
        return new Queue("userQueue", true); // true -> fila durável (não se perde ao reiniciar o RabbitMQ)
    }
}
