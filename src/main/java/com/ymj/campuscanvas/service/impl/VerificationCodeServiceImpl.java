package com.ymj.campuscanvas.service.impl;

import com.ymj.campuscanvas.exception.WrongVerificationCodeException;
import com.ymj.campuscanvas.service.VerificationCodeService;
import com.ymj.campuscanvas.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    EmailUtil emailUtil;

    @Override
    public String generateCode() {
        // 生成6位随机数字
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }

    @Override
    public void sendVerificationCodeWithEmail(String email, String code, int liveMinutes) {
        log.info("Send verification code: {} to {}", code, email);
        emailUtil.sendVerificationCodeAsync(email, code, liveMinutes);
    }

    @Override
    public void storeVerificationCode(String key, String recipient, String code, int liveMinutes) {
        redisTemplate.opsForValue().set(
                key + recipient + ":",
                code,
                liveMinutes,
                TimeUnit.MINUTES
        );
    }

    @Override
    public void verifyCode(String key, String recipient, String code) {
        String storeCode = redisTemplate.opsForValue().get(key + recipient + ":");
        log.error("code: {}, storeCode: {}", code, storeCode);
        if (code == null || !code.equals(storeCode)){
            throw new WrongVerificationCodeException("Verification code is incorrect");
        }
        // 删除验证码
        redisTemplate.delete(key + recipient + ":");
    }
}
