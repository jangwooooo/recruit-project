package com.example.demo.domain.board.presentation.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostAndEditReq {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String type;
    @NotBlank
    private String reqruit;
    @NotBlank
    private String contactType;
    @NotBlank
    private String contactUs;
    private LocalDate endDate;
}
