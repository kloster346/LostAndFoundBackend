package com.example.swxz.controller;

import com.example.swxz.common.Result;
import com.example.swxz.dto.LoginRequest;
import com.example.swxz.dto.LoginResponse;
import com.example.swxz.entity.SuperAdmin;
import com.example.swxz.service.SuperAdminService;
import com.example.swxz.util.JwtUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.swxz.dto.UserSearchRequest;
import com.example.swxz.dto.LostItemAdminSearchRequest;
import com.example.swxz.dto.UserDetailResponse;
import com.example.swxz.dto.LostItemAdminDetailResponse;
// 新增导入
import com.example.swxz.dto.LostItemOverviewResponse;
import com.example.swxz.service.LostItemService;
import com.example.swxz.entity.User;
import com.example.swxz.entity.LostItemAdmin;
import com.example.swxz.service.UserService;
import com.example.swxz.service.LostItemAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/super")
@Tag(name = "超级管理员接口", description = "超级管理员相关接口")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "超级管理员登录")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 保持原有验证逻辑不变
        SuperAdmin admin = superAdminService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (admin == null) {
            return Result.error("用户名或密码错误");
        }
        
        // 生成JWT token
        String token = jwtUtil.generateToken(admin.getId(), admin.getUsername(), "SUPER_ADMIN");
        
        // 返回统一的LoginResponse格式
        LoginResponse loginResponse = new LoginResponse(token, admin.getId(), admin.getUsername(), "SUPER_ADMIN");
        return Result.success(loginResponse);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private LostItemAdminService lostItemAdminService;

    // 新增：注入失物服务
    @Autowired
    private LostItemService lostItemService;

    @GetMapping("/users")
    @Operation(summary = "获取用户列表（超级管理员）")
    public com.example.swxz.common.Result<IPage<UserDetailResponse>> getUsers(UserSearchRequest request) {
        IPage<User> page = userService.searchUsers(request);
        Page<UserDetailResponse> dtoPage = new Page<>(page.getCurrent(), page.getSize());
        dtoPage.setTotal(page.getTotal());
        java.util.List<UserDetailResponse> records = page.getRecords().stream().map(this::toUserDetail).toList();
        dtoPage.setRecords(records);
        return com.example.swxz.common.Result.success(dtoPage);
    }

    @GetMapping("/admins")
    @Operation(summary = "获取失物管理员列表（超级管理员）")
    public com.example.swxz.common.Result<IPage<LostItemAdminDetailResponse>> getAdmins(LostItemAdminSearchRequest request) {
        IPage<LostItemAdmin> page = lostItemAdminService.searchAdmins(request);
        Page<LostItemAdminDetailResponse> dtoPage = new Page<>(page.getCurrent(), page.getSize());
        dtoPage.setTotal(page.getTotal());
        java.util.List<LostItemAdminDetailResponse> records = page.getRecords().stream().map(this::toAdminDetail).toList();
        dtoPage.setRecords(records);
        return com.example.swxz.common.Result.success(dtoPage);
    }

    // 新增：超管获取所有失物（包含已领取与管理员名）
    @GetMapping("/lost-items")
    @Operation(summary = "获取所有失物（含领取人与管理员信息）")
    public com.example.swxz.common.Result<IPage<LostItemOverviewResponse>> getAllLostItems(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        IPage<LostItemOverviewResponse> page = lostItemService.getAllLostItemsWithDetails(pageNum, pageSize, keyword);
        return com.example.swxz.common.Result.success(page);
    }

    private UserDetailResponse toUserDetail(User user) {
        UserDetailResponse dto = new UserDetailResponse();
        dto.setId(user.getId());
        dto.setStudentId(user.getStudentId());
        dto.setUsername(user.getUsername());
        dto.setCollege(user.getCollege());
        dto.setPhone(user.getPhone());
        dto.setCreateTime(user.getCreateTime());
        dto.setUpdateTime(user.getUpdateTime());
        return dto;
    }

    private LostItemAdminDetailResponse toAdminDetail(LostItemAdmin admin) {
        LostItemAdminDetailResponse dto = new LostItemAdminDetailResponse();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setCollege(admin.getCollege());
        dto.setOfficeLocation(admin.getOfficeLocation());
        dto.setPhone(admin.getPhone());
        dto.setCreateTime(admin.getCreateTime());
        dto.setUpdateTime(admin.getUpdateTime());
        return dto;
    }
}