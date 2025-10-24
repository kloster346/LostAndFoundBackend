package com.example.swxz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.swxz.common.Result;
import com.example.swxz.dto.ClaimApprovalRequest;
import com.example.swxz.dto.ClaimRequest;
import com.example.swxz.dto.LostItemRequest;
import com.example.swxz.dto.LostItemSearchRequest;
import com.example.swxz.dto.PendingClaimResponse;
import com.example.swxz.entity.LostItem;
import com.example.swxz.service.LostItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/lost-items")
@Tag(name = "失物接口", description = "失物相关接口")
public class LostItemController {
    
    @Autowired
    private LostItemService lostItemService;
    
    @GetMapping("/search")
    @Operation(summary = "搜索失物")
    public Result<IPage<LostItem>> searchLostItems(LostItemSearchRequest searchRequest) {
        IPage<LostItem> page = lostItemService.searchLostItems(searchRequest);
        return Result.success(page);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取失物详情")
    public Result<LostItem> getLostItem(@PathVariable Long id) {
        LostItem lostItem = lostItemService.getLostItemById(id);
        if (lostItem == null) {
            return Result.error("失物不存在");
        }
        return Result.success(lostItem);
    }
    
    @PostMapping("/publish")
    @Operation(summary = "发布失物")
    public Result<LostItem> publishLostItem(@RequestParam Long adminId,
                                   @ModelAttribute LostItemRequest request,
                                   @RequestParam(required = false) MultipartFile image) {
        LostItem lostItem = lostItemService.publishLostItem(adminId, request, image);
        return Result.success(lostItem);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除失物")
    public Result<Boolean> deleteLostItem(@PathVariable Long id,
                                 @RequestParam Long adminId,
                                 @RequestParam(defaultValue = "false") boolean isSuperAdmin) {
        boolean success = lostItemService.deleteLostItem(id, adminId, isSuperAdmin);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("删除失败");
        }
    }
    
    @PostMapping("/claim")
    @Operation(summary = "领取失物")
    public Result<Boolean> claimLostItem(@RequestBody ClaimRequest request) {
        boolean success = lostItemService.claimLostItem(request);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("领取失败");
        }
    }
    
    @GetMapping("/admin/{adminId}")
    @Operation(summary = "获取管理员发布的失物")
    public Result<IPage<LostItem>> getLostItemsByAdmin(@PathVariable Long adminId,
                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<LostItem> page = lostItemService.getLostItemsByAdmin(adminId, pageNum, pageSize);
        return Result.success(page);
    }
    
    @GetMapping("/all")
    @Operation(summary = "获取所有未领取的失物")
    public Result<IPage<LostItem>> getAllLostItems(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<LostItem> page = lostItemService.getAllLostItems(pageNum, pageSize);
        return Result.success(page);
    }
    
    @GetMapping("/pending-claims")
    @Operation(summary = "获取待审核的申领记录")
    public Result<IPage<PendingClaimResponse>> getPendingClaims(@RequestParam Long adminId,
                                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<PendingClaimResponse> page = lostItemService.getPendingClaims(adminId, pageNum, pageSize);
        return Result.success(page);
    }
    
    @PostMapping("/approve-claim")
    @Operation(summary = "审核申领")
    public Result<Boolean> approveClaim(@RequestParam Long adminId,
                                       @RequestBody ClaimApprovalRequest request) {
        boolean success = lostItemService.approveClaim(adminId, request);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("审核失败");
        }
    }
}