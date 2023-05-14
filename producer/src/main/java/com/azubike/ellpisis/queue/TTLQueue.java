package com.azubike.ellpisis.queue;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class TTLQueue {

    @Value("${ttl.queue.name}")
    private String queueName;

    private final AmqpAdmin amqpAdmin;

    Queue createQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put( "x-message-ttl" ,5000);
        return new Queue(queueName, true, false, false, args);
    }

    @Bean
    public AmqpTemplate defaultTTLQueue(ConnectionFactory connectionFactory , MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(queueName);
        return rabbitTemplate;
    }

    @PostConstruct
    void init(){
        amqpAdmin.declareQueue(createQueue());
    }
}
