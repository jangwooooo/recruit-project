package com.example.demo.domain.auth.presentation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpResponseDto {
    private String email;
    private String password;
    private String name;
    private String grade;
}
