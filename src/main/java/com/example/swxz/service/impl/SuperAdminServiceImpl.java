package com.example.swxz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.swxz.entity.SuperAdmin;
import com.example.swxz.mapper.SuperAdminMapper;
import com.example.swxz.service.SuperAdminService;
import com.example.swxz.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    private SuperAdminMapper superAdminMapper;

    @Override
    public SuperAdmin login(String username, String password) {
        // 首先根据用户名查找超级管理员
        QueryWrapper<SuperAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SuperAdmin superAdmin = superAdminMapper.selectOne(wrapper);
        
        // 如果超级管理员不存在，返回null
        if (superAdmin == null) {
            return null;
        }
        
        // 使用BCrypt验证密码
        if (PasswordUtil.verifyPassword(password, superAdmin.getPassword())) {
            return superAdmin;
        }
        
        return null;
    }
}