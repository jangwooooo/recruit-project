package com.example.demo.domain.board.service;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.exception.BoardNotFoundException;
import com.example.demo.domain.board.presentation.dto.reqeust.PostAndEditReq;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.exception.UserNotFoundException;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserUtil userUtil;

    @Transactional
    public void post(PostAndEditReq req) {
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
    public void edit(PostAndEditReq req) {
        User currentUser = userUtil.currentUser();
        Board board = boardRepository.findBoardByAuthor(currentUser.getName())
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        board.update(req, currentUser.getName());
    }
}
