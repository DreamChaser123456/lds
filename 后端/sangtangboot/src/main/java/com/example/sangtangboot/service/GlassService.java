package com.example.sangtangboot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sangtangboot.entity.GlassCountResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GlassService {

    public List<GlassCountResponse> count(JSONObject data){

        List<GlassCountResponse> list = new ArrayList<>();
        JSONArray records = data.getJSONArray("record");
        System.out.println("records"  + records);
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> none = new HashMap<>();
        Map<String, Integer> transparent = new HashMap<>();
        Map<String, Integer> sun = new HashMap<>();
        Map<String, Integer> ordinary = new HashMap<>();

        for(int i = 0; i < records.size(); i++){
            JSONObject record = records.getJSONObject(i);
            String name = record.getString("person_name");
            JSONObject faceAttr = record.getJSONObject("face_attr");
            if(map.containsKey(name)){
                map.put(name, map.get(name) + 1);
            }else{
                map.put(name, 1);
                none.put(name, 0);
                transparent.put(name, 0);
                sun.put(name, 0);
                ordinary.put(name, 0);
            }
            if(Objects.equals(faceAttr.getString("glass_style"), "transparent_glasses")){
                transparent.put(name, transparent.get(name) + 1);
            } else if (Objects.equals(faceAttr.getString("glass_style"), "sunglasses")) {
                sun.put(name, sun.get(name) + 1);
            }else if(Objects.equals(faceAttr.getString("glass_style"), "st_ordinary_glasses")){
                ordinary.put(name, ordinary.get(name) + 1);
            }else{
                none.put(name, none.get(name) + 1);
            }
        }
        int[] sumArray = new int[10];
        double rate = 0.0;
        int allPerson = 0;
        for(String name : map.keySet()){
            allPerson += map.get(name);
        }
        for (String name : map.keySet()) {
            GlassCountResponse resp = new GlassCountResponse();
            resp.setName(name);
            resp.setAllCount(map.get(name));
            resp.setNoneCount(none.get(name));
            resp.setSunCount(sun.get(name));
            resp.setOrdinaryCount(ordinary.get(name));
            resp.setTransparentCount(transparent.get(name));
            int onePersonGlassCount = sun.get(name) + ordinary.get(name) + transparent.get(name);
            double rateDouble = (double)onePersonGlassCount/allPerson;
            String  rateStringTwo = String.format("%.2f", rateDouble);
            rateDouble = Double.parseDouble(rateStringTwo);
            resp.setGlassRate(rateDouble);
            sumArray[0] += map.get(name);
            sumArray[1] += none.get(name);
            sumArray[2] += ordinary.get(name);
            sumArray[3] += transparent.get(name);
            sumArray[4] += sun.get(name);
            rate += rateDouble;
            list.add(resp);
        }
        GlassCountResponse lastResp = new GlassCountResponse();
        lastResp.setAllCount(sumArray[0]);
        lastResp.setNoneCount(sumArray[1]);
        lastResp.setOrdinaryCount(sumArray[2]);
        lastResp.setTransparentCount(sumArray[3]);
        lastResp.setSunCount(sumArray[4]);
        lastResp.setGlassRate(rate);
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
