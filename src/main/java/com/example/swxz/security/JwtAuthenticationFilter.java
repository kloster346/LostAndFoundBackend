package com.example.swxz.security;

import com.example.swxz.config.JwtConfig;
import com.example.swxz.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * JWT认证过滤器
 * 负责从HTTP请求中提取JWT令牌，验证令牌有效性，并设置Spring Security上下文
 * 
 * @author System
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            // 从请求中提取JWT令牌
            String token = extractTokenFromRequest(request);
            
            if (token != null && jwtUtil.validateTokenFormat(token)) {
                // 令牌有效，设置认证上下文
                setAuthenticationContext(token, request);
            }
        } catch (Exception e) {
            log.error("JWT认证过程中发生错误: {}", e.getMessage());
            // 清除可能存在的认证上下文
            SecurityContextHolder.clearContext();
        }

        // 继续过滤器链
        filterChain.doFilter(request, response);
    }

    /**
     * 从HTTP请求中提取JWT令牌
     * 
     * @param request HTTP请求
     * @return JWT令牌字符串，如果不存在则返回null
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        // 从Authorization头中提取令牌
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtConfig.getTokenPrefix())) {
            return bearerToken.substring(jwtConfig.getTokenPrefix().length());
        }
        
        return null;
    }

    /**
     * 设置Spring Security认证上下文
     * 
     * @param token JWT令牌
     * @param request HTTP请求
     */
    private void setAuthenticationContext(String token, HttpServletRequest request) {
        try {
            // 从令牌中提取用户信息
            String username = jwtUtil.getUsernameFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 创建权限列表
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
                );

                // 创建认证令牌
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
                
                // 设置认证详情
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 将用户ID添加到认证详情中（可选）
                // 可以通过自定义Authentication实现来携带更多用户信息
                
                // 设置到Security上下文
                SecurityContextHolder.getContext().setAuthentication(authToken);
                
                log.debug("JWT认证成功 - 用户: {}, 角色: {}, ID: {}", username, role, userId);
            }
        } catch (Exception e) {
            log.error("设置认证上下文时发生错误: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }
    }

    /**
     * 判断是否应该跳过此过滤器
     * 可以在这里添加不需要JWT认证的路径
     * 
     * @param request HTTP请求
     * @return 如果应该跳过则返回true
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        
        // 跳过登录相关的路径
        return path.startsWith("/api/login") || 
               path.startsWith("/api/register") ||
               path.startsWith("/swagger-ui") ||
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/actuator/health");
    }
}