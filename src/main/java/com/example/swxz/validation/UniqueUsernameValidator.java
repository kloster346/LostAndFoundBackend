package com.example.swxz.validation;

import com.example.swxz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 用户名唯一性验证器
 * 实现UniqueUsername注解的验证逻辑
 */
@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        // 初始化方法，可以在这里获取注解参数
    }
    
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // 如果用户名为空，交给其他验证注解处理
        if (username == null || username.trim().isEmpty()) {
            return true;
        }
        
        // 检查用户名是否已存在
        return !userService.isUsernameExists(username);
    }
}