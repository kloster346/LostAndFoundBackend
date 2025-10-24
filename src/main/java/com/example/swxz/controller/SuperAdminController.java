package com.example.swxz.controller;

import com.example.swxz.common.Result;
import com.example.swxz.dto.LoginRequest;
import com.example.swxz.dto.LoginResponse;
import com.example.swxz.entity.SuperAdmin;
import com.example.swxz.service.SuperAdminService;
import com.example.swxz.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/super")
@Tag(name = "超级管理员接口", description = "超级管理员相关接口")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "超级管理员登录")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 保持原有验证逻辑不变
        SuperAdmin admin = superAdminService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (admin == null) {
            return Result.error("用户名或密码错误");
        }
        
        // 生成JWT token
        String token = jwtUtil.generateToken(admin.getId(), admin.getUsername(), "SUPER_ADMIN");
        
        // 返回统一的LoginResponse格式
        LoginResponse loginResponse = new LoginResponse(token, admin.getId(), admin.getUsername(), "SUPER_ADMIN");
        return Result.success(loginResponse);
    }
}