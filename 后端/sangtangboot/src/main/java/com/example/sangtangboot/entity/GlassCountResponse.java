package com.example.sangtangboot.entity;

import lombok.Data;

@Data
public class GlassCountResponse {
    String name;
    Integer allCount;
    Integer noneCount; // 未佩戴
    Integer transparentCount; //
    Integer sunCount;
    Integer ordinaryCount;
    Double glassRate;
}
