package com.example.demo.domain.email.presentation;

import com.example.demo.domain.email.service.EmailCheckerService;
import com.example.demo.domain.email.service.EmailSenderService;
import com.example.demo.domain.email.presentation.dto.request.EmailSentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;


@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailSenderService emailSenderService;
    private final EmailCheckerService emailCheckerService;


    @PostMapping
    public ResponseEntity<Void> authEmail(@RequestBody @Valid EmailSentDto emailSentDto) {
        emailSenderService.execute(emailSentDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
        public ResponseEntity<Void> mailVerify(@Email @RequestParam String email, @RequestParam String authKey){
            emailCheckerService.execute(email, authKey);
            return ResponseEntity.ok().build();
    }
}
