package com.example.swxz.service;

import com.example.swxz.entity.SuperAdmin;

public interface SuperAdminService {
    SuperAdmin login(String username, String password);
}