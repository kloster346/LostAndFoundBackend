package com.example.swxz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "申请审核请求DTO")
public class ClaimApprovalRequest {
    @Schema(description = "申请记录ID")
    private Long claimId;
    
    @Schema(description = "审核结果 APPROVED:通过 REJECTED:拒绝")
    private String action;
    
    @Schema(description = "审核备注（可选）")
    private String remark;
}