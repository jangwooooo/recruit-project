package com.example.demo.domain.board.repository;

import com.example.demo.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByBoardId(Long boardId);
}
