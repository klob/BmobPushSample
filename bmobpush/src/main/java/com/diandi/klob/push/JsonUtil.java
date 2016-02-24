package com.diandi.klob.push;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diandi.klob.sdk.util.L;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-16  .
 * *********    Time : 17:35 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * JSON 工具
 */
public class JsonUtil {
    private final static String TAG = "JsonUtil";

    public static DUser geFromUser(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        DUser dUser = JSON.parseObject((JSON.parseObject(jsonObject.getString("alert"))).getString("fromUser"), DUser.class);
        return dUser;
    }

    public static DMessage getMessage(String json) {
        DMessage msg;
        JSONObject jsonObject = JSON.parseObject(json);
        if (json.contains("info")) {
            msg = JSON.parseObject(jsonObject.getString("info"), DMessage.class);
        } else if (json.contains("data")) {
            msg = JSON.parseObject(jsonObject.getString("data"), DMessage.class);
        } else if (json.contains("alert")) {
            msg = JSON.parseObject(jsonObject.getString("alert"), DMessage.class);
        } else {
            msg = JSON.parseObject(json, DMessage.class);
        }
        return msg;
    }

    public static JSONObject getKey(String json, String key) {
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject jo = jsonObject.getJSONObject(key);
        L.e(TAG, jo.toString());
        return jo;
    }

    public static JSONObject getKey(String json, String key, int tag) {
        JSONObject jsonObject = JSON.parseObject(json);
        return JSON.parseObject(jsonObject.getString(key));
    }

    public static JSONObject getKeys(String json, String... keys) {
        JSONObject jo0 = parseObject(json);
        for (String key : keys) {
            jo0 = parseObject(jo0.getString(key));
        }
        return jo0;
    }

    public static JSONObject parseObject(String json) {
        return JSON.parseObject(json);
    }


}
