package com.example.demo.domain.board.presentation;

import com.example.demo.domain.board.presentation.dto.reqeust.DeleteCommentRequest;
import com.example.demo.domain.board.presentation.dto.reqeust.EditCommentRequest;
import com.example.demo.domain.board.presentation.dto.reqeust.PostCommentRequest;
import com.example.demo.domain.board.presentation.dto.response.CommentResponse;
import com.example.demo.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> postComment(@RequestBody @Valid PostCommentRequest request){
        commentService.post(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@RequestParam long boardId) {
        List<CommentResponse> responseList = commentService.getComments(boardId);
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Void> edit(@RequestBody EditCommentRequest request) {
        commentService.edit(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteCommentRequest request) {
        commentService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
