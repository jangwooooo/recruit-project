package com.example.demo.domain.auth.presentation.dto.request;


import com.example.demo.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequestDto {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotNull
    private String bio;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .bio(bio)
                .build();
    }


}
