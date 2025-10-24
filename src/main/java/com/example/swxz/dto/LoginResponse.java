package com.example.swxz.dto;

/**
 * 统一登录响应DTO
 * 用于返回JWT认证后的用户信息
 */
public class LoginResponse {
    
    /**
     * JWT token
     */
    private String token;
    
    /**
     * Token类型，固定为"Bearer"
     */
    private String type = "Bearer";
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 用户角色
     */
    private String role;
    
    // 构造函数
    public LoginResponse() {}
    
    public LoginResponse(String token, Long id, String username, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }
    
    // Getter和Setter方法
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + (token != null ? "[PROTECTED]" : null) + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}