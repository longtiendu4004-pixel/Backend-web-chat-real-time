package com.chat.controller;


import com.chat.dto.ApiResponse;
import com.chat.dto.RecentChatDTO;
import com.chat.dto.UserDTO;
import com.chat.model.ChatMessages;
import com.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;



    @GetMapping("/api/messages/history")
    public ResponseEntity<ApiResponse<List<ChatMessages>>> getChatHistory(
            @RequestParam String currentUser,
            @RequestParam String partnerUser) {

        List<ChatMessages> history = chatService.getChatHistory(currentUser, partnerUser);
        ApiResponse<List<ChatMessages>> response = new ApiResponse<>();
        response.setCode(1000);
        response.setData(history);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/api/search")
    public ResponseEntity<ApiResponse<List<UserDTO>>> searchUsers(@RequestParam String keyword, @RequestParam String currentUsername) {
        List<UserDTO> userDTOs = chatService.searchListUser(keyword, currentUsername);
        ApiResponse<List<UserDTO>> response = new ApiResponse<>();
        response.setCode(1000);
        response.setData(userDTOs);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/api/messages/recent")
    public ResponseEntity<ApiResponse<List<RecentChatDTO>>> getRecentChats(@RequestParam String currentUser) {


        List<RecentChatDTO> recentChats = chatService.getRecentChats(currentUser);


        ApiResponse<List<RecentChatDTO>> response = new ApiResponse<>();
        response.setCode(1000);
         response.setMessage("Lấy danh sách thành công");
        response.setData(recentChats);

        // 3. Trả về HTTP Status 200 OK
        return ResponseEntity.ok(response);
    }
}
