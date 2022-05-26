package com.example.dusTmq.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CommonException.class)
    public ErrorResult intervalServerError(CommonException commonException){
        ErrorResult errorResult= new ErrorResult();
        errorResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResult.setMessage(commonException.getMessage());
        log.error("exceptionHandler ex={}",commonException);
        return errorResult;
    }


    
}
