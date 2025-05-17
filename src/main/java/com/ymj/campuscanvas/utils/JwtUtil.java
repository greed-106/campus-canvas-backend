package com.ymj.campuscanvas.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Lazy(false)  // 确保 Spring 容器启动时立即初始化
public class JwtUtil {

    // 单例实例引用
    private static JwtUtil INSTANCE;

    // Spring 注入的配置值（非 static）
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // 缓存签名密钥（避免重复解码）
    private SecretKey signingKey;

    // 私有构造函数确保单例
    private JwtUtil() {}

    // 初始化方法（Spring 回调）
    @PostConstruct
    private void init() {
        // 校验配置
        if (secret == null || secret.isEmpty()) {
            throw new IllegalStateException("JWT secret must be configured");
        }
        if (expiration <= 0) {
            throw new IllegalStateException("JWT expiration must be positive");
        }

        // 初始化密钥
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        // 设置单例引用
        INSTANCE = this;
    }

    /*-------------------- 静态方法入口 --------------------*/
    public static String generateToken(Long userId) {
        return INSTANCE.doGenerateToken(userId);
    }

    public static Claims parseToken(String token) {
        return INSTANCE.doParseToken(token);
    }

    public static boolean isTokenExpired(String token) {
        return INSTANCE.doIsTokenExpired(token);
    }

    public static Long getUserIdFromToken(String token) {
        return INSTANCE.doGetUserIdFromToken(token);
    }

    /*-------------------- 实例方法实现 --------------------*/
    private String doGenerateToken(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    private Claims doParseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean doIsTokenExpired(String token) {
        Claims claims = doParseToken(token);
        return claims.getExpiration().before(new Date());
    }

    private Long doGetUserIdFromToken(String token) {
        Claims claims = doParseToken(token);
        return Long.valueOf(claims.getSubject());
    }
}