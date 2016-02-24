package cn.bmob.listener;

import android.widget.Toast;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.okhttp.callback.ResultCallback;
import com.diandi.klob.sdk.okhttp.callback.Types;
import com.diandi.klob.sdk.util.L;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-07-20  .
 * *********    Time : 17:57 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public abstract class SimpleFindListener<T> extends FindListener<T> {
    @Override
    public abstract void onSuccess(List<T> list);

    @Override
    public void onError(int i, String s) {
        if (i == 9016) {
//            Toast.makeText(XApplication.getInstance(), "当前网络不可用,请检查您的网络!", Toast.LENGTH_SHORT).show();
        } else {
            if (L.isLoggable()) {
                Toast.makeText(XApplication.getInstance(), s, Toast.LENGTH_SHORT).show();
            }
        }
        L.e("find ：" +ResultCallback.getSuperclassTypeParameter(getClass()).toString(),  i, s);
    }
}
