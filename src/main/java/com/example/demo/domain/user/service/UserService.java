package com.example.demo.domain.user.service;

import com.example.demo.domain.auth.presentation.dto.response.NameIsExistRes;
import com.example.demo.domain.user.presentation.dto.request.EditProfileReq;
import com.example.demo.domain.user.presentation.dto.request.PwdRequest;
import com.example.demo.domain.user.presentation.dto.response.MyPageResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.exception.PasswordWrongException;
import com.example.demo.domain.user.exception.UserNotFoundException;
import com.example.demo.domain.user.presentation.dto.response.NameIsDuplicateRes;
import com.example.demo.domain.user.presentation.dto.response.ProfileRes;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MyPageResponse myPage() {
        User user = userUtil.currentUser();
        return MyPageResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .bio(user.getBio())
                .build();
    }

//    public void deleteUser(Long id) {
//        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
//        userRepository.deleteById(id);
//    }d

    @Transactional
    public void deleteUser(PwdRequest pwdRequest){
        User user = userUtil.currentUser();
        if(!passwordEncoder.matches(pwdRequest.getPassword(), user.getPassword())) {
            throw new PasswordWrongException("비밀번호가 올바르지 않습니다.");
        }
        userRepository.delete(user);
    }

    @Transactional
    public void editProfile(EditProfileReq req) {
        User currentUser = userUtil.currentUser();
        User user = userRepository.findUserByEmail(currentUser.getEmail())
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
        user.updateNameAndBio(req.getName(), req.getBio());
    }

    @Transactional
    public void editPwd(PwdRequest pwdRequest) {
        User currentUser = userUtil.currentUser();
        User user = userRepository.findUserByEmail(currentUser.getEmail())
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
        if(!passwordEncoder.matches(pwdRequest.getPassword(), currentUser.getPassword())) {
            throw new PasswordWrongException("비밀번호가 올바르지 않습니다.");
        }
        user.updatePassword(passwordEncoder.encode(pwdRequest.getNewPassword()));
    }

    @Transactional
    public ProfileRes profile(String name) {
        User user = userRepository.findUserByName(name).orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
        System.out.println(user);
        return ProfileRes.builder()
                .name(user.getName())
                .bio(user.getBio())
                .build();
    }

    @Transactional
    public NameIsExistRes checkNameIsExist(String name) {
        NameIsExistRes nameIsExistRes = new NameIsExistRes();
        nameIsExistRes.setIsExist(userRepository.existsByName(name));
        return nameIsExistRes;
    }

    @Transactional
    public NameIsDuplicateRes checkNameDuplicate(String name) {
        User currentUser = userUtil.currentUser();
        NameIsDuplicateRes nameIsDuplicateRes = new NameIsDuplicateRes();
        if(!Objects.equals(currentUser.getName(), name)){
            nameIsDuplicateRes.setIsDuplicate(userRepository.existsByName(name));
        } else {
            nameIsDuplicateRes.setIsDuplicate(false);
        }
        return nameIsDuplicateRes;
    }
}
