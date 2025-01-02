package com.example.sangtangboot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FaceAllResponse {
    String gender;
    Integer age;
    String mask;
    String mustache; // 胡子
    String glass; // 眼镜
    String expression; // 表情
    String cap;
    String libName;
    String libType;
    Date date;
    String imgPath;
}
