package com.example.demo.domain.notification.presentation.response;

import com.example.demo.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long notificationId;
    private Long boardId;
    private String title;
    private Boolean isRead;
    private LocalDate createdAt;

    public NotificationResponse(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.boardId = notification.getBoardId();
        this.title = notification.getGeneratedUser();
        this.isRead = notification.getIsRead();
        this.createdAt = notification.getCreatedAt();
    }
}
