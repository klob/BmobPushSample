package com.diandi.klob.push;

import android.content.Context;
import android.database.Cursor;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.db.BaseDao;
import com.diandi.klob.sdk.util.L;
import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-18  .
 * *********    Time : 22:48 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 推送消息DB类
 */
public class MsgDao extends BaseDao<DMessage> {
    private static final String TAG = "MsgDao";
    private static final String PREFIX = "DMessage" + BmobChatUser.getCurrentUser(XApplication.getInstance()).getObjectId();
    private static MsgDao mInstance;
    private Map<String, Dao<DMessage, Integer>> mDaoMap = new HashMap<String, Dao<DMessage, Integer>>();

    public MsgDao(Context context) {
        this.mContext = context;
        mDaoMap = new HashMap<String, Dao<DMessage, Integer>>();
        getHelper();
        getDao();
    }

    public static MsgDao getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MsgDao.class) {
                if (mInstance == null) {
                    mInstance = new MsgDao(context);
                }
            }
        }
        return mInstance;
    }


    public List<DMessage> getUnReadMsg() {
        List<DMessage> msgs = null;
        msgs = query("isRead", DMessage.STATUS.UNREAD + "");
        return msgs;
    }

    public int getUnReadMsgNumber() {
        List<DMessage> msgs = null;
        msgs = query("isRead", DMessage.STATUS.UNREAD + "");

        return msgs == null ? 0 : msgs.size();
    }

    public void readAll() {
        for (DMessage message : getUnReadMsg()) {
            message.setIsRead(DMessage.STATUS.READ);
            update(message);
        }
    }

    public List<DMessage> getUnReadMsg(int type) {
        List<DMessage> msgs = null;
        msgs = query(new String[]{"isRead", "type1"}, new String[]{DMessage.STATUS.UNREAD + "", type + ""});
        return msgs;
    }

    public int getUnReadMsgNumber(int type) {
        List<DMessage> msgs = null;
        msgs = query(new String[]{"isRead", "type1"}, new String[]{DMessage.STATUS.UNREAD + "", type + ""});
        return msgs.size();
    }

    public List<DMessage> getMessage(int type) {
        List<DMessage> msgs = null;
        msgs = query("type1", type + "");

        return msgs;
    }

    public Dao<DMessage, Integer> getDao() {
        if (mDaoMap.containsKey(PREFIX)) {
            return mDaoMap.get(PREFIX);
        }
        Dao<DMessage, Integer> dao = null;
        try {
            DatabaseTableConfig<DMessage> config = DatabaseTableConfigUtil.fromClass(mDataHelper.getConnectionSource(), DMessage.class);
            config.setTableName(PREFIX);
            createTableIfNotExist(PREFIX);
            dao = UnlimitDaoManager.createDao(mDataHelper.getConnectionSource(), config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dao != null) {
            mDaoMap.put(PREFIX, dao);
        }
        mDao = dao;
        return dao;
    }

    private void createTableIfNotExist(String tableName) {
        //  queryAllTable();
        if (isTableExist(tableName)) {
           /* try {
                mDataHelper.getWritableDatabase().execSQL("ALTER TABLE " + tableName + "  ADD column  objectId VARCHAR(40)");
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return;
        }
        String sql = "  CREATE TABLE " + tableName + " (`targetUserObjectId` VARCHAR DEFAULT '' , `arg1` VARCHAR DEFAULT '' , `arg2` VARCHAR DEFAULT '' , `arg3` VARCHAR DEFAULT '' , `arg4` VARCHAR DEFAULT '' , `targetPlatform` VARCHAR DEFAULT 'Android' , `fromAvatar` VARCHAR DEFAULT '' , `fromInstallId` VARCHAR DEFAULT '' , `fromNick` VARCHAR DEFAULT '' , `fromObjectId` VARCHAR DEFAULT '' , `fromUsername` VARCHAR DEFAULT '' , `tag` VARCHAR DEFAULT '0' ,`objectId` VARCHAR DEFAULT '' , `msg1` VARCHAR DEFAULT '' , `msg2` VARCHAR DEFAULT '' , `successTip` VARCHAR DEFAULT '' ,  `receiveTime` BIGINT DEFAULT 0 , `sendTime` BIGINT DEFAULT 0 , `command` INTEGER DEFAULT 0 , `_id` INTEGER PRIMARY KEY AUTOINCREMENT , `type1` INTEGER DEFAULT 0 , `type2` INTEGER DEFAULT 0 ,`type3` INTEGER DEFAULT 0 , `isRead` INTEGER DEFAULT 0 )";
        mDataHelper.getWritableDatabase().execSQL(sql);
        String index = " CREATE UNIQUE INDEX `dmessage_sendTime_idx` ON `" + tableName + "` ( `sendTime` )";
        try {
            mDataHelper.getWritableDatabase().execSQL(index);
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        }
        L.d("roamer", "isTableExist(tableName):" + isTableExist(tableName));
    }

    private boolean isTableExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = mDataHelper.getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    private boolean queryAllTable() {
        boolean result = false;

        L.e("TAG", "start");
        Cursor cursor = null;
        try {
            String sql = "select name from sqlite_master where type='table' order by name";
            cursor = mDataHelper.getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    @Override
    public void create(DMessage message) {
        L.m(TAG);
        getDao();
        super.create(message);
    }

    @Override
    public void update(DMessage message) {
        getDao();
        super.update(message);
    }

    @Override
    public void creates(List<DMessage> dMessages) {
        getDao();
        super.creates(dMessages);
    }

    @Override
    public void delete(DMessage message) {
        getDao();
        super.delete(message);
    }

    @Override
    public void deleteById(int id) {
        getDao();
        super.deleteById(id);
    }

    @Override
    public List<DMessage> queryAll() {
        getDao();
        return super.queryAll();
    }

    @Override
    public DMessage queryTById(int id) {
        getDao();
        return super.queryTById(id);
    }

    @Override
    public DMessage queryByParam(String idName, String idValue) {
        getDao();
        return super.queryByParam(idName, idValue);
    }

    @Override
    public DMessage queryByParams(String[] attributeNames, String[] attributeValues) {
        getDao();
        return super.queryByParams(attributeNames, attributeValues);
    }

    @Override
    public List<DMessage> query(PreparedQuery<DMessage> preparedQuery) {
        getDao();
        return super.query(preparedQuery);
    }

    @Override
    public List<DMessage> query(String attributeName, String attributeValue) {
        getDao();
        return super.query(attributeName, attributeValue);
    }

    @Override
    public List<DMessage> query(String[] attributeNames, String[] attributeValues) {
        getDao();
        return super.query(attributeNames, attributeValues);
    }


}
