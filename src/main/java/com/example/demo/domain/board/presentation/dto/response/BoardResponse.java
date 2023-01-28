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
public class BoardResponse {

    private Long boardId;
    private String author;
    private String title;
    private String content;
    private String type;
    private String reqruit;
    private LocalDate endDate;
    private LocalDate createdAt;

    public Slice<BoardResponse> toDtoList(Slice<Board> boardList){
        Slice<BoardResponse> boardDtoList = boardList.map(m -> BoardResponse.builder()
                .boardId(m.getBoardId())
                .type(m.getType())
                .title(m.getTitle())
                .content(m.getContent())
                .author(m.getAuthor())
                .reqruit(m.getReqruit())
                .endDate(m.getEndDate())
                .createdAt(m.getCreatedAt())
                .build());
        return boardDtoList;
    }

}