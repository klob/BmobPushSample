package com.diandi.klob.push.sample;

import android.content.Context;
import android.content.Intent;

import com.diandi.klob.push.DMessage;
import com.diandi.klob.push.PushManager;
import com.diandi.klob.sdk.common.Global;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-02-22  .
 * *********    Time : 10:46 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class FeedPushManager extends PushManager {

    private static final String TAG = "FeedPushManager";
    private static FeedPushManager mInstance;

    private FeedPushManager(Context context) {
        super(context);
    }

    public static FeedPushManager getInstance(Context context) {
        synchronized (FeedPushManager.class) {
            if (mInstance == null) {
                mInstance = new FeedPushManager(context);
                mInstance.register(mInstance);
            }
        }
        return mInstance;
    }

    protected void showNotify(DMessage msg) {
        Global.showMsg("收到" + msg.getFromUsername() + "消息:" + msg.getMsg1());
    }

    @Override
    public void onAdd(DMessage message, int type1) {
        super.onAdd(message, type1);
        PushApp.getInstance().getMediaPlayer().start();
    }
}
