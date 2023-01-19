package com.example.demo.domain.auth.presentation;


import com.example.demo.domain.auth.presentation.dto.request.UserSignInRequestDto;
import com.example.demo.domain.auth.presentation.dto.request.UserSignUpRequestDto;
import com.example.demo.domain.auth.presentation.dto.response.NameIsExistRes;
import com.example.demo.domain.auth.presentation.dto.response.UserSignInResponseDto;
import com.example.demo.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Validated UserSignUpRequestDto signUpDto){
         authService.signUp(signUpDto);
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/check")
    public ResponseEntity<NameIsExistRes> checkNameIsExist(@RequestParam String name) {
        NameIsExistRes data = authService.checkNameIsExist(name);
        return new ResponseEntity<>(data, HttpStatus.OK);
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
