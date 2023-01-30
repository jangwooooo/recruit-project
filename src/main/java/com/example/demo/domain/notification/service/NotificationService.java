package com.example.demo.domain.notification.service;

import com.example.demo.domain.board.presentation.dto.response.BoardListResponse;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.notification.presentation.response.NotificationResponse;
import com.example.demo.domain.notification.repository.NotificationRepository;
import com.example.demo.domain.notification.entity.Notification;
import com.example.demo.domain.user.entity.User;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserUtil userUtil;
    private final BoardRepository boardRepository;

    @Transactional
    public void add(String generatedUser, Long boardId, String author) {
        Notification notification = Notification.builder()
                .boardId(boardId)
                .generatedUser(generatedUser)
                .isRead(false)
                .boardAuthor(author)
                .build();

        notificationRepository.save(notification);
    }

    @Transactional
    public List<NotificationResponse> getList() {
        User user = userUtil.currentUser();
        return notificationRepository.findNotificationsByBoardAuthor(user.getName()).stream()
                .map(NotificationResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void read(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFound("알림을 찾을 수 없습니다."));
        notification.updateIsRead(true);
    }
}
