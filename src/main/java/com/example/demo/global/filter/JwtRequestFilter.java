package com.example.demo.global.filter;

import com.example.demo.domain.auth.exception.BlackListAlreadyExistException;
import com.example.demo.global.exception.exceptionCollection.TokenNotValidException;
import com.example.demo.global.security.jwt.TokenProvider;
import com.example.demo.global.security.jwt.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");
        if(accessToken != null) {
            tokenProvider.extractAllClaims(accessToken, jwtProperties.getAccessSecret());
            System.out.printf(tokenProvider.getTokenType(accessToken,jwtProperties.getAccessSecret()));
            if (!tokenProvider.getTokenType(accessToken, jwtProperties.getAccessSecret()).equals("ACCESS_TOKEN"))
                throw new TokenNotValidException("Token is not valid");
            else if(redisTemplate.opsForValue().get(accessToken)!=null)
                throw new BlackListAlreadyExistException("블랙리스트에 이미 등록되어있습니다.");
            String email = tokenProvider.getUserEmail(accessToken, jwtProperties.getAccessSecret());
            registerSecurityContext(request, email);
        }

        filterChain.doFilter(request, response);
    }

    private void registerSecurityContext(HttpServletRequest request, String email) {
        UsernamePasswordAuthenticationToken authenticationToken = tokenProvider.authentication(email);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}