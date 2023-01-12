package com.example.demo.domain.auth.service;

import com.example.demo.domain.auth.presentation.dto.request.UserSignInRequestDto;
import com.example.demo.domain.auth.presentation.dto.request.UserSignUpRequestDto;
import com.example.demo.domain.auth.presentation.dto.response.NewTokenResponse;
import com.example.demo.domain.auth.presentation.dto.response.UserSignInResponseDto;


public interface MemberService {
    void signUp (UserSignUpRequestDto requestDto);

    UserSignInResponseDto login(UserSignInRequestDto requestDto);

    void execute(String accessToken);
    NewTokenResponse tokenReissuance(String reqToken);



}