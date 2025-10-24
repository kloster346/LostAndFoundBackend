package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "失物搜索请求DTO")
public class LostItemSearchRequest {
    @Schema(description = "物品类型 1:书 2:包 3:卡 4:钥匙 5:手机 6:手表 7:笔 8:伞 9:耳机 10:其他")
    private Integer type;
    
    @Schema(description = "颜色 1:红色 2:浅红色 3:暗红色 4:绿色 5:浅绿色 6:暗绿色 7:蓝色 8:浅蓝色 9:深蓝色 10:黄色 11:橙色 12:紫色 13:粉红色 14:棕色 15:灰色 16:黑色 17:白色 18:其他")
    private Integer color;
    
    @Schema(description = "物品名称")
    private String name;
    
    @Schema(description = "楼栋编号")
    private Integer building;
    
    @Schema(description = "页码，默认1")
    private Integer pageNum = 1;
    
    @Schema(description = "每页数量，默认10")
    private Integer pageSize = 10;
}