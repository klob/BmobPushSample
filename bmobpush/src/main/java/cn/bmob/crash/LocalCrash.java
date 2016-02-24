package cn.bmob.crash;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.bmob.v3.BmobUser;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-30  .
 * *********    Time : 11:31 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
@DatabaseTable(tableName = "LocalCrash")
public class LocalCrash {
    @DatabaseField(useGetSet = true, generatedId = true)
    public int _id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String summary;
    @DatabaseField
    private String versionName;
    @DatabaseField
    private String info;
    @DatabaseField
    private String userId;
    @DatabaseField
    private String logFilePath;
    @DatabaseField(defaultValue = "0")
    private int isSend=0;

    public LocalCrash() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public String toString() {
        return "LocalCrash{" +
                "id=" + _id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", info='" + info + '\'' +
                ", userId='" + userId + '\'' +
                ", isSend=" + isSend +
                '}';
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
