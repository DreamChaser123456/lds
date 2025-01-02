package com.example.sangtangboot.resp;

import com.example.sangtangboot.exception.AppExceptionCodeMsg;

public class Resp {

    //服务端返回的错误码
    private int code = 200;
    //服务端返回的错误信息
    private String msg = "success";
    //我们服务端返回的数据
    private Object data;

    private Resp(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Resp success(Object data){
        return new Resp(200, "success", data);
    }

    public static  Resp success(String msg, Object data){
        return new Resp(200, msg,  data);
    }

    // 这个是统一的 异常返回， 我们把我们的业务错误放进来就好了AppExceptionCodeMsg
    public static Resp error(AppExceptionCodeMsg appExceptionCodeMsg){
        return new Resp(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMsg(), null);
    }
    public static Resp error(int code, String msg){
        return new Resp(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

}