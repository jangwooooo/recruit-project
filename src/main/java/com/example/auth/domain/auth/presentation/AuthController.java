package com.example.auth.domain.auth.presentation;

import com.example.auth.domain.auth.presentation.dto.SignUpRequest;
import com.example.auth.domain.auth.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        signUpService.signUp(signUpRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
