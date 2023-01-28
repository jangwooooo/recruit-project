package com.example.demo.domain.board.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {

    private Long boardId;
    private String author;
    private String title;
    private String content;
    private String category;
    private String recruit;
    private LocalDate endDate;
    private LocalDate createdAt;
    private Boolean authority;

    public void updateAuthority(Boolean authority) {
        this.authority = authority;
    }
}
