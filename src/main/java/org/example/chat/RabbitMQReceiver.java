package org.example.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.example.chat.entityAndService.ChatEntity;
import org.example.chat.entityAndService.ChatRepository;
import org.example.chat.entityAndService.ChatService;
import org.example.chat.webSockets.ChatMessageSms;
import org.example.chat.webSockets.MessageType;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.security.UserDetailsImpl;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@EnableRabbit
@Component
@Log4j2
public class RabbitMQReceiver {
    private final
    ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    public RabbitMQReceiver(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @RabbitListener(queues = "chat-queue-send-message")
    public void processMyQueue(String string) {
        log.info(string);
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageSms chat = null;
        try {
            chat = objectMapper.readValue(string, ChatMessageSms.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("RabbitMQReceiver (sendMessage) ");
        if (chat!=null) {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setUserEmail(chat.getName());
            if (chat.getMessageType() == MessageType.CHAT) {
                chatEntity.setContent(chat.getContent());
            } else {
                chatEntity.setPhotoData(chat.getPhotoData());
            }
            chatEntity.setMessageType(chat.getMessageType());
            chatEntity.setTimestamp(chat.getTimestamp());
            chatService.save(chatEntity);
        } else {
            log.info("RabbitMQReceiver name empty");
        }
        messagingTemplate.convertAndSend("/topic/public", chat);
    }

//    @RabbitListener(queues = "chat-queue-send-photo")
//    public void receivePhotoFromRabbitMQ(String string) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ChatMessageSms chat = objectMapper.readValue(string, ChatMessageSms.class);
//        log.info("RabbitMQReceiver (sendPhoto) " + chat);
//        messagingTemplate.convertAndSend("/topic/public", chat);
//    }
//
//    @RabbitListener(queues = "chat-queue-add-user")
//    public void receiveAddUserFromRabbitMQ(String string) throws JsonProcessingException {
//        log.info("RabbitMQReceiver (addUser) " + string);
////        messagingTemplate.convertAndSend("/topic/public", chatMessage);
//    }

}
