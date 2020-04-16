package com.softnero.Crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.softnero.entity.Comments;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Crawler {

    public static List<Comments> getCommentInfo(int pages, int productId) throws Exception {
        List<Comments> result = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            result.addAll(getProductJSON(productId, i));
        }
        return result;

    }

    private static List<Comments> getProductJSON(int productId, int page) throws IOException {
        String uu = "https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&" +
                "productId=" +
                productId +
                "&score=0&sortType=6&page=" +
                page +
                "&pageSize=10&isShadowSku=0&fold=1";
        URL url = new URL(uu);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //通过相应状态码判断是否访问成功
        int code = connection.getResponseCode();
        if (code != 200) {
            return null;
        }
        //获取connection对网页访问之后 的一个输入流，该流中包含了网页的信息内容
        InputStream stream = connection.getInputStream();
        System.out.println(connection.getResponseMessage());
        //通过BufferedReader 获取流中的信息
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "GBK"));
        //输出信息

        String s = reader.readLine();
        //转换成JSON
        //fetchJSON_comment98({"..............});
        String json = s.substring(20, s.length() - 2);
        //去除末尾);
        //json = json.substring(0, json.length() - 2);
        System.out.println(json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("comments");
        List<Comments> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            Comments comments = new Comments();
            comments.setComment(data.getString("content"));
            comments.setId(data.getInteger("id"));
            comments.setNickname(data.getString("nickname"));
            comments.setUpdateTime(convertTimeToLong(data.getString("creationTime")).toString());
            result.add(comments);
        }
        return result;
    }

    public static Long convertTimeToLong(String time) {
        Assert.notNull(time, "time is null");
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(time, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
