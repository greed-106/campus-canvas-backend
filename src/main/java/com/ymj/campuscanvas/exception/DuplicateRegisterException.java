package com.ymj.campuscanvas.exception;

public class DuplicateRegisterException extends RuntimeException{
    public DuplicateRegisterException(String message) {
        super(message);
    }
}