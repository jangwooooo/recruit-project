package com.example.demo.domain.board.presentation.dto.response;

import com.example.demo.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResponse {

    private Long boardId;
    private String author;
    private String title;
    private String category;
    private String recruit;
    private LocalDate endDate;
    private LocalDate createdAt;

    public BoardListResponse(Board board) {
        this.boardId = board.getBoardId();
        this.author = board.getAuthor();
        this.title = board.getTitle();
        this.category = board.getCategory();
        this.recruit = board.getRecruit();
        this.endDate = board.getEndDate();
        this.createdAt = board.getCreatedAt();
    }

    public Slice<BoardListResponse> toDtoList(Slice<Board> boardList){
        Slice<BoardListResponse> boardDtoList = boardList.map(m -> BoardListResponse.builder()
                .boardId(m.getBoardId())
                .category(m.getCategory())
                .title(m.getTitle())
                .author(m.getAuthor())
                .recruit(m.getRecruit())
                .endDate(m.getEndDate())
                .createdAt(m.getCreatedAt())
                .build());
        return boardDtoList;
    }

}
