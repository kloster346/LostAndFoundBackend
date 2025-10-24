package com.example.swxz.service;

import java.util.Date;

/**
 * Token黑名单服务接口
 * 用于管理失效的JWT token
 */

public interface TokenBlacklistService {
    
    /**
     * 将token加入黑名单
     * @param token JWT token
     * @param expiration 过期时间
     */
    void addToBlacklist(String token, Date expiration);
    
    /**
     * 检查token是否在黑名单中
     * @param token JWT token
     * @return true表示在黑名单中，false表示不在
     */
    boolean isTokenBlacklisted(String token);
    
    /**
     * 清理过期的黑名单token
     */
    void cleanupExpiredTokens();
}
