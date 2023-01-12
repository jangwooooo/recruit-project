package com.example.demo.domain.email.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class EmailSentDto {
    @Email
    @NotBlank(message = "이메일(필수)")
    private String email;

    @JsonCreator
    public EmailSentDto(@JsonProperty("email") String email) {
        this.email = email;
    }
}