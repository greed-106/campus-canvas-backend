package com.ymj.campuscanvas.service;

import com.ymj.campuscanvas.pojo.DTO.*;
import com.ymj.campuscanvas.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void insertUser(User user);
    void updateUser(User user);
    LoginResponse loginCheck(LoginRequest user);
    void checkUsernameExist(String username);
    void checkUserIdExist(Long id);
    void checkEmailExist(String email);
    UserProfileResponse getUserProfile(Long id);

    UserBriefResponse getUserBriefProfile(Long id);

    Map<Long, UserBriefResponse> getUserBriefProfilesByIds(List<Long> ids);

    Map<Long, UserProfileResponse> getUserProfilesByIds(List<Long> ids);
}
