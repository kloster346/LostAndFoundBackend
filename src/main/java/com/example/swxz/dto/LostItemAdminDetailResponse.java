package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "失物管理员详情响应DTO")
public class LostItemAdminDetailResponse {
    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "学院")
    private String college;

    @Schema(description = "办公室位置")
    private String officeLocation;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}