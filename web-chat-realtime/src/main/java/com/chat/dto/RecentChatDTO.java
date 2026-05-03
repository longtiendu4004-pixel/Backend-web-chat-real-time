package com.chat.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class RecentChatDTO {
    private String username;     // Tên người chat cùng
    private String lastMessage;  // Nội dung tin nhắn cuối
    private Instant timestamp;   // Thời gian gửi

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
