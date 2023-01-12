package com.example.auth.domain.email.service;

import com.example.auth.domain.auth.exception.UserNotFoundException;
import com.example.auth.domain.email.entity.EmailAuth;
import com.example.auth.domain.email.exception.AuthCodeMismatchException;
import com.example.auth.domain.email.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailCheckService {
    private final EmailAuthRepository emailAuthRepository;

    @Transactional
    public void execute(String email,String authKey){
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
