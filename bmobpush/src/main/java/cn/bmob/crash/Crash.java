package cn.bmob.crash;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v4.KObject;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-10  .
 * *********    Time : 10:30 .
 * *********    Version : 1.0
 * *********    Copyright ? 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 崩溃日志
 */
public class Crash extends KObject {
    public final static String CRASH = "Crash";
    /**
     * 崩溃原因
     */
    private String title;
    /**
     * 崩溃详情
     */
    private String summary;
    /**
     * 崩溃机型信息
     */
    private String info;
    /**崩溃所有信息*/
    /* private String all;*/
    /**
     * 崩溃用户
     */
    private BmobUser mUser;
    /**
     * 崩溃id
     */
    private int id;
    /**
     * 崩溃日志
     */
    private String versionName;

    private int isHandle = 0;

    private BmobFile logFile;

    public BmobUser getUser() {
        return mUser;
    }

    public void setUser(BmobUser user) {
        mUser = user;
    }


    @Override
    public String toString() {
        return "Crash{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  /*  public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public BmobFile getLogFile() {
        return logFile;
    }

    public void setLogFile(BmobFile logFile) {
        this.logFile = logFile;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
