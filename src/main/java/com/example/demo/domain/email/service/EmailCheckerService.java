package com.example.demo.domain.email.service;

import com.example.demo.domain.email.entity.EmailAuth;
import com.example.demo.domain.email.presentation.dto.response.VerifyCheck;
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
    private boolean check;
    @Transactional(rollbackFor = Exception.class)
    public VerifyCheck execute(String email, String authKey){
        System.out.println(authKey);
        EmailAuth emailAuthEntity = emailAuthRepository.findById(email).orElseThrow(()->new UserNotFoundException("유저를 찾을 수 없습니다."));
        checkAuthKey(emailAuthEntity,authKey);
        VerifyCheck verifyCheck = new VerifyCheck(check);
        emailAuthEntity.updateAuthentication(true);
        emailAuthRepository.save(emailAuthEntity);
        return verifyCheck;
    }

    private void checkAuthKey(EmailAuth emailAuthEntity, String authKey){
        if(!Objects.equals(emailAuthEntity.getRandomValue(), authKey)){
            check = false;
            System.out.println("인증실패");
            //throw new PasswordWrongException("인증번호가 일치하지 않습니다.");
        } else {
            System.out.println("인증성공");
            check = true;
        }
    }
}