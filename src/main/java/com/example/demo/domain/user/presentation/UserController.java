package com.example.demo.domain.user.presentation;

import com.example.demo.domain.user.presentation.dto.request.EditProfileReq;
import com.example.demo.domain.user.presentation.dto.request.PwdRequest;
import com.example.demo.domain.user.presentation.dto.response.MyPageResponse;
import com.example.demo.domain.user.presentation.dto.response.ProfileRes;
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
        MyPageResponse data = userService.myPage();
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody @Valid PwdRequest pwdRequest) {
        userService.deleteUser(pwdRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> editProfile(@RequestBody @Valid EditProfileReq editProfileReq) {
        userService.editProfile(editProfileReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/edit/pwd")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid PwdRequest pwdRequest) {
        userService.editPwd(pwdRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileRes> profile(@RequestParam String name) {
        ProfileRes data = userService.profile(name);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
