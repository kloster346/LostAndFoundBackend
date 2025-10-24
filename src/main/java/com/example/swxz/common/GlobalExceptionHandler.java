package com.example.swxz.common;

import com.example.swxz.exception.JwtAuthenticationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理JWT认证异常
     */
    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> handleJwtAuthenticationException(JwtAuthenticationException e) {
        return Result.error(401, e.getMessage());
    }
    
    /**
     * 处理JWT令牌过期异常
     */
    @ExceptionHandler(JwtAuthenticationException.TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> handleTokenExpiredException(JwtAuthenticationException.TokenExpiredException e) {
        return Result.error(401, "令牌已过期，请重新登录");
    }
    
    /**
     * 处理JWT令牌无效异常
     */
    @ExceptionHandler(JwtAuthenticationException.TokenInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> handleTokenInvalidException(JwtAuthenticationException.TokenInvalidException e) {
        return Result.error(401, "令牌无效，请重新登录");
    }
    
    /**
     * 处理JWT令牌缺失异常
     */
    @ExceptionHandler(JwtAuthenticationException.TokenMissingException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> handleTokenMissingException(JwtAuthenticationException.TokenMissingException e) {
        return Result.error(401, "缺少认证令牌，请先登录");
    }
    
    /**
     * 处理JWT令牌格式错误异常
     */
    @ExceptionHandler(JwtAuthenticationException.TokenMalformedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> handleTokenMalformedException(JwtAuthenticationException.TokenMalformedException e) {
        return Result.error(401, "令牌格式错误，请重新登录");
    }
    
    /**
     * 处理数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Result.error(400, "数据验证失败", errors);
    }
    
    /**
     * 处理数据库唯一约束异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e) {
        String message = e.getMessage();
        if (message.contains("username")) {
            return Result.error(409, "用户名已存在");
        } else if (message.contains("student_id")) {
            return Result.error(409, "学号已存在");
        }
        return Result.error(409, "数据重复，请检查输入");
    }
    
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.error("系统异常，请联系管理员");
    }
}