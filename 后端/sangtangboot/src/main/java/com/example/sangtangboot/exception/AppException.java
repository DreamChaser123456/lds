package com.example.sangtangboot.exception;

public class AppException extends RuntimeException{

    private int code = 500;
    private String msg = "服务器异常";


    // 这个是异常类  AppExceptionCodeMsg作为异常类的参数。
    public AppException(AppExceptionCodeMsg appExceptionCodeMsg){
        super();
        this.code = appExceptionCodeMsg.getCode();
        this.msg = appExceptionCodeMsg.getMsg();

    }

    // 只有500 服务器异常用了，平时我们都用上面那个
    public AppException(int code,String msg){
        super();
        this.code = code;
        this.msg = msg;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}