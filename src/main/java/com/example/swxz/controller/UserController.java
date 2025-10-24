package com.example.swxz.controller;

import com.example.swxz.common.Result;
import com.example.swxz.dto.LoginRequest;
import com.example.swxz.dto.LoginResponse;
import com.example.swxz.dto.RegisterRequest;
import com.example.swxz.dto.RegisterResponse;
import com.example.swxz.entity.User;
import com.example.swxz.service.UserService;
import com.example.swxz.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Tag(name = "普通用户接口", description = "普通用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 保持原有验证逻辑不变
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            return Result.error("用户名或密码错误");
        }
        
        // 生成JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), "USER");
        
        // 返回统一的LoginResponse格式
        LoginResponse loginResponse = new LoginResponse(token, user.getId(), user.getUsername(), "USER");
        return Result.success(loginResponse);
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "获取用户信息")
    public Result<User> getUserProfile(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(user);
    }

    @PutMapping("/profile")
    @Operation(summary = "更新用户信息")
    public Result<User> updateUserProfile(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return Result.success(updatedUser);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        // 注册逻辑实现
        RegisterResponse response = userService.register(request);
        return Result.success(response);
    }

    @GetMapping("/check-username/{username}")
    @Operation(summary = "检查用户名是否可用")
    public Result<Boolean> checkUsername(@PathVariable String username) {
        // 用户名唯一性检查
        boolean exists = userService.isUsernameExists(username);
        return Result.success(!exists);
    }

    @GetMapping("/check-student-id/{studentId}")
    @Operation(summary = "检查学号是否可用")
    public Result<Boolean> checkStudentId(@PathVariable String studentId) {
        // 学号唯一性检查
        boolean exists = userService.isStudentIdExists(studentId);
        return Result.success(!exists);
    }
}