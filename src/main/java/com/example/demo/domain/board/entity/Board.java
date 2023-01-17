package com.example.demo.domain.board.entity;


import com.example.demo.domain.board.presentation.dto.reqeust.EditBoardReq;
import com.example.demo.domain.board.presentation.dto.reqeust.PostBoardReq;
import com.example.demo.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    private String author;
    private String title;
    @Lob
    private String content;
    private String type;
    private String reqruit;
    private String contactType;
    private String contactUs;
    private LocalDate endDate;

    public void update(EditBoardReq req) {
        this.title = req.getTitle();
        this.content = req.getContent();
        this.type = req.getType();
        this.reqruit = req.getReqruit();
        this.contactType = req.getContactType();
        this.contactUs = req.getContactUs();
        this.endDate = req.getEndDate();
    }
}
