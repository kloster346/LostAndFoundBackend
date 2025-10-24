package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "领取失物请求DTO")
public class ClaimRequest {
    @Schema(description = "失物ID")
    private Long lostItemId;
    
    @Schema(description = "领取人学号")
    private String claimerStudentId;
    
    @Schema(description = "领取人姓名")
    private String claimerName;
    
    @Schema(description = "领取人手机号")
    private String claimerPhone;
}