package com.example.swxz.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 * 提供密码加密和验证功能
 */
@Component
public class PasswordUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 加密密码
     * 
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证密码
     * 
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 密码是否匹配
     */
    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 检查密码是否已加密
     * BCrypt加密后的密码以$2a$、$2b$、$2x$或$2y$开头
     * 
     * @param password 密码
     * @return 是否已加密
     */
    public static boolean isEncrypted(String password) {
        return password != null &&
                (password.startsWith("$2a$") ||
                        password.startsWith("$2b$") ||
                        password.startsWith("$2x$") ||
                        password.startsWith("$2y$"));
    }
}