package com.example.swxz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swxz.dto.RegisterRequest;
import com.example.swxz.dto.RegisterResponse;
import com.example.swxz.dto.UserSearchRequest;
import com.example.swxz.entity.User;
import com.example.swxz.mapper.UserMapper;
import com.example.swxz.service.UserService;
import com.example.swxz.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        // 首先根据用户名查找用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        
        // 如果用户不存在，返回null
        if (user == null) {
            return null;
        }
        
        // 使用BCrypt验证密码
        if (PasswordUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }
        
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User updateUser(User user) {
        user.setUpdateTime(java.time.LocalDateTime.now());
        userMapper.updateById(user);
        return user;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (isUsernameExists(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 检查学号是否已存在
        if (isStudentIdExists(request.getStudentId())) {
            throw new RuntimeException("学号已存在");
        }
        
        // 创建新用户
        User user = new User();
        user.setStudentId(request.getStudentId());
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtil.encryptPassword(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setCollege(request.getCollege());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        // 保存用户
        userMapper.insert(user);
        
        // 构建响应
        RegisterResponse response = new RegisterResponse();
        response.setId(user.getId());
        response.setStudentId(user.getStudentId());
        response.setUsername(user.getUsername());
        response.setCollege(user.getCollege());
        response.setPhone(user.getPhone());
        response.setCreateTime(user.getCreateTime());
        response.setMessage("注册成功");
        
        return response;
    }

    @Override
    public boolean isUsernameExists(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean isStudentIdExists(String studentId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        return userMapper.selectCount(wrapper) > 0;
    }

    @Override
    public com.baomidou.mybatisplus.core.metadata.IPage<User> searchUsers(UserSearchRequest request) {
        Integer pageNum = request.getPageNum() != null && request.getPageNum() > 0 ? request.getPageNum() : 1;
        Integer pageSize = request.getPageSize() != null && request.getPageSize() > 0 ? request.getPageSize() : 10;

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            wrapper.like("username", request.getUsername());
        }
        if (request.getStudentId() != null && !request.getStudentId().isEmpty()) {
            wrapper.eq("student_id", request.getStudentId());
        }
        if (request.getCollege() != null && !request.getCollege().isEmpty()) {
            wrapper.like("college", request.getCollege());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            wrapper.like("phone", request.getPhone());
        }

        String sortBy = (request.getSortBy() == null || request.getSortBy().isEmpty()) ? "create_time" : request.getSortBy();
        String sortOrder = (request.getSortOrder() == null || request.getSortOrder().isEmpty()) ? "desc" : request.getSortOrder().toLowerCase();
        // 白名单校验，避免非法字段被用于排序
        java.util.Set<String> whitelist = new java.util.HashSet<>(java.util.Arrays.asList("create_time", "update_time", "username"));
        if (!whitelist.contains(sortBy)) {
            sortBy = "create_time";
        }
        if ("asc".equals(sortOrder)) {
            wrapper.orderByAsc(sortBy);
        } else {
            wrapper.orderByDesc(sortBy);
        }

        Page<User> page = new Page<>(pageNum, pageSize);
        return userMapper.selectPage(page, wrapper);
    }
}