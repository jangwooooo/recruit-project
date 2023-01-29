package com.example.demo.domain.board.service;

import com.example.demo.domain.board.repository.CommentRepository;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserUtil userUtil;
}
