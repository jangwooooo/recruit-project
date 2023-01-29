package com.example.demo.domain.board.presentation.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentRequest {

    private Long commentId;
    private Long boardId;
    private String newComment;
}
