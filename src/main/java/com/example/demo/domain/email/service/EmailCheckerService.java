package com.example.demo.domain.email.service;

import com.example.demo.domain.email.entity.EmailAuth;
import com.example.demo.domain.email.exception.AuthCodeMismatchException;
import com.example.demo.domain.email.repository.EmailAuthRepository;
import com.example.demo.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailCheckerService {

    private final EmailAuthRepository emailAuthRepository;

    @Transactional
    public void execute(String email, String authKey){
        EmailAuth emailAuthEntity = emailAuthRepository.findById(email).orElseThrow(()->new UserNotFoundException("유저를 찾을 수 없습니다."));
        checkAuthKey(emailAuthEntity,authKey);
        emailAuthEntity.updateAuthentication(true);
        emailAuthRepository.save(emailAuthEntity);
    }

    private void checkAuthKey(EmailAuth emailAuthEntity, String authKey){
        if(!Objects.equals(emailAuthEntity.getRandomValue(), authKey)){
            throw new AuthCodeMismatchException("인증번호가 일치하지 않습니다.");
        }
    }
}