package com.azubike.ellpisis.consumer;

import com.azubike.ellpisis.model.QueueObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListenerConsumer {
    @RabbitListener(queues = "${default.queue.name}" , containerFactory = "createListener")
    public void receiveMessage(QueueObject queueObject){
     log.info("Consuming message : {}" ,queueObject);
    }
}
