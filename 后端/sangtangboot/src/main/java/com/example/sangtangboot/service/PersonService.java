package com.example.sangtangboot.service;

import com.alibaba.fastjson.JSONObject;
import com.example.sangtangboot.common.Constant;
import com.example.sangtangboot.entity.FaceAllResponse;
import com.example.sangtangboot.entity.FaceAlarmResponse;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonService {

    public FaceAllResponse convert(JSONObject record){
        JSONObject faceAttr = record.getJSONObject("face_attr");
        FaceAllResponse resp = new FaceAllResponse();
        // 加载 face 的特征
        Integer age = Integer.parseInt(faceAttr.getString("st_age_value").split("\\.")[0]);
        String gender = faceAttr.getString("gender_code");
        String mask = faceAttr.getString("st_respirator");
        String mustache = faceAttr.getString("mustache_style");
        String glass = faceAttr.getString("glass_style");
        String expression = faceAttr.getString("st_expression");
        String cap = faceAttr.getString("cap_style");
        String libName = record.getString("lib_name");
        Integer libCode = record.getIntValue("lib_type");
        String libType = "白名单";
        if(libCode == 1){
            libType = "黑名单";
        }
        Date date = record.getDate("trigger");
        String imgPath = record.getString("snap_path");
        // set进对象里
        resp.setAge(age);
        resp.setMask(Constant.map.get(mask));
        resp.setGender(Constant.map.get(gender));
        resp.setMustache(Constant.map.get(mustache));
        resp.setGlass(Constant.map.get(glass));
        resp.setExpression(Constant.map.get(expression));
        resp.setCap(Constant.map.get(cap));
        resp.setLibName(libName);
        resp.setLibType(libType);
        resp.setDate(date);
        resp.setImgPath(imgPath);
        return resp;
    }


    public FaceAlarmResponse convertAlarm(JSONObject record){
        JSONObject faceAttr = record.getJSONObject("face_attr");
        FaceAlarmResponse resp = new FaceAlarmResponse();
        // 加载 face 的特征
        Integer age = Integer.parseInt(faceAttr.getString("st_age_value").split("\\.")[0]);
        String gender = faceAttr.getString("gender_code");
        String mask = faceAttr.getString("st_respirator");
        String mustache = faceAttr.getString("mustache_style");
        String glass = faceAttr.getString("glass_style");
        String expression = faceAttr.getString("st_expression");
        String cap = faceAttr.getString("cap_style");
        String libName = record.getString("lib_name");
        Integer libCode = record.getIntValue("lib_type");
        String libType = "白名单";
        if(libCode == 1){
            libType = "黑名单";
        }
        Date date = record.getDate("trigger");
        String imgPath = record.getString("snap_path");
        String name = record.getString("person_name");
        Integer similarity = record.getIntValue("similarity");
        // set进对象里
        resp.setAge(age);
        resp.setMask(Constant.map.get(mask));
        resp.setGender(Constant.map.get(gender));
        resp.setMustache(Constant.map.get(mustache));
        resp.setGlass(Constant.map.get(glass));
        resp.setExpression(Constant.map.get(expression));
        resp.setCap(Constant.map.get(cap));
        resp.setLibName(libName);
        resp.setLibType(libType);
        resp.setDate(date);
        resp.setImgPath(imgPath);
        resp.setName(name);
        resp.setSimilarity(similarity);
        return resp;
    }
}
