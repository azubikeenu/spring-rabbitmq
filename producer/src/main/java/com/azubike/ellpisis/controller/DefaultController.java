package com.azubike.ellpisis.controller;

import com.azubike.ellpisis.model.QueueObject;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/default")
@RequiredArgsConstructor
public class DefaultController {

    private final AmqpTemplate defaultQueue;

    @GetMapping("/")
    public ResponseEntity<String> sendDefaultMessage(){
      var message =  new QueueObject("default", LocalDateTime.now());
      defaultQueue.convertAndSend(message);
      return ResponseEntity.ok("Message successfully sent");
    }

}
