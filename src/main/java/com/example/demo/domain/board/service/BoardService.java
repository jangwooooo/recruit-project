package com.example.demo.domain.board.service;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.exception.BoardNotFoundException;
import com.example.demo.domain.board.presentation.dto.reqeust.EditBoardReq;
import com.example.demo.domain.board.presentation.dto.reqeust.PostBoardReq;
import com.example.demo.domain.board.presentation.dto.response.BoardListResponse;
import com.example.demo.domain.board.presentation.dto.response.BoardResponse;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.global.exception.exceptionCollection.TokenNotValidException;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
                .category(req.getType())
                .recruit(req.getReqruit())
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

    @Transactional
    public void delete(Long boardId) {
        User user = userUtil.currentUser();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        if (!Objects.equals(user.getName(), board.getAuthor())) {
            throw new TokenNotValidException("권한이 없는 사용자입니다.");
        }
        boardRepository.delete(board);
    }

    @Transactional
    public Slice<BoardListResponse> fetchBoardPagesBy(Long lastBoardId, Integer size, String category) {
        PageRequest pageRequest = PageRequest.of(0, size); // 페이지네이션을 위한 PageRequest, 페이지는 0으로 고정한다.
        Slice<Board> boards;
        if(lastBoardId==null){
            lastBoardId = boardRepository.findFirstByOrderByBoardIdDesc().getBoardId()+1;
        }
        if(category.length()==0){
            boards = boardRepository.findAllByBoardIdLessThanOrderByBoardIdDesc(lastBoardId, pageRequest);
        } else {
            boards = boardRepository.findAllByBoardIdLessThanAndCategoryOrderByBoardIdDesc(lastBoardId, pageRequest, category);
        }

        BoardListResponse boardListResponse = new BoardListResponse();
        Slice<BoardListResponse> responses = boardListResponse.toDtoList(boards);

        return responses;
    }

    @Transactional
    public BoardResponse getBoardInfo(Long boardId) {
        User user = userUtil.currentUser();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        Boolean authority = false;
        if (Objects.equals(user.getName(), board.getAuthor())) {
            authority = true;
        }
        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .recruit(board.getRecruit())
                .endDate(board.getEndDate())
                .createdAt(board.getCreatedAt())
                .authority(authority)
                .build();
    }
}
