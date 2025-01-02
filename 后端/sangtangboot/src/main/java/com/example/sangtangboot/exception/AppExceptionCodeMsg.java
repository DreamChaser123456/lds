package com.example.sangtangboot.exception;

//这个枚举类中定义的都是跟  业务有关的  异常
public enum AppExceptionCodeMsg {

    //可以看出是  这个类创建了 3 个 实例对象
    INVALID_CODE(10000,"验证码无效"),
    USERNAME_NOT_EXISTS(10001,"用户名不存在"),
    USER_CREDIT_NOT_ENOUTH(10002,"用户积分不足"),
    DIVIDE_ZERO(10003, "整数除0"),
    INSERT_USER_FAIL(10004, "插入user失败")
    ;

    //这是一个类的2个成员属性
    private final Integer code ;
    private final String msg ;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    AppExceptionCodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}