package com.chat.service;
import com.chat.exception.AppException;
import com.chat.exception.ErrorCode;
import com.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        // 1. Tìm ông User của mình trong MongoDB
        com.chat.model.User mongoUser = userRepository.findByUsername(username);
        if(mongoUser == null){
            throw new AppException(ErrorCode.USER_EXISTED);
        }


        // 2. Chuyển sang ông User của Spring Security để trả về
        return org.springframework.security.core.userdetails.User
                .withUsername(mongoUser.getUsername())
                .password(mongoUser.getPassword())
                .authorities(mongoUser.getRole().name())
                .build();
    }
}