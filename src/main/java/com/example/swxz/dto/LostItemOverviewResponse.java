package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "超管视图-失物概览响应")
public class LostItemOverviewResponse {
    @Schema(description = "失物ID")
    private Long id;

    @Schema(description = "物品名称")
    private String name;

    @Schema(description = "物品类型")
    private Integer type;

    @Schema(description = "颜色")
    private Integer color;

    @Schema(description = "物品描述")
    private String description;

    @Schema(description = "捡到地点")
    private String foundLocation;

    @Schema(description = "楼栋编号")
    private Integer building;

    @Schema(description = "具体位置（如房间号）")
    private String specificLocation;

    @Schema(description = "图片路径")
    private String imageUrl;

    @Schema(description = "发布到平台时间")
    private LocalDateTime publishTime;

    @Schema(description = "是否被领取 0:未领取 1:已领取")
    private Integer isClaimed;

    @Schema(description = "领取时间")
    private LocalDateTime claimTime;

    @Schema(description = "发布管理员ID")
    private Long adminId;

    @Schema(description = "发布管理员用户名")
    private String adminUsername;

    @Schema(description = "领取人学号")
    private String claimerStudentId;

    @Schema(description = "领取人姓名")
    private String claimerName;

    @Schema(description = "领取人手机号")
    private String claimerPhone;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}