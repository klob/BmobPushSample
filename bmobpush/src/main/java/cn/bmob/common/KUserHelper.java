package cn.bmob.common;

import android.content.Context;

import com.diandi.klob.sdk.XApplication;

import cn.bmob.v3.BmobUser;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-25  .
 * *********    Time : 23:41 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class KUserHelper {


    public static BmobUser getCurrentUser() {
        BmobUser user = BmobUser.getCurrentUser(XApplication.getInstance());
        if (user == null) {
            return new BmobUser();
        } else {
            return user;
        }
    }

    public static String getUserId() {
        try {
            return getCurrentUser().getObjectId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
