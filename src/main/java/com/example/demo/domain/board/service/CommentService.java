package com.example.demo.domain.board.service;

import com.example.demo.domain.board.entity.Comment;
import com.example.demo.domain.board.presentation.dto.reqeust.PostCommentRequest;
import com.example.demo.domain.board.presentation.dto.response.CommentResponse;
import com.example.demo.domain.board.repository.CommentRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserUtil userUtil;

    @Transactional
    public void post(PostCommentRequest request) {
        User user = userUtil.currentUser();
        Comment comment = Comment.builder()
                .boardId(request.getBoardId())
                .writer(user.getName())
                .content(request.getContent())
                .build();

        commentRepository.save(comment);
    }
}
