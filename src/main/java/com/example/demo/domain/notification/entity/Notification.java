package com.example.demo.domain.notification.entity;

import com.example.demo.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private Long boardId;
    private String generatedUser;
    private String boardAuthor;
    private Boolean isRead;

    public void updateIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
