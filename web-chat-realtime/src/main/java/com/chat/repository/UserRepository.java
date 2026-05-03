package com.chat.repository;


import com.chat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Phục vụ luồng Login
    User findByUsername(String username);

    // Phục vụ luồng Signup (kiểm tra trùng lặp)
    boolean existsByUsername(String username);

    // search User
    List<User> findByUsernameContainingIgnoreCase(String keyword);
}
