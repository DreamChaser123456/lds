package com.example.sangtangboot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sangtangboot.common.Constant;
import com.example.sangtangboot.entity.CarAllResponse;
import com.example.sangtangboot.entity.CarCountResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService {

    public CarAllResponse convertCar(JSONObject record){
        JSONObject vehicleAttr = record.getJSONObject("vehicle_attr");
        CarAllResponse respEntityFive = new CarAllResponse();
        if(Objects.equals(record.getString("vehicle_number"), "")){
            respEntityFive.setName("未识别车牌");
        }else{
            respEntityFive.setName(record.getString("vehicle_number"));
        }
        respEntityFive.setCarColor(Constant.carMap.get(vehicleAttr.getString("vehicle_color")));
        respEntityFive.setCarDirection(Constant.carMap.get(vehicleAttr.getString("CarDirection")));
        if(Objects.equals(vehicleAttr.getString("vehicle_class"), "")){
            respEntityFive.setName("未识别类型");
        }else{
            respEntityFive.setCarType(Constant.carMap.get(vehicleAttr.getString("vehicle_class")));
        }
        respEntityFive.setDate(record.getDate("trigger_time"));
        return respEntityFive;
    }

    public List<CarCountResponse> count(JSONObject data){

        List<CarCountResponse> list = new ArrayList<>();
        JSONArray records = data.getJSONArray("record");

        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> whiteCount = new HashMap<>();
        Map<String, Integer> otherColorCount = new HashMap<>();
        for(int i = 0; i < records.size(); i++){
            JSONObject record = records.getJSONObject(i);
            JSONObject vehicleAttr = record.getJSONObject("vehicle_attr");
            String name = record.getString("vehicle_number");
            if(Objects.equals(name, "")){
                name = "未识别车牌";
            }
            if(map.containsKey(name)){
                map.put(name, map.get(name) + 1);
            }else{
                map.put(name, 1);
                whiteCount.put(name, 0);
                otherColorCount.put(name, 0);
            }
            if(Objects.equals(vehicleAttr.getString("vehicle_color"), "white")){
                whiteCount.put(name, whiteCount.get(name) + 1);
            } else{
                otherColorCount.put(name, otherColorCount.get(name) + 1);
            }
        }
        int[] sumArray = new int[10];
        double rate = 0.0;
        int allCar = 0;
        for(String name : map.keySet()){
            allCar += map.get(name);
        }
        for (String name : map.keySet()){
            CarCountResponse respEntitySix = new CarCountResponse();
            respEntitySix.setName(name);
            respEntitySix.setWhiteColor(whiteCount.get(name));
            respEntitySix.setOtherColor(otherColorCount.get(name));
            int oneCarAllCount = whiteCount.get(name);
            double rateDouble = (double)oneCarAllCount/allCar;
            String  rateStringTwo = String.format("%.2f", rateDouble);
            rateDouble = Double.parseDouble(rateStringTwo);
            respEntitySix.setWhiteRate(rateDouble);
            respEntitySix.setNameCount(map.get(name));
            sumArray[0] += map.get(name);
            sumArray[1] += whiteCount.get(name);
            sumArray[2] += otherColorCount.get(name);
            rate += rateDouble;
            list.add(respEntitySix);
        }
        CarCountResponse lastResp = new CarCountResponse();
        lastResp.setWhiteColor(sumArray[1]);
        lastResp.setOtherColor(sumArray[2]);
        lastResp.setWhiteRate(rate);
        lastResp.setName("总和");
        lastResp.setNameCount(allCar);
        list.add(lastResp);
        return list;
    }

}
