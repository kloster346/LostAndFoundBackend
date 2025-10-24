package com.example.swxz.validation;

import com.example.swxz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 学号唯一性验证器
 * 实现UniqueStudentId注解的验证逻辑
 */
@Component
public class UniqueStudentIdValidator implements ConstraintValidator<UniqueStudentId, String> {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void initialize(UniqueStudentId constraintAnnotation) {
        // 初始化方法，可以在这里获取注解参数
    }
    
    @Override
    public boolean isValid(String studentId, ConstraintValidatorContext context) {
        // 如果学号为空，交给其他验证注解处理
        if (studentId == null || studentId.trim().isEmpty()) {
            return true;
        }
        
        // 检查学号是否已存在
        return !userService.isStudentIdExists(studentId);
    }
}