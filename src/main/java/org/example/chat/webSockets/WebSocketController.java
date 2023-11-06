package org.example.chat.webSockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.chat.entityAndService.ChatRepository;
import org.example.security.UserDetailsImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitTemplate template;

    private final
    ChatRepository chatRepository;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, RabbitTemplate template, ChatRepository chatRepository) {
        this.messagingTemplate = messagingTemplate;
        this.template = template;
        this.chatRepository = chatRepository;
    }

    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
    public ChatMessageSms sendMessage(
            ChatMessageSms chatMessage
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        template.setExchange("common-exchange");
        template.convertAndSend(objectMapper.writeValueAsString(chatMessage));
        return chatMessage;
    }

    @MessageMapping("/chat.sendPhoto")
//    @SendTo("/topic/public")
    public ChatMessageSms sendPhoto(
            @Payload ChatMessageSms chatMessage
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        template.setExchange("common-exchange");
        template.convertAndSend(objectMapper.writeValueAsString(chatMessage));
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
    public ChatMessageSms addUser(
            ChatMessageSms chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getName());
        log.info(chatMessage);
        messagingTemplate.convertAndSend("chat-queue-add-user", chatMessage);
        return chatMessage;
    }

    @GetMapping("/pingpong")
    public String getPingPong(
            Model model, @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        model.addAttribute("name", userDetails.getUserEntity().getEmail());
        return "/pingpong";
    }

    @GetMapping("/getAllMessages")
    public ResponseEntity getAllMessages() {
        return ResponseEntity.ok().body(chatRepository.findAll());
    }
}
