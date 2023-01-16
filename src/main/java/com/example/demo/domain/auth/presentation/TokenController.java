package com.example.demo.domain.auth.presentation;

import com.example.demo.domain.auth.presentation.dto.response.NewTokenResponse;
import com.example.demo.domain.auth.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final MemberServiceImpl memberService;

    @GetMapping("/token/reissue")
    public ResponseEntity<NewTokenResponse> reIssueToken(@RequestHeader("RefreshToken") String refreshToken) {
        NewTokenResponse reIssueToken = memberService.tokenReissuance(refreshToken);
        return new ResponseEntity<>(reIssueToken, HttpStatus.OK);
    }
}
