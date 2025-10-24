package com.example.swxz.controller;

import com.example.swxz.common.Result;
import com.example.swxz.dto.LoginRequest;
import com.example.swxz.dto.LoginResponse;
import com.example.swxz.entity.LostItemAdmin;
import com.example.swxz.service.LostItemAdminService;
import com.example.swxz.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/lost-item")
@Tag(name = "失物管理员接口", description = "失物管理员相关接口")
public class LostItemAdminController {

    @Autowired
    private LostItemAdminService lostItemAdminService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 保持原有验证逻辑不变
        LostItemAdmin admin = lostItemAdminService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (admin == null) {
            return Result.error("用户名或密码错误");
        }
        
        // 生成JWT token
        String token = jwtUtil.generateToken(admin.getId(), admin.getUsername(), "LOST_ITEM_ADMIN");
        
        // 返回统一的LoginResponse格式
        LoginResponse loginResponse = new LoginResponse(token, admin.getId(), admin.getUsername(), "LOST_ITEM_ADMIN");
        return Result.success(loginResponse);
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "获取管理员信息")
    public Result<LostItemAdmin> getAdminProfile(@PathVariable Long id) {
        LostItemAdmin admin = lostItemAdminService.getAdminById(id);
        if (admin == null) {
            return Result.error("管理员不存在");
        }
        return Result.success(admin);
    }
}