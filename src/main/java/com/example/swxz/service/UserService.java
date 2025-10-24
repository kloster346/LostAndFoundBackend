package com.example.swxz.service;

import com.example.swxz.dto.RegisterRequest;
import com.example.swxz.dto.RegisterResponse;
import com.example.swxz.entity.User;

public interface UserService {
    User login(String username, String password);
    User getUserById(Long id);
    User updateUser(User user);
    
    // 新增方法
    RegisterResponse register(RegisterRequest request);
    boolean isUsernameExists(String username);
    boolean isStudentIdExists(String studentId);
}