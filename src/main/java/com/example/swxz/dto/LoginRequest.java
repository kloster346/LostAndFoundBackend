package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录请求DTO")
public class LoginRequest {
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "密码")
    private String password;
}