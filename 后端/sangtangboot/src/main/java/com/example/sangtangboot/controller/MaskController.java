package com.example.sangtangboot.controller;
import com.alibaba.fastjson.JSONObject;
import com.example.sangtangboot.entity.MaskCountResponse;
import com.example.sangtangboot.exception.AppException;
import com.example.sangtangboot.resp.Resp;
import com.example.sangtangboot.service.MaskService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@CrossOrigin
@RestController
public class MaskController {

    @Resource
    private MaskService maskService;


    @GetMapping("/getMaskCount")
    public Resp maskCount(@RequestParam String sessionId, @RequestParam(name = "msg_id") String msgId) {
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
        List<MaskCountResponse> respList = null;
        try {
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                object = JSONObject.parseObject(content);
                if (object.getIntValue("code") == 0) {
                    object = object.getJSONObject("data");
                    System.out.println("object = " + object);
                    respList = maskService.count(object);
                } else {
                    throw new AppException(500, "请求无权限，请重新登录");
                }
            } else {
                throw new AppException(500, "请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Resp.success(respList);
    }

}
