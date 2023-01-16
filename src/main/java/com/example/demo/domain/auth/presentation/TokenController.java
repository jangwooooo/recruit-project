package com.example.demo.domain.auth.presentation;

import com.example.demo.domain.auth.presentation.dto.response.NewTokenResponse;
import com.example.demo.domain.auth.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/token")
@RestController
public class TokenController {

    private final MemberServiceImpl memberService;

    @GetMapping("/reissue")
    public ResponseEntity<NewTokenResponse> reIssueToken(@RequestHeader("RefreshToken") String refreshToken) {
        NewTokenResponse reIssueToken = memberService.tokenReissuance(refreshToken);
        return new ResponseEntity<>(reIssueToken, HttpStatus.OK);
    }
}
