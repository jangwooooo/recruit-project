package com.example.auth.domain.auth.service;

import com.example.auth.domain.auth.entity.Member;
import com.example.auth.domain.auth.exception.EmailAlreadyExistException;
import com.example.auth.domain.auth.exception.NotVerifyEmailException;
import com.example.auth.domain.auth.presentation.dto.SignUpRequest;
import com.example.auth.domain.auth.repository.MemberRepository;
import com.example.auth.domain.email.entity.EmailAuth;
import com.example.auth.domain.email.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest signUpRequest){
        if(memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyExistException("이메일이 이미 DB에 존재 합니다.");
        }
        EmailAuth emailAuth = emailAuthRepository.findById(signUpRequest.getEmail())
                .orElseThrow(() -> new NotVerifyEmailException("이메일이 인증되지 않았습니다."));
        if(!emailAuth.getAuthentication()) {
            throw new NotVerifyEmailException("이메일이 인증되지 않았습니다");
        }
        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .build();

        memberRepository.save(member);
    }
}
