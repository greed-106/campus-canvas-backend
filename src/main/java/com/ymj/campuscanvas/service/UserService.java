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
    
    /**
     * 更新用户状态（封禁/解封）
     * @param userId 用户ID
     * @param status 新状态
     */
    void updateUserStatus(Long userId, User.UserStatus status);
    
    /**
     * 根据用户名搜索用户
     * @param username 用户名
     * @return 用户简要信息列表
     */
    List<UserBriefResponse> searchUsersByUsername(String username);
    
    /**
     * 获取所有用户简要信息及状态
     * @return 用户简要信息及状态列表
     */
    List<UserBriefWithStatusResponse> getAllUsersWithStatus();
}
