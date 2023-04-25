package com.azubike.ellpisis.controller;

import com.azubike.ellpisis.model.QueueObject;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/direct")
public class DirectController {
    private final AmqpTemplate directExchange;
    @Value("${direct.exchange.routing_key.one}")
    private String routingKeyOne;
    @Value("${direct.exchange.routing_key.two}")
    private String routingKeyTwo;

    @GetMapping("/{key}")
    public ResponseEntity<String> publishMessage(@PathVariable String key){
        QueueObject queueObject = QueueObject.builder().type("Direct exchange").build();
        final String routingKey = Objects.equals(key, "routing-key-one") ? routingKeyOne : routingKeyTwo;
        directExchange.convertAndSend(routingKey, queueObject);
        return ResponseEntity.ok("Message successfully sent");
    }

}
