package com.example.swxz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("lost_items")
@Schema(description = "失物实体")
public class LostItem {
    @TableId(type = IdType.AUTO)
    @Schema(description = "失物ID")
    private Long id;
    
    @Schema(description = "物品名称")
    private String name;
    
    @Schema(description = "物品类型 1:书 2:包 3:卡 4:钥匙 5:手机 6:手表 7:笔 8:伞 9:耳机 10:其他")
    private Integer type;
    
    @Schema(description = "颜色 1:红色 2:浅红色 3:暗红色 4:绿色 5:浅绿色 6:暗绿色 7:蓝色 8:浅蓝色 9:深蓝色 10:黄色 11:橙色 12:紫色 13:粉红色 14:棕色 15:灰色 16:黑色 17:白色 18:其他")
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

    // 存储时只存文件名，读取时动态拼接域名
    public String getImageUrl() {
        return this.imageUrl;
    }
    
    @Schema(description = "发布到平台时间")
    private java.time.LocalDateTime publishTime;
    
    @Schema(description = "是否被领取 0:未领取 1:已领取")
    private Integer isClaimed;
    
    @Schema(description = "领取时间")
    private java.time.LocalDateTime claimTime;
    
    @Schema(description = "发布管理员ID")
    private Long adminId;
    
    @Schema(description = "领取人学号")
    private String claimerStudentId;
    
    @Schema(description = "领取人姓名")
    private String claimerName;
    
    @Schema(description = "领取人手机号")
    private String claimerPhone;
    
    @Schema(description = "创建时间")
    private java.time.LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private java.time.LocalDateTime updateTime;
}