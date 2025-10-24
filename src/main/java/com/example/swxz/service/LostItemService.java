package com.example.swxz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.swxz.dto.ClaimApprovalRequest;
import com.example.swxz.dto.ClaimRequest;
import com.example.swxz.dto.LostItemRequest;
import com.example.swxz.dto.LostItemSearchRequest;
import com.example.swxz.dto.PendingClaimResponse;
import com.example.swxz.entity.LostItem; 
import org.springframework.web.multipart.MultipartFile;

public interface LostItemService {
    
    IPage<LostItem> searchLostItems(LostItemSearchRequest searchRequest);
    
    LostItem publishLostItem(Long adminId, LostItemRequest request, MultipartFile image);
    
    LostItem getLostItemById(Long id);
    
    boolean deleteLostItem(Long id, Long adminId, boolean isSuperAdmin);
    
    boolean claimLostItem(ClaimRequest request);
    
    IPage<LostItem> getLostItemsByAdmin(Long adminId, Integer pageNum, Integer pageSize);
    
    IPage<LostItem> getAllLostItems(Integer pageNum, Integer pageSize);
    
    IPage<PendingClaimResponse> getPendingClaims(Long adminId, Integer pageNum, Integer pageSize);
    
    boolean approveClaim(Long adminId, ClaimApprovalRequest request);
}