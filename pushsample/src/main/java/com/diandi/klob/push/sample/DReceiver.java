package com.diandi.klob.push.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.diandi.klob.push.DMessage;
import com.diandi.klob.push.JsonUtil;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.util.NetworkUtil;

import cn.bmob.common.KUserHelper;
import cn.bmob.im.db.BmobDB;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-01-04  .
 * *********    Time : 22:07 .
 * *********    Project name : diandy1.2.0 .
 * *********    Version : 1.0
 * *********    Copyright @ 2014, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 推送消息广播
 */
public class DReceiver extends BroadcastReceiver {
    private final static String TAG = "DReceiver";
    private final static String MESSAGE = "msg";
    public static String json = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (intent.getStringExtra(MESSAGE) != null) {
                String jsonCache = intent.getStringExtra(MESSAGE);
                if (!json.equals(jsonCache)) {
                    json = jsonCache;
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //    L.e(TAG, JsonUtil.getKey(json,"alert"));

        if (TextUtils.isEmpty(json)) {
            return;
        }
        if (json.contains(DMessage.DPUSH)) {
            L.d(TAG, json);
            json = intent.getStringExtra(MESSAGE);
            if (NetworkUtil.isAvailable(context)) {
                parseMessage(context, json);
            }
        }

    }

    public void parseMessage(final Context context, String jsonstr) {
        final DMessage msg = JsonUtil.getMessage(jsonstr);
        L.e(TAG, msg + "");
     /*   if (!msg.getTargetUserObjectId().equals(KUserHelper.getUserId())) {
            return;
        }
      */  long receiveTime = System.currentTimeMillis();
        msg.setReceiveTime(receiveTime);
        FeedPushManager.getInstance(context).addMessage(msg);


    }


}
