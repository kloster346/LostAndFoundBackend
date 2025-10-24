package com.example.swxz.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegisterResponse {
    private Long id;
    private String studentId;
    private String username;
    private String college;
    private String phone;
    private LocalDateTime createTime;
    private String message;
}