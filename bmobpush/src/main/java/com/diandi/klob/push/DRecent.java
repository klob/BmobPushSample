package com.diandi.klob.push;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobObject;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-02  .
 * *********    Time : 15:53 .
 * *********    Project name : diandy1.2.0 .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  ? 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 推送消息
 * */
public class DRecent extends BmobObject {
    /**推送发起者*/
    private BmobChatUser launchUser;
    /**推送目标*/
    private BmobChatUser targetUser;
    /**推送内容*/
    private String json;



    public BmobChatUser getLaunchUser() {
        return launchUser;
    }

    public void setLaunchUser(BmobChatUser launchUser) {
        this.launchUser = launchUser;
    }

    public BmobChatUser getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(BmobChatUser targetUser) {
        this.targetUser = targetUser;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
