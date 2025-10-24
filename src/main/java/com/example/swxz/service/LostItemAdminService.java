package com.example.swxz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.swxz.dto.LostItemAdminSearchRequest;
import com.example.swxz.entity.LostItemAdmin;

public interface LostItemAdminService {
    LostItemAdmin login(String username, String password);
    LostItemAdmin getAdminById(Long id);
    IPage<LostItemAdmin> searchAdmins(LostItemAdminSearchRequest request);
}