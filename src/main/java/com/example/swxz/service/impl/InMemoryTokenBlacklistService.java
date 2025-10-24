package com.example.swxz.service.impl;

import com.example.swxz.service.TokenBlacklistService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存的Token黑名单服务实现
 */
@Service
public class InMemoryTokenBlacklistService implements TokenBlacklistService {

    // 使用ConcurrentHashMap保证线程安全
    private final Map<String, Date> blacklist = new ConcurrentHashMap<>();

    @Override
    public void addToBlacklist(String token, Date expiration) {
        if (token != null && expiration != null) {
            blacklist.put(token, expiration);
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        if (token == null) {
            return false;
        }
        Date expiration = blacklist.get(token);
        if (expiration == null) {
            return false;
        }
        // 检查是否已过期
        return expiration.after(new Date());
    }

    @Override
    @Scheduled(fixedRate = 604800000L) // 每小时清理一次
    public void cleanupExpiredTokens() {
        Date now = new Date();
        new HashSet<>(blacklist.entrySet()).forEach(entry -> {
            if (entry.getValue().before(now)) {
                blacklist.remove(entry.getKey());
            }
        });
    }
}
