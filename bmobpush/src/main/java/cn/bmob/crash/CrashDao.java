package cn.bmob.crash;

import android.content.Context;

import com.diandi.klob.sdk.db.BaseDao;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-30  .
 * *********    Time : 11:43 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class CrashDao extends BaseDao<LocalCrash> {
    private static CrashDao mInstance;

    public CrashDao(Context context) {
        super(context);
    }

    public static CrashDao getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CrashDao.class) {
                if (mInstance == null) {
                    mInstance = new CrashDao(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public Dao<LocalCrash, Integer> getDao() {
        if (mDao == null) {
            try {
                mDao = getHelper().getDao(LocalCrash.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mDao;
    }

    public List<LocalCrash> queryNotSend() {
        return query("isSend", 0 + "");
    }
}
