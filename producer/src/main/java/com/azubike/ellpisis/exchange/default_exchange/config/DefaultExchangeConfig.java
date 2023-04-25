package com.azubike.ellpisis.exchange.default_exchange.config;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DefaultExchangeConfig {
    private final AmqpAdmin amqpAdmin;
    @Value("${default.queue.name}")
    private  String QUEUE_NAME;

    Queue createQueue(){
        // durable queue is one that outlives the broker
        // exclusive queue is one that only accessed by the current connection and are deleted when the connection closes
        // autoDelete means queue is deleted when all consumers have finished using it
        return new Queue(QUEUE_NAME , true , false , false );
    }

    @Bean
    public AmqpTemplate defaultQueue(ConnectionFactory connectionFactory , MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        // defaultExchange , queueName === routingKey
        rabbitTemplate.setRoutingKey(QUEUE_NAME);
        return rabbitTemplate;
    }

    @PostConstruct
    void init(){
       amqpAdmin.declareQueue(createQueue());
    }

}
