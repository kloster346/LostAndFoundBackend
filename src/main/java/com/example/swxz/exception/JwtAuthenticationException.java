package com.example.swxz.exception;

/**
 * JWT认证异常类
 * 用于处理JWT令牌相关的认证错误
 */
public class JwtAuthenticationException extends RuntimeException {
    
    private String errorCode;
    
    public JwtAuthenticationException(String message) {
        super(message);
    }
    
    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public JwtAuthenticationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public JwtAuthenticationException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    // 常用的JWT异常类型
    public static class TokenExpiredException extends JwtAuthenticationException {
        public TokenExpiredException(String message) {
            super("TOKEN_EXPIRED", message);
        }
    }
    
    public static class TokenInvalidException extends JwtAuthenticationException {
        public TokenInvalidException(String message) {
            super("TOKEN_INVALID", message);
        }
    }
    
    public static class TokenMissingException extends JwtAuthenticationException {
        public TokenMissingException(String message) {
            super("TOKEN_MISSING", message);
        }
    }
    
    public static class TokenMalformedException extends JwtAuthenticationException {
        public TokenMalformedException(String message) {
            super("TOKEN_MALFORMED", message);
        }
    }
}