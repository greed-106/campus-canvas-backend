package com.ymj.campuscanvas.service;

public interface VerificationCodeService {
    String generateCode();
    void sendVerificationCodeWithEmail(String email, String code, int liveMinutes);
    void storeVerificationCode(String key, String recipient, String code, int liveMinutes);
    void verifyCode(String key, String recipient, String code);
}
