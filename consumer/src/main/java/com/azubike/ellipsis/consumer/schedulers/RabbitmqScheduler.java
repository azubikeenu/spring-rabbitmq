package com.azubike.ellipsis.consumer.schedulers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class RabbitmqScheduler {

    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;  // this returns all endpoints registered in rabbit listener

    //second, minute, hour, day of month, month, day(s) of week
    @Scheduled(cron = "0 9 19 * * * ")
    public void stopAll() {
        rabbitListenerEndpointRegistry.getListenerContainers().stream().parallel().forEach(messageListenerContainer -> {
            log.info("Stopping listener container {}", messageListenerContainer);
            messageListenerContainer.stop();
        });
    }

    @Scheduled(cron = "0 11 19 * * * ")
    public void startAll() {
        rabbitListenerEndpointRegistry.getListenerContainers().stream().parallel().forEach(messageListenerContainer -> {
            log.info("Starting listener container {}", messageListenerContainer);
            messageListenerContainer.start();
        });
    }


}
