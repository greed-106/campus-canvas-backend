package com.ymj.campuscanvas.exception.handler;

import com.ymj.campuscanvas.exception.NotLoggedInException;
import com.ymj.campuscanvas.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex){
        return Result.failure(ex.getMessage());
    }
    @ExceptionHandler(NotLoggedInException.class)
    public Result handleNotLoggedInException(NotLoggedInException ex){
        return Result.failure(ex.getMessage(), 401);
    }
}