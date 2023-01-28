package com.example.demo.domain.board.service;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.exception.BoardNotFoundException;
import com.example.demo.domain.board.presentation.dto.reqeust.EditBoardReq;
import com.example.demo.domain.board.presentation.dto.reqeust.PostBoardReq;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.global.exception.exceptionCollection.TokenNotValidException;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserUtil userUtil;

    @Transactional
    public void post(PostBoardReq req) {
        User user = userUtil.currentUser();
        Board board = Board.builder()
                .author(user.getName())
                .title(req.getTitle())
                .content(req.getContent())
                .type(req.getType())
                .reqruit(req.getReqruit())
                .contactType(req.getContactType())
                .contactUs(req.getContactUs())
                .endDate(req.getEndDate())
                .build();
        boardRepository.save(board);
    }

    @Transactional
    public void edit(EditBoardReq req) {
        User user = userUtil.currentUser();
        Board board = boardRepository.findById(req.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        if (!Objects.equals(user.getName(), board.getAuthor())) {
            throw new TokenNotValidException("권한이 없는 사용자입니다.");
        }
        board.update(req);
    }

    public void delete(Long boardId) {
        User user = userUtil.currentUser();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        if (!Objects.equals(user.getName(), board.getAuthor())) {
            throw new TokenNotValidException("권한이 없는 사용자입니다.");
        }
        boardRepository.delete(board);
    }
}
