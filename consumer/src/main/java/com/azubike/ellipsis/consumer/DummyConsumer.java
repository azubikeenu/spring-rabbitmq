package com.azubike.ellipsis.consumer;

import com.azubike.ellipsis.model.DummyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class DummyConsumer {
    @RabbitListener(queues = "q.dummy")
    public void listen(DummyMessage dummyMessage){
        log.info("Consuming message : {}" ,dummyMessage);
    }
}
