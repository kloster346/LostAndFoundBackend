package com.example.swxz.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * 提供JWT token的生成、验证、解析等功能
 */
@Component
public class JwtUtil {

    /**
     * JWT密钥（从配置文件读取）
     */
    @Value("${jwt.secret:mySecretKey123456789012345678901234567890}")
    private String secret;

    /**
     * JWT过期时间（毫秒）- 2小时
     */
    @Value("${jwt.expiration:7200000}")
    private Long expiration;

    /**
     * JWT签发者
     */
    @Value("${jwt.issuer:swxz-system}")
    private String issuer;

    /**
     * 获取签名密钥
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成JWT token
     * 
     * @param userId   用户ID
     * @param username 用户名
     * @param role     用户角色（user/lost_item_admin/super_admin）
     * @return JWT token字符串
     */
    public String generateToken(Long userId, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("role", role);

        return createToken(claims, username);
    }

    /**
     * 创建JWT token
     * 
     * @param claims  声明信息
     * @param subject 主题（通常是用户名）
     * @return JWT token字符串
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 从token中获取用户名
     * 
     * @param token JWT token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 从token中获取用户ID
     * 
     * @param token JWT token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.valueOf(claims.get("userId").toString());
    }

    /**
     * 从token中获取用户角色
     * 
     * @param token JWT token
     * @return 用户角色
     */
    public String getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("role").toString();
    }

    /**
     * 从token中获取过期时间
     * 
     * @param token JWT token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    } 

    /**
     * 从token中获取所有声明
     *  
     * @param token JWT token
     * @return Claims对象
     */
    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    /**
     * 验证token是否过期
     * 
     * @param token JWT token
     * @return true表示已过期，false表示未过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证token是否有效
     * 
     * @param token    JWT token
     * @param username 用户名
     * @return true表示有效，false表示无效
     */
    public Boolean validateToken(String token, String username) {
        try {
            String tokenUsername = getUsernameFromToken(token);
            return (username.equals(tokenUsername) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证token格式和签名是否正确
     * 
     * @param token JWT token
     * @return true表示有效，false表示无效
     */
    public Boolean validateTokenFormat(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 刷新token（生成新的token）
     * 
     * @param token 原token
     * @return 新的JWT token
     */
    public String refreshToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Long userId = Long.valueOf(claims.get("userId").toString());
            String username = claims.getSubject();
            String role = claims.get("role").toString();

            return generateToken(userId, username, role);
        } catch (Exception e) {
            throw new RuntimeException("Cannot refresh token", e);
        }
    }

    /**
     * 从HTTP请求头中提取token
     * 
     * @param authHeader Authorization请求头的值
     * @return JWT token字符串，如果格式不正确则返回null
     */
    public String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}