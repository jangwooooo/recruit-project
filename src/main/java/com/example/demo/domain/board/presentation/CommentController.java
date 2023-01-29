package com.example.demo.domain.board.presentation;

import com.example.demo.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;
}
