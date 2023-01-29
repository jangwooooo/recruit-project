package com.example.demo.domain.board.presentation.dto.response;

import com.example.demo.domain.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private Long boardId;
    private String writer;
    private String content;
    private LocalDate createdAt;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.boardId = comment.getBoardId();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
