package com.example.sangtangboot.exception;

import com.example.sangtangboot.resp.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(AppException.class)
    @ResponseBody
    public Resp handleAppException(AppException e) {
        return Resp.error(e.getCode(), e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Resp handleOtherExceptions(Exception e) {
        log.error("异常信息-Exception：", e);
        return Resp.error(500, "服务器端异常");
    }
}