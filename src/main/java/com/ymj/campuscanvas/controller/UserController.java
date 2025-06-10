package com.ymj.campuscanvas.controller;

import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.pojo.DTO.*;
import com.ymj.campuscanvas.pojo.Post;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.pojo.User;
import com.ymj.campuscanvas.service.*;
import com.ymj.campuscanvas.utils.RedisKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    VerificationCodeService verificationCodeService;
    @Autowired
    PostCompositeService postCompositeService;
    @Autowired
    UserCompositeService userCompositeService;
    @Autowired
    FollowService followService;

    @PostMapping
    public Result register(@RequestBody RegisterRequest request) {
        User user = request.toUser();
        String code = request.getCode();
        log.info("request: {}", request);
        verificationCodeService.verifyCode(RedisKeys.VERIFICATION_CODE_PREFIX, user.getEmail(), code);
        userService.insertUser(user);
        UserProfileResponse profile = user.toUserProfileDTO();
        log.info(profile.toString());
        return Result.success(profile);
    }

    @GetMapping("/check/username")
    public Result checkUsername(@RequestParam String username) {
        userService.checkUsernameExist(username);
        return Result.success();
    }

    @GetMapping("/check/email")
    public Result checkEmail(@RequestParam String email) {
        userService.checkEmailExist(email);
        return Result.success();
    }

    @PutMapping
    public Result updateUserProfile(@RequestBody UpdateUserProfileRequest request) {
        userService.updateUser(request.toUser());
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getUserProfile(@PathVariable Long id) {
        UserProfileResponse profile = userService.getUserProfile(id);
        return Result.success(profile);
    }

    @GetMapping("/{userId}/liked-posts")
    public Result getUserLikedPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PageInfo<GetPostResponse> posts = postCompositeService.selectPostsByUserLiked(userId, pageNum, pageSize);
        return Result.success(posts);
    }

    @GetMapping("/{userId}/posts")
    public Result getPostsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        PageInfo<GetPostResponse> posts = postCompositeService.selectPostsByUserId(userId, pageNum, pageSize, sortBy, sortOrder);
        return Result.success(posts);
    }

    // 获取用户关注列表
    @GetMapping("/{userId}/followings")
    public Result getFollowingList(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PageInfo<UserBriefResponse> followingList = userCompositeService.selectFollowingBriefsByUserId(userId, pageNum, pageSize);
        return Result.success(followingList);
    }

    // 获取用户粉丝列表
    @GetMapping("/{userId}/followers")
    public Result getFollowerList(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PageInfo<UserBriefResponse> followerList = userCompositeService.selectFollowerBriefsByUserId(userId, pageNum, pageSize);
        return Result.success(followerList);
    }

    @GetMapping("/{userId}/follow-counts")
    public Result getFollowCounts(
            @PathVariable Long userId
    ) {
        GetFollowCountsResponse response = followService.getFollowCounts(userId);
        return Result.success(response);
    }

    @GetMapping("/{userId}/favorite-posts")
    public Result getUserFavoritePosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PageInfo<GetPostResponse> posts = postCompositeService.selectPostsByUserFavorite(userId, pageNum, pageSize);
        return Result.success(posts);
    }
    
    /**
     * 更新用户状态（封禁/解封）
     * @param request 用户状态请求
     * @return 操作结果
     */
    @PutMapping("/status")
    public Result updateUserStatus(@RequestBody UserStatusRequest request) {
        log.info("Updating user status: userId={}, status={}, reason={}", 
                request.getUserId(), request.getStatus(), request.getReason());
        
        // 验证参数
        if (request.getUserId() == null) {
            return Result.failure("用户ID不能为空");
        }
        if (request.getStatus() == null) {
            return Result.failure("用户状态不能为空");
        }
        
        userService.updateUserStatus(request.getUserId(), request.getStatus());
        
        String action = request.getStatus() == User.UserStatus.DISABLED ? "封禁" : "解封";
        log.info("User {} successfully: userId={}", action, request.getUserId());
        
        return Result.success("用户" + action + "成功");
    }
    
    /**
     * 封禁用户（快捷接口）
     * @param userId 用户ID
     * @return 操作结果
     */
    @PutMapping("/{userId}/ban")
    public Result banUser(@PathVariable Long userId) {
        log.info("Banning user: userId={}", userId);
        userService.updateUserStatus(userId, User.UserStatus.DISABLED);
        return Result.success("用户封禁成功");
    }
    
    /**
     * 解封用户（快捷接口）
     * @param userId 用户ID
     * @return 操作结果
     */
    @PutMapping("/{userId}/unban")
    public Result unbanUser(@PathVariable Long userId) {
        log.info("Unbanning user: userId={}", userId);
        userService.updateUserStatus(userId, User.UserStatus.ACTIVE);
        return Result.success("用户解封成功");
    }
    
    /**
     * 根据用户名搜索用户
     * @param username 用户名
     * @return 用户简要信息列表
     */
    @GetMapping("/search")
    public Result searchUsersByUsername(@RequestParam String username) {
        log.info("Searching users by username: {}", username);
        List<UserBriefResponse> users = userService.searchUsersByUsername(username);
        return Result.success(users);
    }
    
    /**
     * 获取所有用户简要信息及状态
     * @return 用户简要信息及状态列表
     */
    @GetMapping("/all")
    public Result getAllUsersWithStatus() {
        log.info("Getting all users with status");
        List<UserBriefWithStatusResponse> users = userService.getAllUsersWithStatus();
        return Result.success(users);
    }
}