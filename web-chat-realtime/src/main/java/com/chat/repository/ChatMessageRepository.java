package com.chat.repository;


import com.chat.model.ChatMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessages, String> {

    // Câu lệnh này tìm: (sender = A VÀ receiver = B) HOẶC (sender = B VÀ receiver = A)
    @Query("{ $or: [ { 'senderId': ?0, 'receiverId': ?1 }, { 'senderId': ?1, 'receiverId': ?0 } ] }")
    List<ChatMessages> findChatHistory(String user1, String user2);


    // Lấy tất cả tin nhắn mình gửi hoặc nhận, sắp xếp theo thời gian giảm dần (mới nhất lên đầu)
    List<ChatMessages> findBySenderIdOrReceiverId(String senderId, String receiverId, Sort sort);
}
