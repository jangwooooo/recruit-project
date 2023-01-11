//package com.example.jobgsm.global.security.auth;
//
//import com.example.jobgsm.domain.user.exception.UserNotFoundException;
//import com.example.jobgsm.domain.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class AuthDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UserDetails loadUserByUsername(String email) {
//        return userRepository.findUserByEmail(email)
//                .map(AuthDetails::new)
//                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
//    }
//}