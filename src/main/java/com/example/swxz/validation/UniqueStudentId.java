package com.example.swxz.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * 学号唯一性验证注解
 * 用于验证学号在数据库中是否已存在
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueStudentIdValidator.class)
@Documented
public @interface UniqueStudentId {
    
    /**
     * 默认错误消息
     */
    String message() default "学号已存在";
    
    /**
     * 验证组
     */
    Class<?>[] groups() default {};
    
    /**
     * 负载
     */
    Class<? extends Payload>[] payload() default {};
}