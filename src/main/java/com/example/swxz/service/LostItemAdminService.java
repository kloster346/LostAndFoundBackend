package com.example.swxz.service;

import com.example.swxz.entity.LostItemAdmin;

public interface LostItemAdminService {
    LostItemAdmin login(String username, String password);
    LostItemAdmin getAdminById(Long id);
}