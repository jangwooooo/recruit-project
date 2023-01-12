package com.example.auth.domain.email.presentation;

import com.example.auth.domain.email.presentation.dto.request.EmailRequest;
import com.example.auth.domain.email.service.EmailCheckService;
import com.example.auth.domain.email.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {

    private final EmailSendService emailSendService;
    private final EmailCheckService emailCheckService;

    @PostMapping
    public ResponseEntity<Void> authEmail(@RequestBody @Valid EmailRequest emailRequest) {
        emailSendService.execute(emailRequest);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Void> emailVerify(@Email @RequestParam String email, @RequestParam String authKey) {
        emailCheckService.execute(email, authKey);
        return ResponseEntity.ok().build();
    }
}
