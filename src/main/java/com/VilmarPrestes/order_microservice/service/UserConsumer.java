package com.VilmarPrestes.order_microservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    @RabbitListener(queues = "userQueue")
    public void receiveMessage(String mensagem) {
        System.out.println("Mensagem recebida do RabbitMQ: " + mensagem);
    }
}
