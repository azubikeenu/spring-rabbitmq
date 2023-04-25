package com.azubike.ellpisis.controller;


import com.azubike.ellpisis.model.QueueObject;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/fanout")
public class FanoutController {
    private final AmqpTemplate fanoutExchange;

    @GetMapping("/")
    ResponseEntity<String> publishMessage(){
        fanoutExchange.convertAndSend(QueueObject.builder().type("Fanout").time(LocalDateTime.now()).build());
        return ResponseEntity.ok("Message successfully sent!!!");
    }

}
