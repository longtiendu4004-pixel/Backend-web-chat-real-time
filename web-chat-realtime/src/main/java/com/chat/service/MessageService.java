package com.chat.service;

import com.chat.dto.MessageDTO;

import com.chat.model.ChatMessages;
import com.chat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository repository;

    public MessageDTO chatSingle(ChatMessages chat) {

        // 1. LƯU XUỐNG DB TRƯỚC!
        // MongoDB sẽ tự động tạo ID và trả về object đã có đầy đủ ID
        ChatMessages savedChat = repository.save(chat);

        // 2. MAP SANG DTO TỪ OBJECT VỪA LƯU (Để đảm bảo lấy được ID)
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(savedChat.getId());
        messageDTO.setContent(savedChat.getContent());
        messageDTO.setSenderId(savedChat.getSenderId());
        messageDTO.setReceiverId(savedChat.getReceiverId());
        messageDTO.setDeleted(savedChat.isDeleted());
        messageDTO.setType(savedChat.getType());
        messageDTO.setTimestamp(savedChat.getTimestamp());

        // 3. GỬI CHO NGƯỜI NHẬN
        if (messageDTO.getReceiverId() != null) {
            messagingTemplate.convertAndSend("/topic/user/" + messageDTO.getReceiverId(), messageDTO);
        }

        // 4. ECHO: GỬI TRẢ LẠI CHO NGƯỜI GỬI
        if (messageDTO.getSenderId() != null && !messageDTO.getSenderId().equals(messageDTO.getReceiverId())) {
            messagingTemplate.convertAndSend("/topic/user/" + messageDTO.getSenderId(), messageDTO);
        }

        return messageDTO;
    }

}