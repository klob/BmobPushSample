package cn.bmob.listener;

import android.widget.Toast;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.okhttp.callback.ResultCallback;
import com.diandi.klob.sdk.util.L;


import cn.bmob.v3.listener.UpdateListener;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-07-20  .
 * *********    Time : 17:18 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public abstract class SimpleUpdateListener extends UpdateListener {
    @Override
    public abstract void onSuccess();


    @Override
    public void onFailure(int i, String s) {
        if (i == 9016) {
          //  Toast.makeText(XApplication.getInstance(), "当前网络不可用,请检查您的网络!", Toast.LENGTH_SHORT).show();
        } else {
            if (L.isLoggable()) {
                Toast.makeText(XApplication.getInstance(), s, Toast.LENGTH_SHORT).show();
            }
        }
        L.e("update ：" , i, s);
    }
}
