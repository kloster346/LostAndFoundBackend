package com.example.swxz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swxz.dto.PendingClaimResponse;
import com.example.swxz.entity.ClaimedItem;
import org.apache.ibatis.annotations.Param;

public interface ClaimedItemMapper extends BaseMapper<ClaimedItem> {
    
    IPage<ClaimedItem> findClaimedItems(Page<ClaimedItem> page, @Param("adminId") Long adminId);
    
    IPage<PendingClaimResponse> getPendingClaims(Page<PendingClaimResponse> page, @Param("adminId") Long adminId);
}