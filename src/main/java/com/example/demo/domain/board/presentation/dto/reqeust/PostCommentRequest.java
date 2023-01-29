package com.example.demo.domain.board.presentation.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRequest {

    @NotNull
    private Long boardId;
    private String content;
}
