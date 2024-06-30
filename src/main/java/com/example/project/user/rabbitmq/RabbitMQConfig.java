package com.example.project.user.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String directExchangeName;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.queue.persistent}")
    private boolean persistentQueue;

    @Bean
    Queue queue() {
        return new Queue(queueName, persistentQueue);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName);
    }


    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing.key.#");
    }
}