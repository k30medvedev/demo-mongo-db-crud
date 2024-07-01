package com.example.project.part.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class RabbitMQSenderImpl implements RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.exchange.name}")
    private String directExchangeName;

    public RabbitMQSenderImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(Object message) {
        CompletableFuture.runAsync(() -> {
            String json;
            try {
                json = objectMapper.writeValueAsString(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            Objects.requireNonNull(json, "message should exist");

            log.info("message send to the queue: " + json);
            rabbitTemplate.convertAndSend(directExchangeName, routingKey, json);
        });
    }

}
