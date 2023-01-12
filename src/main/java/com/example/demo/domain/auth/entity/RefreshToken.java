package com.example.demo.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash(value = "refreshToken")
public class RefreshToken {
    @Id
    private String email;
    @Indexed
    private String refreshToken;

    public void exchangeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}