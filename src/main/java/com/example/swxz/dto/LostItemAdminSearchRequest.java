package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "失物管理员搜索请求")
public class LostItemAdminSearchRequest {
    @Schema(description = "页码，从1开始")
    private Integer pageNum;

    @Schema(description = "每页大小")
    private Integer pageSize;

    @Schema(description = "用户名，模糊匹配")
    private String username;

    @Schema(description = "学院，模糊匹配")
    private String college;

    @Schema(description = "办公室位置，模糊匹配")
    private String officeLocation;

    @Schema(description = "手机号，模糊匹配")
    private String phone;

    @Schema(description = "排序字段，默认create_time")
    private String sortBy;

    @Schema(description = "排序方式，asc或desc，默认desc")
    private String sortOrder;
}