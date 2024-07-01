package com.example.project.part.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RabbitMQSenderImplTest {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;
    private RabbitMQSenderImpl rabbitMQSender;

    @BeforeEach
    public void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        objectMapper = new ObjectMapper();
        rabbitMQSender = new RabbitMQSenderImpl(rabbitTemplate, objectMapper);
    }

    @Test
    public void testSend() throws JsonProcessingException {
        Object message = new TestMessage("test");

//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> rabbitMQSender.send(message));

        // Wait for the async operation to complete
//        future.join();
        rabbitMQSender.send(message);

        ArgumentCaptor<String> exchangeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(rabbitTemplate, times(1)).convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), messageCaptor.capture());

//        assertEquals("test-exchange", exchangeCaptor.getValue());
//        assertEquals("test-routing-key", routingKeyCaptor.getValue());
        assertEquals(objectMapper.writeValueAsString(message), messageCaptor.getValue());
    }

    @Test
    public void testSend_NullMessage() {
        assertThrows(NullPointerException.class, () -> {
            rabbitMQSender.send(null);
        });
    }

    private static class TestMessage {
        private String content;

        public TestMessage(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}