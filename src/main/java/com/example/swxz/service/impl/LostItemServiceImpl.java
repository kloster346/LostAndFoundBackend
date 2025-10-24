package com.example.swxz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swxz.dto.ClaimApprovalRequest;
import com.example.swxz.dto.ClaimRequest;
import com.example.swxz.dto.LostItemRequest;
import com.example.swxz.dto.LostItemSearchRequest;
import com.example.swxz.dto.PendingClaimResponse;
import com.example.swxz.entity.ClaimedItem;
import com.example.swxz.entity.LostItem;
import com.example.swxz.mapper.ClaimedItemMapper;
import com.example.swxz.mapper.LostItemMapper;
import com.example.swxz.service.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID; 

@Service
public class LostItemServiceImpl implements LostItemService {
    
    @Autowired
    private LostItemMapper lostItemMapper;
    
    @Autowired
    private ClaimedItemMapper claimedItemMapper;
    
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    @Override
    public IPage<LostItem> searchLostItems(LostItemSearchRequest searchRequest) {
        Page<LostItem> page = new Page<>(searchRequest.getPageNum(), searchRequest.getPageSize());
        return lostItemMapper.searchLostItems(page, searchRequest);
    }
    
    @Override
    public LostItem publishLostItem(Long adminId, LostItemRequest request, MultipartFile image) {
        LostItem lostItem = new LostItem();
        lostItem.setName(request.getName());
        lostItem.setType(request.getType());
        lostItem.setColor(request.getColor());
        lostItem.setDescription(request.getDescription());
        
        // 构建捡到地点字符串
        String foundLocation = "[" + getBuildingName(request.getBuilding()) + "]";
        if (request.getSpecificLocation() != null && !request.getSpecificLocation().isEmpty()) {
            foundLocation += request.getSpecificLocation();
        }
        lostItem.setFoundLocation(foundLocation);
        lostItem.setBuilding(request.getBuilding());
        lostItem.setSpecificLocation(request.getSpecificLocation());
        
        // 处理图片上传
        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            lostItem.setImageUrl(imageUrl);
        }
        
        lostItem.setPublishTime(LocalDateTime.now());
        lostItem.setIsClaimed(0);
        lostItem.setAdminId(adminId);
        lostItem.setCreateTime(LocalDateTime.now());
        lostItem.setUpdateTime(LocalDateTime.now());
        
        lostItemMapper.insert(lostItem);
        return lostItem;
    }
    
    @Override
    public LostItem getLostItemById(Long id) {
        return lostItemMapper.selectById(id);
    }
    
    @Override
    public boolean deleteLostItem(Long id, Long adminId, boolean isSuperAdmin) {
        LostItem lostItem = lostItemMapper.selectById(id);
        if (lostItem == null) {
            return false;
        }
        
        // 超级管理员可以删除任何失物，普通管理员只能删除自己发布的
        if (!isSuperAdmin && !lostItem.getAdminId().equals(adminId)) {
            return false;
        }
        
        return lostItemMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean claimLostItem(ClaimRequest request) {
        LostItem lostItem = lostItemMapper.selectById(request.getLostItemId());
        if (lostItem == null || lostItem.getIsClaimed() == 1) {
            return false;
        }
        
        // 只创建申请记录，不修改lost_items表
        ClaimedItem claimedItem = new ClaimedItem();
        claimedItem.setOriginalLostItemId(lostItem.getId());
        claimedItem.setName(lostItem.getName());
        claimedItem.setType(String.valueOf(lostItem.getType()));
        claimedItem.setColor(String.valueOf(lostItem.getColor()));
        claimedItem.setDescription(lostItem.getDescription());
        claimedItem.setFoundLocation(lostItem.getFoundLocation());
        claimedItem.setBuilding(lostItem.getBuilding());
        claimedItem.setSpecificLocation(lostItem.getSpecificLocation());
        claimedItem.setImageUrl(lostItem.getImageUrl());
        claimedItem.setPublishTime(lostItem.getPublishTime());
        claimedItem.setAdminId(lostItem.getAdminId());
        claimedItem.setClaimerStudentId(request.getClaimerStudentId());
        claimedItem.setClaimerName(request.getClaimerName());
        claimedItem.setClaimerPhone(request.getClaimerPhone());
        claimedItem.setClaimStatus("PENDING");
        claimedItem.setApplyTime(LocalDateTime.now());
        claimedItem.setCreateTime(LocalDateTime.now());
        
        claimedItemMapper.insert(claimedItem);
        return true;
    }
    
    @Override
    public IPage<LostItem> getLostItemsByAdmin(Long adminId, Integer pageNum, Integer pageSize) {
        Page<LostItem> page = new Page<>(pageNum, pageSize);
        QueryWrapper<LostItem> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id", adminId).eq("is_claimed", 0);
        return lostItemMapper.selectPage(page, wrapper);
    }
    
    @Override
    public IPage<LostItem> getAllLostItems(Integer pageNum, Integer pageSize) {
        Page<LostItem> page = new Page<>(pageNum, pageSize);
        QueryWrapper<LostItem> wrapper = new QueryWrapper<>();
        wrapper.eq("is_claimed", 0);
        return lostItemMapper.selectPage(page, wrapper);
    }
    
    @Override
    public IPage<PendingClaimResponse> getPendingClaims(Long adminId, Integer pageNum, Integer pageSize) {
        Page<PendingClaimResponse> page = new Page<>(pageNum, pageSize);
        return claimedItemMapper.getPendingClaims(page, adminId);
    }
    
    @Override
    public boolean approveClaim(Long adminId, ClaimApprovalRequest request) {
        // 验证管理员权限
        ClaimedItem claimedItem = claimedItemMapper.selectById(request.getClaimId());
        if (claimedItem == null || !claimedItem.getAdminId().equals(adminId)) {
            return false;
        }
        
        // 验证当前状态
        if (!"PENDING".equals(claimedItem.getClaimStatus())) {
            return false;
        }
        
        if ("APPROVED".equals(request.getAction())) {
            // 审核通过：更新申请状态并修改lost_items表
            claimedItem.setClaimStatus("APPROVED");
            claimedItem.setClaimTime(LocalDateTime.now());
            claimedItemMapper.updateById(claimedItem);
            
            // 更新原失物状态
            LostItem lostItem = lostItemMapper.selectById(claimedItem.getOriginalLostItemId());
            if (lostItem != null) {
                lostItem.setIsClaimed(1);
                lostItem.setClaimTime(LocalDateTime.now());
                lostItem.setClaimerStudentId(claimedItem.getClaimerStudentId());
                lostItem.setClaimerName(claimedItem.getClaimerName());
                lostItem.setClaimerPhone(claimedItem.getClaimerPhone());
                lostItem.setUpdateTime(LocalDateTime.now());
                lostItemMapper.updateById(lostItem);
            }
        } else if ("REJECTED".equals(request.getAction())) {
            // 审核拒绝：只更新申请状态
            claimedItem.setClaimStatus("REJECTED");
            claimedItemMapper.updateById(claimedItem);
        } else {
            return false;
        }
        
        return true;
    }
    
    private String saveImage(MultipartFile image) {
        try {
            if (!Files.exists(Paths.get(uploadDir))) {
                Files.createDirectories(Paths.get(uploadDir));
            }
            
            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;
            
            Path filePath = Paths.get(uploadDir, filename);
            Files.write(filePath, image.getBytes());
            
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("图片保存失败", e);
        }
    }
    
    private String getBuildingName(Integer buildingCode) {
        // 这里可以根据buildingCode获取对应的楼栋名称
        // 简化处理，返回字符串表示
        return "楼栋" + buildingCode;
    }
}