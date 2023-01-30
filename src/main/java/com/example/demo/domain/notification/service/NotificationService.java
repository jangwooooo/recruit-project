package com.example.demo.domain.notification.service;

import com.example.demo.domain.notification.repository.NotificationRepository;
import com.example.demo.domain.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void add(String generatedUser, Long boardId) {
        Notification notification = Notification.builder()
                .boardId(boardId)
                .generatedUser(generatedUser)
                .isRead(false)
                .build();

        notificationRepository.save(notification);
    }
}
