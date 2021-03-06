package com.studeveloper.user.service;

import com.studeveloper.auth.dto.AuthRequestDto;
import com.studeveloper.auth.dto.AuthResponseDto;
import com.studeveloper.auth.utils.JwtTokenUtil;
import com.studeveloper.auth.utils.SsafyUserDetails;
import com.studeveloper.category.domain.Category;
import com.studeveloper.study.domain.Study;
import com.studeveloper.study.domain.UserStudy;
import com.studeveloper.user.domain.*;
import com.studeveloper.user.dto.*;
import com.studeveloper.user.utils.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositorySupport userRepositorySupport;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserResponseDto save(UserRequestDto userRequestDto) {
        validSignupAuthId(userRequestDto.getUserAuthId());
        String encryptedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto.getUserAuthId(), encryptedPassword, userRequestDto.getName());
        userRepository.save(user);
        return UserConverter.toDto(user);
    }

    public User findById(Long userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        return user;
    }

    public UserResponseDto update(Authentication authentication, Long userId, UserUpdateDto userUpdateDto) {
        User user =  userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        checkAllowedUser(authentication, user);
        user.update(userUpdateDto);
        userRepository.save(user);
        return UserConverter.toDto(user);

    }

    public void delete(Authentication authentication, Long userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        checkAllowedUser(authentication, user);
        userRepository.delete(user);
    }

    public List<Study> findParticipatingStudy(Long userId) {
        User user = findById(userId);
        List<UserStudy> userStudies = user.getParticipatingStudies();
        return userStudies.stream()
                .map(UserStudy::getStudy)
                .collect(Collectors.toList());
    }

    public List<Category> findInterestCategories(Long userId) {
        User user = findById(userId);
        List<UserInterestCategory> interestCategories = user.getInterestCategories();
        return interestCategories.stream()
                .map(UserInterestCategory::getCategory)
                .collect(Collectors.toList());
    }


    public User getUserByUserAuthId(String userId) {
        // ????????? ?????? ?????? ?????? (userId ??? ?????? ??????).
        User user = userRepository.findByUserAuthId(userId).
                orElseThrow(RuntimeException::new);
        return user;
    }

    public UserCheckInfoDto checkInfo(Authentication authentication) {
        User user = findRequestUser(authentication);
        return new UserCheckInfoDto(user.getId(), user.getName());
    }

    public void checkAllowedUser(Authentication authentication, User user) {
        User requestUser = findRequestUser(authentication);
        requestUser.isAllowedUser(user);
    }

    public boolean existsByUserAuthId(String userAuthId) { return userRepository.existsByUserAuthId(userAuthId);}

    public void validSignupAuthId(String authId) {
        if (existsByUserAuthId(authId)) {
            throw new IllegalStateException();
        }
    }

    public AuthResponseDto login(AuthRequestDto requestDto) {
        String userAuthId = requestDto.getUserAuthId();
        String password = requestDto.getPassword();
        String accessToken = getAccessToken(userAuthId, password);
        User user = getUserByUserAuthId(userAuthId);
        return new AuthResponseDto(user.getId(), user.getName(), accessToken);
    }

    public String getAccessToken(String userAuthId, String password) {
//         ????????? ???????????? ???????????? ???????????? ?????? ??????
        if(!existsByUserAuthId(userAuthId)){
            // 404: ???????????? ???????????? ??????
            throw new IllegalStateException("???????????? ?????? ??????????????????.");
        }
        User user = getUserByUserAuthId(userAuthId);
        // ????????? ????????? ??????????????? ????????? ???????????? ??? ????????? ????????? ????????? ???????????? ??????????????? ????????? ??????.(????????? ?????????????????? ?????? ??????)
        if(passwordEncoder.matches(password, user.getPassword())) {
            // ????????? ??????????????? ?????? ??????, ????????? ???????????? ??????.(????????? ????????? ???????????? ????????? ??????)
            return JwtTokenUtil.getToken(userAuthId);
        }
        // 401: ???????????? ?????? ????????????
        throw new IllegalStateException("????????? ?????? ??????????????? ?????? ??????????????????.");
    }

    public User findRequestUser(Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userAuthId = userDetails.getUsername();
        return getUserByUserAuthId(userAuthId);
    }

    // ?????? ?????? ??????
    public void updateScores(List<UserScoreDto> userScoreDtoList) {
        for (UserScoreDto userScoreDto : userScoreDtoList) {
            this.updateUserScore(userScoreDto.getUserId(), userScoreDto.getUserScore());
        }
    }

    public User updateUserScore(Long userId, double userScore) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        user.updateScore(userScore);
        userRepository.save(user);
        return user;
    }
}
