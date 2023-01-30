package com.example.demo.domain.board.repository;

import com.example.demo.domain.board.entity.Board;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findBoardsByAuthor(String name);
    Slice<Board> findAllByBoardIdLessThanAndCategoryOrderByBoardIdDesc(Long lastBoardId, PageRequest pageRequest, String category);
    Slice<Board> findAllByBoardIdLessThanOrderByBoardIdDesc(Long lastBoardId, PageRequest pageRequest);
    Board findFirstByOrderByBoardIdDesc();
    Optional<Board> findBoardIdsByAuthor(String name);
    Board findAuthorByBoardId(Long boardId);
}
