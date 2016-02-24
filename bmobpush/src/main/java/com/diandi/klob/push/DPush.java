package com.diandi.klob.push;

import android.content.Context;
import android.util.Log;

import com.b.a.V;
import com.diandi.klob.sdk.util.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.XListener;
import cn.bmob.v3.requestmanager.of;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-10-11  .
 * *********    Time : 20:00 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class DPush {
    private final static String TAG = "DPUSH";
    private static of requestCommand;
    private static JSONObject params;
    private static cn.bmob.v3.a.b.This rApi$6fc396a8;

    public DPush() {
    }

    public final void Code(Context var1, String alert,String var2, JSONObject var3, final PushListener var4) {
        try {
            params = new JSONObject();
            JSONObject var5;
            JSONObject aps;
            (aps = new JSONObject()).put("alert", alert);
            aps.put("badge", 1);
            // var8.put("alert", new JSONObject("有新消息"));
            aps.put("sound", "default");
            (var5 = new JSONObject()).put("aps", aps);
            var5.put("info", var2);
            JSONObject var8;
            (var8 = new JSONObject()).put("data", var5);

            if (var3 != null) {
                var8.put("where", var3);
            }
            params.put("data", var8);
            L.json(TAG, params);
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        HashMap var7;
        (var7 = new HashMap()).put("params", params);
        rApi$6fc396a8 = new cn.bmob.v3.a.b.This(var1, 1, "api", "/8/push", var7);
        (requestCommand = of.I(var1)).V(rApi$6fc396a8, new XListener() {
            public final void onSuccess(V data) {
                if (var4 != null) {
                    var4.onSuccess();
                }

            }

            public final void onFailure(int code, String e) {
                if (var4 != null) {
                    var4.onFailure(code, e);
                } else {
                    Log.e("BmobPush", "Push Message Error: " + e);
                }
            }
        });
    }

    public final void Code2(Context var1, String var2, JSONObject var3, final PushListener var4) {
        try {
            params = new JSONObject();
            JSONObject var5;
            (var5 = new JSONObject()).put("alert", var2);

            JSONObject var8;
            (var8 = new JSONObject()).put("data", var5);

            if (var3 != null) {
                var8.put("where", var3);
            }
            var8.put("badge", 1);
            // var8.put("alert", new JSONObject("有新消息"));
            var8.put("sound", "default");

            params.put("data", var8);
            L.json(TAG, params);
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        HashMap var7;
        (var7 = new HashMap()).put("params", params);
        rApi$6fc396a8 = new cn.bmob.v3.a.b.This(var1, 1, "api", "/8/push", var7);
        (requestCommand = of.I(var1)).V(rApi$6fc396a8, new XListener() {
            public final void onSuccess(V data) {
                if (var4 != null) {
                    var4.onSuccess();
                }

            }

            public final void onFailure(int code, String e) {
                if (var4 != null) {
                    var4.onFailure(code, e);
                } else {
                    Log.e("BmobPush", "Push Message Error: " + e);
                }
            }
        });
    }

    public final void Code(Context var1, JSONObject var2, JSONObject var3, final PushListener var4) {
        try {
            params = new JSONObject();
            JSONObject var5;
            (var5 = new JSONObject()).put("data", var2);
            if (var3 != null) {
                var5.put("where", var3);
            }

            params.put("data", var5);
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        HashMap var7;
        (var7 = new HashMap()).put("params", params);
        L.json(TAG, params);
        rApi$6fc396a8 = new cn.bmob.v3.a.b.This(var1, 1, "api", "/8/push", var7);
        (requestCommand = of.I(var1)).V(rApi$6fc396a8, new XListener() {
            public final void onSuccess(V data) {
                if (var4 != null) {
                    var4.onSuccess();
                }

            }

            public final void onFailure(int code, String e) {
                if (var4 != null) {
                    var4.onFailure(code, e);
                } else {
                    Log.e("BmobPush", "Push Message Error: " + e);
                }
            }
        });
    }
}
