package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "用户详情响应DTO")
public class UserDetailResponse {
    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "学院")
    private String college;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}