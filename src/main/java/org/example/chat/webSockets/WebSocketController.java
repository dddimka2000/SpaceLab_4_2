package org.example.chat.webSockets;

import lombok.extern.log4j.Log4j2;
import org.example.security.UserDetailsImpl;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;

@Controller
@Log4j2
public class WebSocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageSms sendMessage(
            ChatMessageSms chatMessage
    ) {
        log.info(chatMessage);
        return chatMessage;
    }
    @MessageMapping("/chat.sendPhoto")
    @SendTo("/topic/public")
    public ChatMessageSms sendPhoto(
             @Payload ChatMessageSms chatMessage
    ) {
        log.info(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageSms addUser(
            ChatMessageSms chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getName());
        log.info(chatMessage);
        return chatMessage;
    }

    @GetMapping("/pingpong")
    public String getPingPong(
//            Model model, @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
//        model.addAttribute("name", userDetails.getUserEntity().getEmail());
        return "/pingpong";
    }
}
