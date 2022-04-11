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
        // 디비에 유저 정보 조회 (userId 를 통한 조회).
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
//         로그인 시도하는 아이디가 존재하지 않는 경우
        if(!existsByUserAuthId(userAuthId)){
            // 404: 아이디가 존재하지 않음
            throw new IllegalStateException("존재하지 않는 아이디입니다.");
        }
        User user = getUserByUserAuthId(userAuthId);
        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if(passwordEncoder.matches(password, user.getPassword())) {
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            return JwtTokenUtil.getToken(userAuthId);
        }
        // 401: 유효하지 않는 패스워드
        throw new IllegalStateException("아이디 혹은 비밀번호를 다시 확인해주세요.");
    }

    public User findRequestUser(Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userAuthId = userDetails.getUsername();
        return getUserByUserAuthId(userAuthId);
    }

    // 유저 평점 갱신
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
