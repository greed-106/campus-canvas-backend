package com.ymj.campuscanvas.exception;

public class WrongVerificationCodeException extends RuntimeException {
    public WrongVerificationCodeException(String message) {
        super(message);
    }
}
