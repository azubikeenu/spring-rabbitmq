package com.azubike.ellpisis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.addresses}")
    private String addresses ;
    @Value("${spring.rabbitmq.username}")
    private String username ;
    @Value("${spring.rabbitmq.password}")
    private String  password ;

    @Bean
    public MessageConverter messageConverter(){
        ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
        return new Jackson2JsonMessageConverter(objectMapper);
    }


    // Creating a rabbitMQ listener

    @Bean
    SimpleRabbitListenerContainerFactory createListener (){
        final SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory());
        simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(10);
        // this will start with 5 consumers
        simpleRabbitListenerContainerFactory.setConcurrentConsumers(5);
        simpleRabbitListenerContainerFactory.setAutoStartup(true);
        simpleRabbitListenerContainerFactory.setMessageConverter(messageConverter());
        // this prevents requeue of message on exception
        simpleRabbitListenerContainerFactory.setDefaultRequeueRejected(false);
        return simpleRabbitListenerContainerFactory;
    }

    @Bean
    ConnectionFactory connectionFactory (){
        AbstractConnectionFactory connectionFactory =new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

}
