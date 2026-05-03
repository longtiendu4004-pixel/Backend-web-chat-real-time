package com.chat.controller;

import com.chat.dto.ApiResponse;
import com.chat.dto.LoginRequest;
import com.chat.dto.SignupRequest;
import com.chat.dto.UserDTO;
import com.chat.model.User;
import com.chat.repository.UserRepository;
import com.chat.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(value = "*")
public class AuthController {

    @Autowired
    private AuthService authService;


    // --- ĐĂNG KÝ ---
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDTO>> signup(@RequestBody SignupRequest request) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        UserDTO dto = authService.registerUser(request);
        response.setCode(1000);
        response.setData(dto);
        return ResponseEntity.ok().body(response);


    }

    // --- ĐĂNG NHẬP ---
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginRequest request,
                                                   HttpServletRequest httpRequest,
                                                   HttpServletResponse httpResponse) {

        authService.login(request, httpRequest, httpResponse);
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("Đăng nhập thành công");
        return ResponseEntity.ok().body(response);
    }

    // --- ĐĂNG XUẤT ---
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        authService.logout(request);
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("Đã đăng xuất thành công!");
        return ResponseEntity.ok().body(response);
    }


}