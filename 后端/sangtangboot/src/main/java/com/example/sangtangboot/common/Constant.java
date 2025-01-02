package com.example.sangtangboot.common;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class Constant {
    public static Map<String, String> map = new HashMap<>();

    public static Map<String, String> carMap = new HashMap<>();

    @PostConstruct
    public void init() {
        map.put("mustache_style_type_none", "无胡子");
        map.put("whiskers", "有胡子");
        map.put("st_respirator_full", "正常佩戴");
        map.put("st_respirator_nose", "未正常佩戴");
        map.put("st_respirator_mouth", "未佩戴");
        map.put("glasses_style_type_none", "无眼镜");
        map.put("transparent_glasses", "透明眼镜");
        map.put("sunglasses", "太阳镜");
        map.put("st_ordinary_glasses", "普通眼镜");
        map.put("female", "女");
        map.put("male", "男");
        map.put("st_angry", "愤怒");
        map.put("st_happy", "开心");
        map.put("st_sorrow", "伤心");
        map.put("st_calm", "沉着");
        map.put("st_surprised", "惊喜");
        map.put("st_scared", "害怕");
        map.put("st_disgust", "失望");
        map.put("st_yawn", "哈欠");
        map.put("hat_style_type_none", "无帽子");
        map.put("cap", "有帽子");

        carMap.put("gray", "灰色");
        carMap.put("white", "白色");
        carMap.put("red", "红色");
        carMap.put("black", "黑色");
        carMap.put("blue", "蓝色");
        carMap.put("green", "绿色");
        carMap.put("brown", "棕色");
        carMap.put("yellow", "黄色");
        carMap.put("purple", "紫色");
        carMap.put("pink", "粉色");
        carMap.put("st_car", "汽车");
        carMap.put("st_van", "货车");
        carMap.put("st_small_truck", "小卡车");
        carMap.put("st_big_truck", "大卡车");
        carMap.put("st_suv", "越野车");
        carMap.put("st_big_bus", "大巴");
        carMap.put("st_med_bus", "中巴");
        carMap.put("Back", "车尾");
        carMap.put("Front","车头");
        carMap.put("st_automobile_type_other", "其他类型");
    }
}
