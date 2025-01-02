package com.example.sangtangboot.entity;

import lombok.Data;

@Data
public class MaskCountResponse {
    String name;
    Integer allCount;
    Integer whileCount;
    Integer halfWhileCount;
    Integer blackCount;
    Double whileRate;
}
