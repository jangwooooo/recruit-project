package com.example.auth.domain.email.service;

import com.example.auth.domain.email.entity.EmailAuth;
import com.example.auth.domain.email.exception.EmailSendFailException;
import com.example.auth.domain.email.presentation.dto.request.EmailRequest;
import com.example.auth.domain.email.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Random;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailSendService {

    private final JavaMailSender mailSender;
    private final EmailAuthRepository emailAuthRepository;

    @Async
    @Transactional
    public void execute(EmailRequest emailRequest){

        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(88888) + 11111);

        sendAuthEmail(emailRequest.getEmail(),authKey);
    }

    private void sendAuthEmail(String email, String authKey) {
        String subject = "인증번호";
        String text = "회원 가입을 위한 인증번호는 " + authKey + "입니다. <br />";
        EmailAuth emailAuthEntity = emailAuthRepository.findById(email)
                .orElse(EmailAuth.builder()
                        .authentication(false)
                        .randomValue(authKey)
                        .attemptCount(0)
                        .email(email)
                        .build());
        emailAuthEntity.updateRandomValue(authKey);
        emailAuthEntity.increaseAttemptCount();


        emailAuthRepository.save(emailAuthEntity);
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text,true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailSendFailException("메일 발송에 실패했습니다");
        }
    }
}
