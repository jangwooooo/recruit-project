package com.example.demo.global.security.jwt;

import com.example.demo.global.exception.exceptionCollection.TokenExpirationException;
import com.example.demo.global.exception.exceptionCollection.TokenNotValidException;
import com.example.demo.global.security.auth.AuthDetailsService;
import com.example.demo.global.security.jwt.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Getter
public class TokenProvider {


    private final AuthDetailsService authDetailsService;
    private final JwtProperties jwtProperties;
    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 120; // 2시간
    private final long REFRESH_TOKEN_EXPIRE_TIME = ACCESS_TOKEN_EXPIRE_TIME * 12 * 7; // 일주일
//
//    @Value("${security.jwt.token.secretKey}")
//    private String secretKey;


    @AllArgsConstructor
    private enum TokenType {
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken");
        String value;
    }

    @AllArgsConstructor
    private enum TokenClaimName {
        USER_EMAIL("userEmail"),
        TOKEN_TYPE("tokenType");
        String value;
    }

    // 암호화된 키 값 가져오기
    private Key getSignInKey(String secretKey) {
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

    // Token 생성
    private String generateToken(String userEmail, TokenType tokenType, String secret, long expireTime) {
        final Claims claims = Jwts.claims();
        claims.put(TokenClaimName.USER_EMAIL.value, userEmail);
        claims.put(TokenClaimName.TOKEN_TYPE.value, tokenType);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSignInKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    // 모든 Claims 추출 ( Payload에 들어가는 값은 Claims 이라고 부른다 )
    public Claims extractAllClaims(String token, String secret){
        token = token.replace("Bearer ", "");
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpirationException("토큰이 만료되었습니다");
        } catch (JwtException e) {
            throw new TokenNotValidException("토큰이 유효하지 않습니다.");
        }
    }

    // 토큰 값으로 유저 이메일 조회
    public String getUserEmail(String token, String secret){
        return extractAllClaims(token, secret).get(TokenClaimName.USER_EMAIL.value, String.class);
    }

    // 토큰 타입 확인
    public String getTokenType(String token, String secret) {
        return extractAllClaims(token, secret).get(TokenClaimName.TOKEN_TYPE.value, String.class);
    }

    public ZonedDateTime getExpiredAtToken(String token, String secret) {
        return ZonedDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRE_TIME);
    }

    // AccessToken 토큰 생성
    public String generatedAccessToken(String email) {
        return generateToken(email, TokenType.ACCESS_TOKEN, jwtProperties.getAccessSecret(), ACCESS_TOKEN_EXPIRE_TIME);
    }
    // RefreshToken 토큰 생성
    public String generatedRefreshToken(String email) {
        return generateToken(email, TokenType.REFRESH_TOKEN, jwtProperties.getRefreshSecret(), REFRESH_TOKEN_EXPIRE_TIME);
    }
    public UsernamePasswordAuthenticationToken authentication(String userEmail) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }




}