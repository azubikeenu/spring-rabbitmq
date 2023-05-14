package com.azubike.ellpisis.consumer;


import com.azubike.ellpisis.model.QueueObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/consume")
@RequiredArgsConstructor
public class ConsumerController {
    private final ManualConsumer manualConsumer;

    @GetMapping("/{queueName}")
    ResponseEntity<List<QueueObject>> getMessages(@PathVariable("queueName") String queueName){
        final List<QueueObject> messages = manualConsumer.getMessages(queueName);
        return ResponseEntity.ok(messages);
    }

}
