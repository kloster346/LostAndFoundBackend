package com.example.swxz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("lost_item_admins")
@Schema(description = "失物管理员实体")
public class LostItemAdmin {
    @TableId(type = IdType.AUTO)
    @Schema(description = "管理员ID")
    private Long id;
    
    @Schema(description = "账号名")
    private String username;
    
    @Schema(description = "密码")
    private String password;
    
    @Schema(description = "学院")
    private String college;
    
    @Schema(description = "办公地点")
    private String officeLocation;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "创建时间")
    private java.time.LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private java.time.LocalDateTime updateTime;
}