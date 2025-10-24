package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "待审核申请响应DTO")
public class PendingClaimResponse {
    @Schema(description = "申请记录ID")
    private Long id;
    
    @Schema(description = "原失物ID")
    private Long originalLostItemId;
    
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
    
    @Schema(description = "具体位置")
    private String specificLocation;
    
    @Schema(description = "图片路径")
    private String imageUrl;
    
    @Schema(description = "发布时间")
    private LocalDateTime publishTime;
    
    @Schema(description = "申请时间")
    private LocalDateTime applyTime;
    
    @Schema(description = "申请状态")
    private String claimStatus;
    
    @Schema(description = "申请人学号")
    private String claimerStudentId;
    
    @Schema(description = "申请人姓名")
    private String claimerName;
    
    @Schema(description = "申请人手机号")
    private String claimerPhone;
}