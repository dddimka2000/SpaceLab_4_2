package org.example.chat;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RabbitMQSender {

    private final AmqpTemplate amqpTemplate;

    public RabbitMQSender(@Qualifier("rabbitTemplateSendMessage")AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage(String message) {
        log.info("RabbitMQSender"+message);
        amqpTemplate.convertAndSend("topic-exchange", "chat.sendMessage", message);
    }
}