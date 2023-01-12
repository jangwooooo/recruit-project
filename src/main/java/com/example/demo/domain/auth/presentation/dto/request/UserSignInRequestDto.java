package com.example.demo.domain.auth.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequestDto {


    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
