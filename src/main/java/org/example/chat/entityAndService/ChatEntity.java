package org.example.chat.entityAndService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.chat.webSockets.MessageType;
import org.example.entity.BuilderObject;
import org.example.entity.UserEntity;

import java.util.Date;

@Entity
@Data
public class ChatEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String content;
    private MessageType messageType;
    @Lob
    @Column(length = 1048576)
    private byte[] photoData;
    private String userEmail;
    private Date timestamp;
}
