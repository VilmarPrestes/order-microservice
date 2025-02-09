package com.VilmarPrestes.order_microservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;
    private static final String QUEUE_NAME = "userQueue";

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String mensagem) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, mensagem);
        System.out.println("Mensagem enviada para RabbitMQ: " + mensagem);
    }
}
