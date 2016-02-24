package com.diandi.klob.push;

import com.alibaba.fastjson.annotation.JSONField;
import com.diandi.klob.sdk.XApplication;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.bmob.im.bean.BmobChatUser;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-16  .
 * *********    Time : 15:09 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 推送消息
 */
@DatabaseTable(daoClass = MessageImpl.class)
public class DMessage {
    public static final String DPUSH = "dpush";
    /**
     * 推送标志位
     */
    public static final String DPUSH_KEY_TARGETUSER_OBJECT_ID = "targetUserObjectId";
    @DatabaseField(useGetSet = true, defaultValue = "0")
    public int command = 0;
    @DatabaseField(useGetSet = true, defaultValue = "0")
    public int type1 = 1;
    @DatabaseField(useGetSet = true, defaultValue = "0")
    public int type2 = 2;
    @DatabaseField(useGetSet = true, defaultValue = "0")
    public int type3 = 3;
    public String dPush="dpush";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String successTip = "";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String arg1 = "1";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String arg2 = "2";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String arg3 = "3";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String arg4 = "4";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String objectId = "";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String msg1 = "msg1";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String msg2 = "msg2";
    //发送时间
    @DatabaseField(useGetSet = true, defaultValue = "0", uniqueIndex = true)
    public long sendTime = System.currentTimeMillis();
    //接收时间
    @DatabaseField(useGetSet = true, defaultValue = "0")
    public long receiveTime;
    @DatabaseField(useGetSet = true, defaultValue = "0")
    public String tag = "tag";
    //发送者信息
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String fromObjectId = "fromObjectId";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String fromInstallId = "fromInstallId";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String fromAvatar = "fromAvatar";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String fromUsername = "fromUsername";
    @DatabaseField(useGetSet = true, defaultValue = "")
    public String fromNick = "fromNick";
    @DatabaseField(useGetSet = true, defaultValue = "")
    //接收者信息
    public String targetUserObjectId = "targetUserObjectId";
    @DatabaseField(useGetSet = true, defaultValue = "0")
    public String targetPlatform = "android";
    @DatabaseField(useGetSet = true, defaultValue = "0")
    public int isRead;
    public DUser fromUser;
    @JSONField(serialize = false)
    @DatabaseField(useGetSet = true, generatedId = true)
    private int _id;


    public DMessage() {
    }

    public BmobChatUser getTargetUser() {
        return mTargetUser;
    }

    public void setTargetUser(BmobChatUser targetUser) {
        mTargetUser = targetUser;
    }

    public BmobChatUser mTargetUser;
    public DMessage(BmobChatUser targetUser) {
        BmobChatUser fromUser = BmobChatUser.getCurrentUser(XApplication.getInstance(), BmobChatUser.class);
        mTargetUser=targetUser;
        this.fromUser = new DUser(fromUser);
        this.fromObjectId = fromUser.getObjectId();
        this.fromInstallId = fromUser.getInstallId();
        this.fromAvatar = fromUser.getAvatar();
        this.fromUsername = fromUser.getUsername();
        this.fromNick = fromUser.getNick();

        this.targetUserObjectId = targetUser.getObjectId();

    }


    public DMessage(BmobChatUser fromUser, BmobChatUser targetUser) {
        this.fromObjectId = fromUser.getObjectId();
        this.fromInstallId = fromUser.getInstallId();
        this.fromAvatar = fromUser.getAvatar();
        this.fromUsername = fromUser.getUsername();
        this.fromNick = fromUser.getNick();

        this.targetUserObjectId = targetUser.getObjectId();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public DUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(DUser fromUser) {
        this.fromUser = fromUser;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getFromObjectId() {
        return fromObjectId;
    }

    public void setFromObjectId(String fromObjectId) {
        this.fromObjectId = fromObjectId;
    }

    public String getFromInstallId() {
        return fromInstallId;
    }

    public void setFromInstallId(String fromInstallId) {
        this.fromInstallId = fromInstallId;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getFromNick() {
        return fromNick;
    }

    public void setFromNick(String fromNick) {
        this.fromNick = fromNick;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "DMessage{" +
                "command=" + command +
                ", type1=" + type1 +
                ", type2=" + type2 +
                ", type3=" + type3 +
                ", dPush='" + dPush + '\'' +
                ", successTip='" + successTip + '\'' +
                ", arg1='" + arg1 + '\'' +
                ", arg2='" + arg2 + '\'' +
                ", arg3='" + arg3 + '\'' +
                ", arg4='" + arg4 + '\'' +
                ", objectId='" + objectId + '\'' +
                ", msg1='" + msg1 + '\'' +
                ", msg2='" + msg2 + '\'' +
                ", sendTime=" + sendTime +
                ", receiveTime=" + receiveTime +
                ", tag='" + tag + '\'' +
                ", fromObjectId='" + fromObjectId + '\'' +
                ", fromInstallId='" + fromInstallId + '\'' +
                ", fromAvatar='" + fromAvatar + '\'' +
                ", fromUsername='" + fromUsername + '\'' +
                ", fromNick='" + fromNick + '\'' +
                ", targetUserObjectId='" + targetUserObjectId + '\'' +
                ", targetPlatform='" + targetPlatform + '\'' +
                ", isRead=" + isRead +
                ", fromUser=" + fromUser +
                ", _id=" + _id +
                '}';
    }

    public String getSuccessTip() {
        return successTip;
    }

    public void setSuccessTip(String successTip) {
        this.successTip = successTip;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public int getType3() {
        return type3;
    }

    public void setType3(int type3) {
        this.type3 = type3;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    public String getArg4() {
        return arg4;
    }

    public void setArg4(String arg4) {
        this.arg4 = arg4;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getdPush() {
        return dPush;
    }

    public void setdPush(String dPush) {
        this.dPush = dPush;
    }

    public String getTargetUserObjectId() {
        return targetUserObjectId;
    }

    public void setTargetUserObjectId(String targetUserObjectId) {
        this.targetUserObjectId = targetUserObjectId;
    }


    public String getTargetPlatform() {
        return targetPlatform;
    }

    public void setTargetPlatform(String targetPlatform) {
        this.targetPlatform = targetPlatform;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }


    public class STATUS {
        public static final int UNREAD = 0;
        public static final int READ = 1;
        public static final int SUCCESS = 2;
        public static final int ERROR = 3;
    }

}
