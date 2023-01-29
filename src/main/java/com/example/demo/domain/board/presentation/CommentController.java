package com.example.demo.domain.board.presentation;

import com.example.demo.domain.board.presentation.dto.reqeust.PostCommentRequest;
import com.example.demo.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> postComment(@RequestBody PostCommentRequest request){
        commentService.post(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
