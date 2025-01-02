package com.example.sangtangboot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CarAllResponse {
    String name;
    String carColor;
    String carType; // 未佩戴
    String carDirection;
    Date date;
    String imgPath;
}
