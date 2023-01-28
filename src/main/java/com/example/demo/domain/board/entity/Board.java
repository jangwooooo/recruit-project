package com.example.demo.domain.board.entity;


import com.example.demo.domain.board.presentation.dto.reqeust.EditBoardReq;
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
    private String category;
    private String recruit;
    private String contactType;
    private String contactUs;
    private LocalDate endDate;

    public void update(EditBoardReq req) {
        this.title = req.getTitle();
        this.content = req.getContent();
        this.category = req.getCategory();
        this.recruit = req.getRecruit();
        this.contactType = req.getContactType();
        this.contactUs = req.getContactUs();
        this.endDate = req.getEndDate();
    }

    public void updateAuthor(String name) {
        this.author = name;
    }
}
