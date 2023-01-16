package com.example.demo.domain.user.presentation;

import com.example.demo.domain.user.presentation.dto.request.PwdRequest;
import com.example.demo.domain.user.presentation.dto.response.MyPageResponse;
import com.example.demo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<MyPageResponse> myPage() {
        return ResponseEntity.ok().body(userService.myPage());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody @Valid PwdRequest pwdRequest) {
        userService.deleteUser(pwdRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> editUser(@RequestBody @Valid PwdRequest pwdRequest) {
        userService.editPwd(pwdRequest);
        return ResponseEntity.ok().build();
    }
}
