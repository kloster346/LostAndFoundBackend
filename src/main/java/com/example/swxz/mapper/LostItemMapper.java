package com.example.swxz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swxz.dto.LostItemSearchRequest;
import com.example.swxz.entity.LostItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LostItemMapper extends BaseMapper<LostItem> {
    
    IPage<LostItem> searchLostItems(Page<LostItem> page, @Param("param") LostItemSearchRequest searchRequest);
    
    List<LostItem> findByAdminId(@Param("adminId") Long adminId);
}