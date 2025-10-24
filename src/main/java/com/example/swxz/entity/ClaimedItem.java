package com.example.swxz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("claimed_items")
@Schema(description = "已领取失物")
public class ClaimedItem {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    private Long id;
    
    @Schema(description = "原失物ID")
    private Long originalLostItemId;
    
    @Schema(description = "物品名称")
    private String name;
    
    @Schema(description = "物品类型")
    private String type;
    
    @Schema(description = "颜色")
    private String color;
    
    @Schema(description = "描述")
    private String description;
    
    @Schema(description = "捡到地点")
    private String foundLocation;
    
    @Schema(description = "楼栋")
    private Integer building;
    
    @Schema(description = "具体位置")
    private String specificLocation;
    
    @Schema(description = "图片URL")
    private String imageUrl;
    
    @Schema(description = "发布时间")
    private LocalDateTime publishTime;
    
    @Schema(description = "领取时间")
    private LocalDateTime claimTime;
    
    @Schema(description = "管理员ID")
    private Long adminId;
    
    @Schema(description = "领取人学号")
    private String claimerStudentId;
    
    @Schema(description = "领取人姓名")
    private String claimerName;
    
    @Schema(description = "领取人电话")
    private String claimerPhone;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "申领状态")
    private String claimStatus;
    
    @Schema(description = "申请时间")
    private LocalDateTime applyTime;
    
    public enum ClaimStatus {
        PENDING,    // 待审核
        APPROVED,   // 已通过
        REJECTED    // 已拒绝
    }
}