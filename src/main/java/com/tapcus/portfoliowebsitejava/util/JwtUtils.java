package com.tapcus.portfoliowebsitejava.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Data
public class JwtUtils {
    // 過期毫秒數
    @Value("${jwt.exprier}")
    private long expire;
    // 密鑰
    @Value("${jwt.secret}")
    private String secret;

    // 給 jwt 取名稱，通過 header 獲取名稱
    @Value("${jwt.header}")
    private String header;

    Date nowDate = new Date();

    // 生成 jwt
    // 參數表示主體這裡是 username
    public String generateToken(String username) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                // 創建時間
                .setIssuedAt(nowDate)
                // 過期時間，這裡設置 7 天
                .setExpiration(new Date(Instant.now().toEpochMilli() + expire))
                // 加簽形式與密鑰
                .signWith(SignatureAlgorithm.HS512, secret)
                // 進行合成
                .compact();
    }

    // 解析 jwt
    // 解析完成沒問題就能拿到 Subject 這裡是 username
    // Claims 就是 jwt 創建時所設置的東西
    // 包含主體、創建過期時間等等
    public Claims getClaimByToken(String jwt) {
        // jwt 被竄改的可能，導致例外，後面程式無法進行
        // 發生問題就返回空，不為空就是通過驗證
        try {
            return Jwts.parser()
                    // 解析用的密鑰
                    .setSigningKey(secret)
                    // 解析
                    .parseClaimsJws(jwt)
                    // 返回 Claims
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // 判斷 jwt 是否過期
    public  boolean isTokenExpired(Claims claims) {
        // 過期時間是否在現在時間之前
        // 在之前表示過期
        return claims.getExpiration().before(new Date());
    }
}
