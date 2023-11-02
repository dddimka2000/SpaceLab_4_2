package org.example.chat;

import lombok.extern.log4j.Log4j2;
import org.example.chat.webSockets.ChatMessageSms;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RabbitMQReceiver {
    private final SimpMessagingTemplate messagingTemplate;

    public RabbitMQReceiver(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "chat-queue-send-message")
    public void receiveMessageFromRabbitMQ(ChatMessageSms chatMessage) {
        log.info("RabbitMQReceiver (sendMessage) " + chatMessage);
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    @RabbitListener(queues = "chat-queue-send-photo")
    public void receivePhotoFromRabbitMQ(ChatMessageSms chatMessage) {
        log.info("RabbitMQReceiver (sendPhoto) " + chatMessage);
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    @RabbitListener(queues = "chat-queue-add-user")
    public void receiveAddUserFromRabbitMQ(ChatMessageSms chatMessage) {
        log.info("RabbitMQReceiver (addUser) " + chatMessage);
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

}
