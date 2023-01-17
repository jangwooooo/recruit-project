package com.example.demo.domain.auth.presentation;


import com.example.demo.domain.auth.presentation.dto.request.UserSignInRequestDto;
import com.example.demo.domain.auth.presentation.dto.request.UserSignUpRequestDto;
import com.example.demo.domain.auth.presentation.dto.response.UserSignInResponseDto;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.domain.user.presentation.dto.response.NameCheckRes;
import com.example.demo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Validated UserSignUpRequestDto signUpDto){
         authService.signUp(signUpDto);
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/check")
    public ResponseEntity<NameCheckRes> checkSignupNameDuplicate(@RequestParam String name) {
        NameCheckRes isExist = userService.checkNameIsExist(name);
        return new ResponseEntity<>(isExist, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserSignInResponseDto> login(@RequestBody @Validated UserSignInRequestDto signInDto) {
        UserSignInResponseDto data = authService.login(signInDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("authorization") String accessToken){
        authService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
