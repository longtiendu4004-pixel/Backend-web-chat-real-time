package com.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Data
@Document(collection = "messages")
public class ChatMessages {
    @Id
    private String id;
    private String senderId;
    private String content;
    private Instant timestamp = Instant.now();

    @Field(targetType = FieldType.STRING)
    private MessageType type = MessageType.TEXT; // "TEXT", "IMAGE", "FILE"

    private boolean isDeleted;

    private String receiverId;


    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }


    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }



    public String getContent() {
        return content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public MessageType getType() {
        return type;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
