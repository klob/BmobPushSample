package cn.bmob.listener;

import android.widget.Toast;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.okhttp.callback.ResultCallback;
import com.diandi.klob.sdk.util.L;


import java.io.Serializable;

import cn.bmob.v3.listener.GetListener;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-07-22  .
 * *********    Time : 13:32 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public abstract class SimpleGetListener<T> extends GetListener<T> implements Serializable {

    @Override
    public abstract void onSuccess(T obj) ;

    @Override
    public void onFailure(int i, String s) {
        if (i == 9016) {
           // Toast.makeText(XApplication.getInstance(), "当前网络不可用,请检查您的网络!", Toast.LENGTH_SHORT).show();
        } else {
            if (L.isLoggable()) {
                Toast.makeText(XApplication.getInstance(), s, Toast.LENGTH_SHORT).show();
            }
        }
        L.e("get ：" + ResultCallback.getSuperclassTypeParameter(getClass()).toString(), i, s);

    }

}
