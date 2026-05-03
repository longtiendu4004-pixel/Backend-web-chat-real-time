package com.chat.service;


import com.chat.dto.RecentChatDTO;
import com.chat.dto.UserDTO;
import com.chat.model.ChatMessages;
import com.chat.model.User;
import com.chat.repository.ChatMessageRepository;
import com.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<ChatMessages> getChatHistory(String currentUser, String partnerUser) {

        List<ChatMessages> history = repository.findChatHistory(currentUser, partnerUser);
        return history;
    }

    public List<UserDTO> searchListUser(String keyword, String currentUsername){
        // Tìm kiếm trong DB
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(keyword);

        //Nếu có currentName
        User userCur = null;
        for(User u : users){
            if(u.getUsername().equals(currentUsername)){
                userCur = u;
            }
        }
        if(userCur != null){
            users.remove(userCur);
        }

        // Map sang DTO để không lộ Password
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setAvatarUrl(user.getAvatarUrl());
            return dto;
        }).collect(Collectors.toList());
        return userDTOs;
    }
    public List<RecentChatDTO> getRecentChats(String currentUser) {
        // 1. Lấy toàn bộ tin nhắn liên quan đến user này (sắp xếp giảm dần theo thời gian)
        List<ChatMessages> allMessages = repository.findBySenderIdOrReceiverId(
                currentUser, currentUser, Sort.by(Sort.Direction.DESC, "timestamp")
        );

        // 2. Dùng Map để lọc ra tin nhắn mới nhất của mỗi người
        Map<String, RecentChatDTO> recentMap = new LinkedHashMap<>();

        for (ChatMessages msg : allMessages) {
            // Xác định đối tác đang chat
            String partner = msg.getSenderId().equals(currentUser) ? msg.getReceiverId() : msg.getSenderId();

            // Nếu người này chưa có trong Map -> Đây là tin nhắn mới nhất -> Lưu lại
            if (!recentMap.containsKey(partner) && partner != null) {
                RecentChatDTO dto = new RecentChatDTO();
                dto.setUsername(partner);

                // Nếu mình là người gửi thì thêm chữ "Bạn: " cho giống Messenger thật
                String prefix = msg.getSenderId().equals(currentUser) ? "Bạn: " : "";
                dto.setLastMessage(prefix + msg.getContent());
                dto.setTimestamp(msg.getTimestamp());

                recentMap.put(partner, dto);
            }
        }

        // 3. Trả về một List sạch sẽ
        return new ArrayList<>(recentMap.values());
    }
}
