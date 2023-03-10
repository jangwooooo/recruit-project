package com.example.demo.domain.board.service;

import com.example.demo.domain.board.entity.Comment;
import com.example.demo.domain.board.exception.BoardNotFoundException;
import com.example.demo.domain.board.exception.CommentNotFound;
import com.example.demo.domain.board.presentation.dto.reqeust.DeleteCommentRequest;
import com.example.demo.domain.board.presentation.dto.reqeust.EditCommentRequest;
import com.example.demo.domain.board.presentation.dto.reqeust.PostCommentRequest;
import com.example.demo.domain.board.presentation.dto.response.CommentResponse;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.board.repository.CommentRepository;
import com.example.demo.domain.notification.service.NotificationService;
import com.example.demo.domain.user.entity.User;
import com.example.demo.global.exception.exceptionCollection.TokenNotValidException;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserUtil userUtil;
    private final BoardRepository boardRepository;
    private final NotificationService notificationService;

    @Transactional
    public void post(PostCommentRequest request) {
        User user = userUtil.currentUser();
        notificationService.add(user.getName(),request.getBoardId(),boardRepository.findAuthorByBoardId(request.getBoardId()).getAuthor());
        Comment comment = Comment.builder()
                .boardId(request.getBoardId())
                .writer(user.getName())
                .content(request.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public List<CommentResponse> getComments(Long boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("???????????? ?????? ??? ????????????."));
        return commentRepository.findCommentsByBoardId(boardId).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(EditCommentRequest request) {
        Comment comment = commentRepository.findCommentByCommentIdAndBoardId(request.getCommentId(),request.getBoardId())
                .orElseThrow(() -> new CommentNotFound("????????? ?????? ??? ????????????."));
        User user = userUtil.currentUser();
        if(!Objects.equals(user.getName(), comment.getWriter())){
            throw new TokenNotValidException("????????? ?????? ??????????????????.");
        }
        comment.updateContent(request.getNewContent());
    }

    @Transactional
    public void delete(DeleteCommentRequest request) {
        Comment comment = commentRepository.findCommentByCommentIdAndBoardId(request.getCommentId(),request.getBoardId())
                .orElseThrow(() -> new CommentNotFound("????????? ?????? ??? ????????????."));
        User user = userUtil.currentUser();
        if(!Objects.equals(user.getName(), comment.getWriter())){
            throw new TokenNotValidException("????????? ?????? ??????????????????.");
        }
        commentRepository.delete(comment);
    }
}
