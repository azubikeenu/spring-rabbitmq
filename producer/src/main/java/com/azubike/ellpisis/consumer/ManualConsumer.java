package com.azubike.ellpisis.consumer;

import com.azubike.ellpisis.model.QueueObject;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ManualConsumer {
    private  final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;

    private int getMessageCount(String queueName){
        final Properties queueProperties = amqpAdmin.getQueueProperties(queueName);
        return (int) Objects.requireNonNull(queueProperties).get(RabbitAdmin.QUEUE_MESSAGE_COUNT);
    }

  public List<QueueObject> getMessages(String queueName){
       List<QueueObject> messages = new ArrayList<>();
        IntStream.rangeClosed(0, getMessageCount(queueName)).forEach(message -> {
            messages.add((QueueObject) rabbitTemplate.receiveAndConvert(queueName)) ;
        });
        return  messages;
    }


}
