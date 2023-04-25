package com.azubike.ellpisis.exchange.direct;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DirectExchangeConfig {

    @Value("${direct.exchange.name}")
    private String exchangeName;
    @Value("${direct.queue.name.queue_one}")
    private String queueOne;
    @Value("${direct.queue.name.queue_two}")
    private String queueTwo;
    @Value("${direct.exchange.routing_key.one}")
    private String routingKeyOne;
    @Value("${direct.exchange.routing_key.two}")
    private String routingKeyTwo;

    private final AmqpAdmin amqpAdmin;

    Queue createDirectQueueOne() {
        return new Queue(queueOne, true, false, false);
    }

    Queue createDirectQueueTwo() {
        return new Queue(queueTwo, true, false, false);
    }

    DirectExchange createDirectExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    Binding createBindingOne() {
        return BindingBuilder.bind(createDirectQueueOne()).to(createDirectExchange()).with(routingKeyOne);
    }

    Binding createBindingTwo() {
        return BindingBuilder.bind(createDirectQueueTwo()).to(createDirectExchange()).with(routingKeyTwo);
    }

    @Bean
    public AmqpTemplate directExchange(ConnectionFactory connectionFactory , MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(exchangeName);
        return rabbitTemplate;
    }

    @PostConstruct
    void init(){
        amqpAdmin.declareQueue(createDirectQueueOne());
        amqpAdmin.declareQueue(createDirectQueueTwo());
        amqpAdmin.declareExchange(createDirectExchange());
        amqpAdmin.declareBinding(createBindingOne());
        amqpAdmin.declareBinding(createBindingTwo());
    }



}
