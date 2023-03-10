package com.example.demo.domain.notification.presentation;

import com.example.demo.domain.notification.presentation.response.NotificationResponse;
import com.example.demo.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications() {
        List<NotificationResponse> responses = notificationService.getList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PatchMapping("/{notificationId}")
    public ResponseEntity<Void> checkRead(@PathVariable Long notificationId) {
        notificationService.read(notificationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.delete(notificationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
