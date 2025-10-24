package com.example.swxz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
}