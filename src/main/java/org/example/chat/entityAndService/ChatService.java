package org.example.chat.entityAndService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    final
    ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public  void save(ChatEntity chatEntity){
        chatRepository.save(chatEntity);
    }
}
