package com.example.demo.domain.auth.service;

import com.example.demo.domain.auth.entity.BlackList;
import com.example.demo.domain.auth.entity.RefreshToken;
import com.example.demo.domain.auth.exception.*;
import com.example.demo.domain.auth.presentation.dto.request.UserSignInRequestDto;
import com.example.demo.domain.auth.presentation.dto.request.UserSignUpRequestDto;
import com.example.demo.domain.auth.presentation.dto.response.NameIsExistRes;
import com.example.demo.domain.auth.presentation.dto.response.NewTokenResponse;
import com.example.demo.domain.auth.presentation.dto.response.UserSignInResponseDto;
import com.example.demo.domain.auth.repository.BlackListRepository;
import com.example.demo.domain.auth.repository.RefreshTokenRepository;
import com.example.demo.domain.email.entity.EmailAuth;
import com.example.demo.domain.email.repository.EmailAuthRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.exception.PasswordWrongException;
import com.example.demo.domain.user.exception.UserNotFoundException;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.exceptionCollection.TokenNotValidException;
import com.example.demo.global.security.jwt.TokenProvider;
import com.example.demo.global.security.jwt.properties.JwtProperties;
import com.example.demo.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final BlackListRepository blackListRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final RedisTemplate redisTemplate;
    private final EmailAuthRepository emailAuthRepository;



    @Transactional
    public void signUp(UserSignUpRequestDto signUpDto) {

        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EmailAlreadyExistException("???????????? ?????? DB??? ?????? ?????????.");
        }
        EmailAuth emailAuth = emailAuthRepository.findById(signUpDto.getEmail())
                .orElseThrow(() -> new NotVerifyEmailException("???????????? ???????????? ???????????????."));
        if(!emailAuth.getAuthentication()) {
            throw new NotVerifyEmailException("???????????? ???????????? ???????????????");
        }
        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .name(signUpDto.getName())
                .bio(signUpDto.getBio())
                .build();
        userRepository.save(user);

    }

    @Transactional
    public NameIsExistRes checkNameIsExist(String name) {
        NameIsExistRes nameIsExistRes = new NameIsExistRes();
        nameIsExistRes.setIsExist(userRepository.existsByName(name));
        return nameIsExistRes;
    }

    @Transactional
    public UserSignInResponseDto login(UserSignInRequestDto signInDto) {
        User user = userRepository.findUserByEmail(signInDto.getEmail()).orElseThrow(() -> new UserNotFoundException("????????? ?????? ???????????????."));
               if(!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new PasswordWrongException("??????????????? ???????????? ????????????.");
        }
        String accessToken = tokenProvider.generatedAccessToken(signInDto.getEmail());
        String refreshToken = tokenProvider.generatedRefreshToken(signInDto.getEmail());
        RefreshToken entityToRedis = new RefreshToken(signInDto.getEmail(), refreshToken, tokenProvider.getREFRESH_TOKEN_EXPIRE_TIME());
        refreshTokenRepository.save(entityToRedis);
        return UserSignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(tokenProvider.getExpiredAtToken(accessToken, jwtProperties.getAccessSecret()))
                .build();
    }

    @Transactional
    public void logout(String accessToken){
        User user = userUtil.currentUser();
        RefreshToken refreshToken = refreshTokenRepository.findById(user.getEmail()).orElseThrow(()->new RefreshTokenNotFoundException("refreshToken ??? ?????? ??? ????????????."));
        refreshTokenRepository.delete(refreshToken);
        saveBlackList(user.getEmail(),accessToken);
    }

    private void saveBlackList(String email, String accessToken){
        if(redisTemplate.opsForValue().get(accessToken)!=null){
            throw new BlackListAlreadyExistException("?????????????????? ?????? ????????????????????????.");
        }
        ZonedDateTime accessTokenExpire = tokenProvider.getExpiredAtToken(accessToken,jwtProperties.getAccessSecret());
        BlackList blackList = BlackList.builder()
                .email(email)
                .accessToken(accessToken)
                .build();
        blackListRepository.save(blackList);
    }

    @Transactional
    public NewTokenResponse tokenReissuance(String reqToken) {
        String email = tokenProvider.getUserEmail(reqToken, jwtProperties.getRefreshSecret());
        RefreshToken token = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new RefreshTokenNotFoundException("???????????? ?????? refreshToken ?????????"));
        if(!token.getRefreshToken().equals(reqToken)) {
            throw new TokenNotValidException("????????? ???????????? ????????????");
        }
        String accessToken = tokenProvider.generatedAccessToken(email);
        String refreshToken = tokenProvider.generatedRefreshToken(email);
        token.exchangeRefreshToken(refreshToken);
        refreshTokenRepository.save(token);
        return NewTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}