package com.example.demo.domain.user.service;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.presentation.dto.response.BoardListResponse;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.board.service.BoardService;
import com.example.demo.domain.user.exception.NameAlreadyExistException;
import com.example.demo.domain.user.presentation.dto.request.EditProfileReq;
import com.example.demo.domain.user.presentation.dto.request.PwdRequest;
import com.example.demo.domain.user.presentation.dto.response.MyPageResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.exception.PasswordWrongException;
import com.example.demo.domain.user.exception.UserNotFoundException;
import com.example.demo.domain.user.presentation.dto.response.ProfileRes;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @Transactional
    public MyPageResponse myPage() {
        User user = userUtil.currentUser();
        return MyPageResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .bio(user.getBio())
                .build();
    }

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
        if(!Objects.equals(currentUser.getName(), req.getName())) {
            if (userRepository.existsByName(req.getName())) {
                throw new NameAlreadyExistException("이미 존재하는 닉네임입니다.");
            }
        }
        List<Board> boardList = boardRepository.findBoardsByAuthor(user.getName());
        for (Board board : boardList) {
            board.updateAuthor(req.getName());
        }
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
        User user = userRepository.findUserByName(name)
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
        return ProfileRes.builder()
                .name(user.getName())
                .bio(user.getBio())
                .build();
    }

    @Transactional
    public List<BoardListResponse> getMyBoard() {
        User user = userUtil.currentUser();
        return boardRepository.findBoardsByAuthor(user.getName()).stream()
                .map(BoardListResponse::new)
                .collect(Collectors.toList());
    }
}
