package cn.bmob.v4;

import android.content.Context;
import android.os.Build;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import cn.bmob.v3.BmobInstallation;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-06  .
 * *********    Time : 10:09 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public class Installation extends BmobInstallation {
    public String getTargetUserObjectId() {
        return targetUserObjectId;
    }

    public void setTargetUserObjectId(String targetUserObjectId) {
        this.targetUserObjectId = targetUserObjectId;
    }

    private String targetUserObjectId;
    public Installation(Context context) {
        super(context);
    }


}
