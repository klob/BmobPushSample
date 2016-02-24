package com.diandi.klob.push;



/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-16  .
 * *********    Time : 14:53 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */

import cn.bmob.im.bean.BmobChatUser;

/**
 * User的本地类
 */
public class DUser {
    public String objectId = "fromObjectId";
    public String installId = "fromInstallId";
    public String avatar = "fromAvatar";
    public String username = "fromUsername";
    public String nick = "fromNick";

    public DUser() {
    }

    public DUser(BmobChatUser user) {
        this.objectId = user.getObjectId();
        this.installId = user.getInstallId();
        this.avatar = user.getAvatar();
        this.username = user.getUsername();
        this.nick = user.getNick();
    }

    @Override
    public String toString() {
        return "DUser{" +
                "objectId='" + objectId + '\'' +
                ", installId='" + installId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getInstallId() {
        return installId;
    }

    public void setInstallId(String installId) {
        this.installId = installId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
