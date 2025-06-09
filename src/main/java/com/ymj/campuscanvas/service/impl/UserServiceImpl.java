package com.ymj.campuscanvas.service.impl;

import com.ymj.campuscanvas.exception.DuplicateRegisterException;
import com.ymj.campuscanvas.exception.NotFoundException;
import com.ymj.campuscanvas.mapper.UserMapper;
import com.ymj.campuscanvas.pojo.DTO.*;
import com.ymj.campuscanvas.pojo.User;
import com.ymj.campuscanvas.service.UserService;
import com.ymj.campuscanvas.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            throw new DuplicateRegisterException("Username or email already exists");
        }
    }

    @Override
    @CacheEvict(value = "userProfile", key = "#user.id")
    public void updateUser(User user) {
        if(user.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        if(userMapper.selectUserById(user.getId()) == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        userMapper.updateUser(user);
    }

    @Override
    public LoginResponse loginCheck(LoginRequest user) {
        User userByUsername = userMapper.selectUserByUsername(user.getUsername());
        if(userByUsername == null) {
            throw new IllegalArgumentException("Username does not exist");
        }
        if(!userByUsername.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        if(userByUsername.getStatus() == User.UserStatus.DISABLED){
            throw new IllegalArgumentException("Account has been disabled");
        }
        if(userByUsername.getStatus() == User.UserStatus.DELETED){
            throw new IllegalArgumentException("Account has been deleted");
        }

        try {
            Long id = userByUsername.getId();
            String token = JwtUtil.generateToken(id);
            return new LoginResponse(token, id);
        } catch (Exception e) {
            log.error("Error generating token: {}", e.getMessage());
            throw new RuntimeException("Failed to create token");
        }

    }

    @Override
    public void checkUsernameExist(String username) {
        if(isUsernameExist(username)){
            throw new DuplicateRegisterException("Username already exists");
        }
    }
    @Override
    public void checkUserIdExist(Long id) {
        if(userMapper.selectUserById(id) == null){
            throw new NotFoundException("User does not exist");
        }
    }

    @Override
    public void checkEmailExist(String email) {
        if(isEmailExist(email)){
            throw new DuplicateRegisterException("Email already exists");
        }
    }

    @Override
    @Cacheable(value = "userProfile", key = "#id")
    public UserProfileResponse getUserProfile(Long id) {
        User user = userMapper.selectUserById(id);
        if(user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        return user.toUserProfileDTO();
    }
    @Override
    @Cacheable(value = "userBrief", key = "#id")
    public UserBriefResponse getUserBriefProfile(Long id) {
        User user = userMapper.selectUserById(id);
        if(user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        return user.toUserBriefDTO();
    }

    @Override
    public Map<Long, UserBriefResponse> getUserBriefProfilesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        // 1. 去重
        Set<Long> uniqueIds = new HashSet<>(ids);
        List<User> users = userMapper.selectUsersByIds(new ArrayList<>(uniqueIds));

        // 2. 构建ID到UserBriefResponse的映射
        Map<Long, UserBriefResponse> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, User::toUserBriefDTO));
        // 3. 判断是否有缺失的ID
        for (Long id : uniqueIds) {
            if (!userMap.containsKey(id)) {
                throw new NotFoundException("User with ID: " + id + " not found");
            }
        }
        return userMap;
    }

    @Override
    public Map<Long, UserProfileResponse> getUserProfilesByIds(List<Long> ids) {
        if(ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }
        Set<Long> uniqueIds = new HashSet<>(ids);
        List<User> users = userMapper.selectUsersByIds(new ArrayList<>(uniqueIds));
        if(users == null || users.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, UserProfileResponse> userProfiles = users.stream()
                .collect(Collectors.toMap(User::getId, User::toUserProfileDTO));
        return userProfiles;
    }

    public boolean isUsernameExist(String username) {
        User userByUsername = userMapper.selectUserByUsername(username);
        return userByUsername != null;
    }

    public boolean isEmailExist(String email) {
        User userByEmail = userMapper.selectUserByEmail(email);
        return userByEmail != null;
    }
    
    @Override
    @CacheEvict(value = "userProfile", key = "#userId")
    public void updateUserStatus(Long userId, User.UserStatus status) {
        log.info("Updating user status: userId={}, status={}", userId, status);
        
        // 检查用户是否存在
        checkUserIdExist(userId);
        
        // 更新用户状态
        userMapper.updateUserStatus(userId, status);
        
        log.info("User status updated successfully: userId={}, newStatus={}", userId, status);
    }
}
