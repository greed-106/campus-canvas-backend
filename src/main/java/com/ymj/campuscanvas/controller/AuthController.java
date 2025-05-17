package com.ymj.campuscanvas.controller;

import com.ymj.campuscanvas.pojo.DTO.LoginRequest;
import com.ymj.campuscanvas.pojo.DTO.LoginResponse;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.service.UserService;
import com.ymj.campuscanvas.service.VerificationCodeService;
import com.ymj.campuscanvas.utils.RedisKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/auth")
public class AuthController {
    @Autowired
    VerificationCodeService verificationCodeService;
    @Autowired
    UserService userService;

    @GetMapping("/verification-code/email")
    public Result sendVerificationCodeWithEmail(@RequestParam String email) {
        String code = verificationCodeService.generateCode();
        int liveMinutes = 5;
        verificationCodeService.sendVerificationCodeWithEmail(email, code, liveMinutes);
        verificationCodeService.storeVerificationCode(RedisKeys.VERIFICATION_CODE_PREFIX, email, code, liveMinutes);
        return Result.success();
    }

    @GetMapping("/verification-code/phone")
    public Result sendVerificationCodeWithPhone(@RequestParam String phone) {
        log.info("phone: {}", phone);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest user) {
        LoginResponse res = userService.loginCheck(user);
        return Result.success(res);
    }
}
