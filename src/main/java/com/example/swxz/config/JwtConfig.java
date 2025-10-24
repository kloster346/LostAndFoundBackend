package com.example.swxz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置类
 * 用于管理JWT相关的配置参数
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * JWT密钥
     * 建议使用至少32位的复杂字符串
     */
    private String secret = "mySecretKey123456789012345678901234567890";

    /**
     * JWT过期时间（毫秒）
     * 默认2小时 = 2 * 60 * 60 * 1000 = 7200000毫秒
     */
    private Long expiration = 7200000L;

    /**
     * JWT签发者
     */
    private String issuer = "swxz-system";

    /**
     * JWT主题
     */
    private String subject = "swxz-auth";

    /**
     * JWT受众
     */
    private String audience = "swxz-users";

    /**
     * 请求头中token的名称
     */
    private String header = "Authorization";

    /**
     * token前缀
     */
    private String tokenPrefix = "Bearer ";

    /**
     * 是否启用JWT认证
     */
    private Boolean enabled = true;

    /**
     * 刷新token的时间窗口（毫秒）
     * 在token过期前多长时间可以刷新token
     * 默认30分钟 = 30 * 60 * 1000 = 1800000毫秒
     */
    private Long refreshWindow = 1800000L;

    // Getter和Setter方法

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getRefreshWindow() {
        return refreshWindow;
    }

    public void setRefreshWindow(Long refreshWindow) {
        this.refreshWindow = refreshWindow;
    }

    /**
     * 获取完整的token前缀（包含空格）
     */
    public String getFullTokenPrefix() {
        return tokenPrefix;
    }

    /**
     * 检查是否需要刷新token
     * @param expirationTime token过期时间
     * @return true表示需要刷新，false表示不需要
     */
    public boolean shouldRefreshToken(long expirationTime) {
        long currentTime = System.currentTimeMillis();
        return (expirationTime - currentTime) <= refreshWindow;
    }

    @Override
    public String toString() {
        return "JwtConfig{" +
                "secret='[PROTECTED]'" +
                ", expiration=" + expiration +
                ", issuer='" + issuer + '\'' +
                ", subject='" + subject + '\'' +
                ", audience='" + audience + '\'' +
                ", header='" + header + '\'' +
                ", tokenPrefix='" + tokenPrefix + '\'' +
                ", enabled=" + enabled +
                ", refreshWindow=" + refreshWindow +
                '}';
    }
}