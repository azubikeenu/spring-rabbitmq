package com.azubike.ellpisis.exchange.fanout;

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

@RequiredArgsConstructor
@Configuration
@Slf4j
public class FanoutExchangeConfig {

    @Value("${fanout.exchange.name}")
    private String exchangeName;
    @Value("${fanout.queue.name.queue_one}")
    private String queueOne;
    @Value("${fanout.queue.name.queue_two}")
    private String queueTwo;

    private final AmqpAdmin amqpAdmin;


    Queue createDirectQueueOne() {
        return new Queue(queueOne, true, false, false);
    }

    Queue createDirectQueueTwo() {
        return new Queue(queueTwo, true, false, false);
    }

    FanoutExchange createFanoutExchange() {
        return new FanoutExchange(exchangeName, true, false);
    }

    Binding createBindingOne() {
        return BindingBuilder.bind(createDirectQueueOne()).to(createFanoutExchange());
    }

    Binding createBindingTwo() {
        return BindingBuilder.bind(createDirectQueueTwo()).to(createFanoutExchange());
    }

    @Bean
    public AmqpTemplate fanoutExchange(ConnectionFactory connectionFactory , MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(exchangeName);
        return rabbitTemplate;
    }

    @PostConstruct
    void init(){
        amqpAdmin.declareQueue(createDirectQueueOne());
        amqpAdmin.declareQueue(createDirectQueueTwo());
        amqpAdmin.declareExchange(createFanoutExchange());
        amqpAdmin.declareBinding(createBindingOne());
        amqpAdmin.declareBinding(createBindingTwo());
    }

}
