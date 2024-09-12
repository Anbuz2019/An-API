package com.anbuz.anapibackend.handler;

import com.anbuz.anapibackend.common.BaseResponse;
import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapibackend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("BUSINESS EXCEPTION: {}, description: {}, stackTrace: {}",
                e.getMessage(), e.getDescription(), e.getStackTrace());
        return BaseResponse.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("RUNTIME EXCEPTION: {} , {}", e.getMessage(), e.getStackTrace());
        return BaseResponse.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}
