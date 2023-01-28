package com.example.demo.domain.board.presentation;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.presentation.dto.reqeust.EditBoardReq;
import com.example.demo.domain.board.presentation.dto.reqeust.PostBoardReq;
import com.example.demo.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
@CrossOrigin
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> postBoard(@RequestBody @Valid PostBoardReq req) {
        boardService.post(req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> editBoard(@RequestBody @Valid EditBoardReq req) {
        boardService.edit(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Slice<Board>> getBoardList(@RequestParam Long lastBoardId, @RequestParam int size, @RequestParam String type) {
        Slice<Board> boardListRes = boardService.fetchBoardPagesBy(lastBoardId, size, type);
        return new ResponseEntity<>(boardListRes, HttpStatus.OK);
    }
}
