package com.example.swxz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swxz.dto.LostItemAdminSearchRequest;
import com.example.swxz.entity.LostItemAdmin;
import com.example.swxz.mapper.LostItemAdminMapper;
import com.example.swxz.service.LostItemAdminService;
import com.example.swxz.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LostItemAdminServiceImpl implements LostItemAdminService {

    @Autowired
    private LostItemAdminMapper lostItemAdminMapper;

    @Override
    public LostItemAdmin login(String username, String password) {
        // 首先根据用户名查找管理员
        QueryWrapper<LostItemAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        LostItemAdmin admin = lostItemAdminMapper.selectOne(wrapper);
        
        // 如果管理员不存在，返回null
        if (admin == null) {
            return null;
        }
        
        // 使用BCrypt验证密码
        if (PasswordUtil.verifyPassword(password, admin.getPassword())) {
            return admin;
        }
        
        return null;
    }

    @Override
    public LostItemAdmin getAdminById(Long id) {
        return lostItemAdminMapper.selectById(id);
    }

    @Override
    public IPage<LostItemAdmin> searchAdmins(LostItemAdminSearchRequest request) {
        Integer pageNum = request.getPageNum() != null && request.getPageNum() > 0 ? request.getPageNum() : 1;
        Integer pageSize = request.getPageSize() != null && request.getPageSize() > 0 ? request.getPageSize() : 10;

        QueryWrapper<LostItemAdmin> wrapper = new QueryWrapper<>();
        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            wrapper.like("username", request.getUsername());
        }
        if (request.getCollege() != null && !request.getCollege().isEmpty()) {
            wrapper.like("college", request.getCollege());
        }
        if (request.getOfficeLocation() != null && !request.getOfficeLocation().isEmpty()) {
            wrapper.like("office_location", request.getOfficeLocation());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            wrapper.like("phone", request.getPhone());
        }

        String sortBy = (request.getSortBy() == null || request.getSortBy().isEmpty()) ? "create_time" : request.getSortBy();
        String sortOrder = (request.getSortOrder() == null || request.getSortOrder().isEmpty()) ? "desc" : request.getSortOrder().toLowerCase();
        java.util.Set<String> whitelist = new java.util.HashSet<>(java.util.Arrays.asList("create_time", "update_time", "username"));
        if (!whitelist.contains(sortBy)) {
            sortBy = "create_time";
        }
        if ("asc".equals(sortOrder)) {
            wrapper.orderByAsc(sortBy);
        } else {
            wrapper.orderByDesc(sortBy);
        }

        Page<LostItemAdmin> page = new Page<>(pageNum, pageSize);
        return lostItemAdminMapper.selectPage(page, wrapper);
    }
}