package com.example.sangtangboot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sangtangboot.entity.MaskCountResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaskService {

    public List<MaskCountResponse> count(JSONObject data){
        List<MaskCountResponse> list = new ArrayList<>();
        JSONArray records = data.getJSONArray("record");
        System.out.println("records"  + records);
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> white = new HashMap<>();
        Map<String, Integer> halfWhite = new HashMap<>();
        Map<String, Integer> black = new HashMap<>();
        for(int i = 0; i < records.size(); i++){
            JSONObject record = records.getJSONObject(i);
            String name = record.getString("person_name");
            JSONObject faceAttr = record.getJSONObject("face_attr");
            if(map.containsKey(name)){
                map.put(name, map.get(name) + 1);
            }else{
                map.put(name, 1);
                white.put(name, 0);
                halfWhite.put(name, 0);
                black.put(name, 0);
            }
            if(Objects.equals(faceAttr.getString("st_respirator"), "st_respirator_full")){
                white.put(name, white.get(name) + 1);
            } else if (Objects.equals(faceAttr.getString("st_respirator"), "st_respirator_nose")) {
                halfWhite.put(name, halfWhite.get(name) + 1);
            }else{
                black.put(name, black.get(name) + 1);
            }
        }
        int[] sumArray = new int[10];
        double rate = 0.0;
        int allPerson = 0;
        for(String name : map.keySet()){
            allPerson += map.get(name);
        }
        for (String name : map.keySet()) {
            MaskCountResponse resp = new MaskCountResponse();
            resp.setName(name);
            resp.setAllCount(map.get(name));
            resp.setWhileCount(white.get(name));
            resp.setHalfWhileCount(halfWhite.get(name));
            resp.setBlackCount(black.get(name));

            double rateDouble = (double)white.get(name) / allPerson;
            String  rateStringTwo = String.format("%.2f", rateDouble);
            rateDouble = Double.parseDouble(rateStringTwo);
            resp.setWhileRate(rateDouble);
            sumArray[0] += map.get(name);
            sumArray[1] += white.get(name);
            sumArray[2] += halfWhite.get(name);
            sumArray[3] += black.get(name);
            rate += rateDouble;
            list.add(resp);
        }
        MaskCountResponse lastResp = new MaskCountResponse();
        lastResp.setAllCount(sumArray[0]);
        lastResp.setWhileCount(sumArray[1]);
        lastResp.setHalfWhileCount(sumArray[2]);
        lastResp.setBlackCount(sumArray[3]);
        lastResp.setWhileRate(rate);
        lastResp.setName("总和");
        list.add(lastResp);
        return list;
    }

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Double c = (double)a / b;
        System.out.println(c);
    }
}
