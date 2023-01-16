package com.example.demo.domain.auth.presentation;


import com.example.demo.domain.auth.presentation.dto.request.UserSignInRequestDto;
import com.example.demo.domain.auth.presentation.dto.request.UserSignUpRequestDto;
import com.example.demo.domain.auth.presentation.dto.response.NewTokenResponse;
import com.example.demo.domain.auth.presentation.dto.response.UserSignInResponseDto;
import com.example.demo.domain.auth.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final MemberServiceImpl memberService;


    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Validated UserSignUpRequestDto signUpDto){
         memberService.signUp(signUpDto);
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/login")
    public UserSignInResponseDto login(@RequestBody @Validated UserSignInRequestDto signInDto) {
        return memberService.login(signInDto);
    }
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("authorization") String accessToken){
        memberService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
