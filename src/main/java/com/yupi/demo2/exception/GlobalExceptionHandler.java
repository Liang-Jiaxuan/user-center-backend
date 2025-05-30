package com.yupi.demo2.exception;

import com.yupi.demo2.common.BaseResponse;
import com.yupi.demo2.common.ErrorCode;
import com.yupi.demo2.common.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        System.out.println("businessException"+e.getMessage()+e);
        return ResultUtils.error(e.getCode(), e.getMessage(),"");
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        //log.error()
        System.out.println("runtimeException"+e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(),"");
    }
}
