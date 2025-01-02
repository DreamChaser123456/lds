package com.example.sangtangboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sangtangboot.entity.FaceAllResponse;
import com.example.sangtangboot.entity.FaceAlarmResponse;
import com.example.sangtangboot.exception.AppException;
import com.example.sangtangboot.resp.Resp;
import com.example.sangtangboot.service.PersonService;
import org.springframework.web.bind.annotation.*;
import org.apache.http.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class PersonController {

    @Resource
    private PersonService personService;

    @GetMapping("/getAll")
    public Resp get(@RequestParam String sessionId, @RequestParam(name = "msg_id") String msgId) {
        String url = "http://192.168.1.101:80/api/json";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("sessionid", sessionId);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        JSONObject data = new JSONObject();
        data.put("msg_id", msgId);
        data.put("qry_len", 50);
        StringEntity entity = new StringEntity(data.toString(), "UTF-8");
        httpPost.setEntity(entity);
        JSONObject object = null;
        List<FaceAllResponse> list = new ArrayList<>();
        try {
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                object = JSONObject.parseObject(content);
                if (object.getIntValue("code") == 0) {
                    object = object.getJSONObject("data");
                    JSONArray records = object.getJSONArray("record");
                    for(int i = 0; i < records.size(); i++){
                        JSONObject record = records.getJSONObject(i);
                        FaceAllResponse resp = personService.convert(record);
                        list.add(resp);
                        System.out.println(resp);
                    }
                    System.out.println("object = " + object);
                } else {
                    throw new AppException(500, "请求无权限，请重新登录");
                }
            } else {
                throw new AppException(500, "请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Resp.success(list);
    }

    @GetMapping("/getAlarm")
    public Resp alarm(@RequestParam String sessionId, @RequestParam(name = "msg_id") String msgId) {
        String url = "http://192.168.1.101:80/api/json";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("sessionid", sessionId);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        JSONObject data = new JSONObject();
        data.put("msg_id", msgId);
        data.put("qry_len", 50);
        StringEntity entity = new StringEntity(data.toString(), "UTF-8");
        httpPost.setEntity(entity);
        JSONObject object = null;
        List<FaceAlarmResponse> list = new ArrayList<>();
        try {
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                object = JSONObject.parseObject(content);
                if (object.getIntValue("code") == 0) {
                    object = object.getJSONObject("data");
                    JSONArray records = object.getJSONArray("record");
                    for(int i = 0; i < records.size(); i++){
                        JSONObject record = records.getJSONObject(i);
                        FaceAlarmResponse resp = personService.convertAlarm(record);
                        list.add(resp);
                        System.out.println(resp);
                    }
                    System.out.println("object = " + object);
                } else {
                    throw new AppException(500, "请求无权限，请重新登录");
                }
            } else {
                throw new AppException(500, "请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Resp.success(list);
    }

    @GetMapping("/login")
    public Resp login(@RequestParam String username, @RequestParam String password, @RequestParam String msgId) {
        String url = "http://192.168.1.101:80/api/json";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        JSONObject data = new JSONObject();
        data.put("msg_id", msgId);
        data.put("user_name", username);
        data.put("user_pwd", password);
        System.out.println(username+" "+password);
        StringEntity entity = new StringEntity(data.toString(), "UTF-8");
        httpPost.setEntity(entity);
        String sessionId = null;
        try {
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject object = JSONObject.parseObject(content);
                if (object.getIntValue("code") == 0) {
                    sessionId = object.getString("data");
                    System.out.println("object = " + object);
                } else {
                    throw new AppException(500, "请求无权限，请重新登录");
                }
            } else {
                throw new AppException(500, "请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Resp.success(sessionId);
    }

}
