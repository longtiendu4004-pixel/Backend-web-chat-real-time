package com.chat.service;

import com.chat.dto.ApiResponse;
import com.chat.dto.LoginRequest;
import com.chat.dto.SignupRequest;
import com.chat.dto.UserDTO;
import com.chat.exception.AppException;
import com.chat.exception.ErrorCode;
import com.chat.model.User;
import com.chat.model.Role;
import com.chat.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    public UserDTO registerUser(SignupRequest request) {
        // 1. Kiểm tra xem username đã tồn tại chưa
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        // 2. kiểm tra password
        if(request.getPassword().length() <= 3){
            throw new AppException(ErrorCode.PASSWORD_WEAK);
        }

        // 3. Tạo User mới và mã hóa mật khẩu
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.USER); // Mặc định là User thường


        // 4. Lưu xuống MongoDB
        userRepository.save(newUser);

        //return
        UserDTO dto = new UserDTO();
        dto.setId(newUser.getId());
        dto.setUsername(newUser.getUsername());
        dto.setRole(newUser.getRole());
        dto.setDeleted(newUser.isDeleted());
        dto.setAvatarUrl(newUser.getAvatarUrl());
        return dto;
    }


    public void login(LoginRequest request,
                      HttpServletRequest httpRequest,
                      HttpServletResponse httpResponse){
        // 1, lấy UserName và PassWord chưa băm
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        // 2, Tiến hành kiểm tra xác thực
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        //3, Khởi tạo session gắn với trình duyệt
        securityContextRepository.saveContext(context, httpRequest, httpResponse);
    }
    public void logout(HttpServletRequest request) {
        // Hủy session
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
    }


}