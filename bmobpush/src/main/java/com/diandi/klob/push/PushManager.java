package com.diandi.klob.push;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.b.a.V;
import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.util.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.XListener;
import cn.bmob.v3.requestmanager.of;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-18  .
 * *********    Time : 22:54 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 推送管理
 */
public class PushManager implements PushMsgHandler {
    private final static String TAG = "PushManager";
    public static DMessage sCacheMsg;
    private static boolean isActive = true;
    protected Context mContext;
    private DPush mDPush;
    private ArrayList<PushMsgHandler> mListeners = new ArrayList<>();

    protected PushManager(Context context) {
        isActive = true;
        mContext = context;
        mDPush = new DPush();
        //  notifyAllMsgs();
    }

    public static boolean isActive() {
        return isActive;
    }


    public void pushAll(DMessage message) {
        BmobPushManager manager = new BmobPushManager(mContext);

        String json = JSON.toJSONString(message);
        // Toast.makeText(mContext, json, Toast.LENGTH_LONG).show();
        manager.pushMessage(json, new PushListener() {
            @Override
            public void onSuccess() {
                //  Toast.makeText(mContext, "发送成功", Toast.LENGTH_LONG).show();
                L.e(TAG, "推送成功");
            }

            @Override
            public void onFailure(int id, String msg) {
                //   Toast.makeText(mContext, "发送失败 ： " + id + msg, Toast.LENGTH_LONG).show();
                L.e(TAG, "推送失败 ： " + id + msg);
            }
        });
    }

    //推送消息
    public void push(final DMessage message) {

        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
        query.addWhereEqualTo(DMessage.DPUSH_KEY_TARGETUSER_OBJECT_ID, message.getTargetUserObjectId());
        BmobPushManager manager = new BmobPushManager(XApplication.getInstance());
        manager.setQuery(query);

        final String json = JSON.toJSONString(message);

//        L.e(TAG, json);
   /*   manager.pushMessage(object,new PushListener() {
            @Override
            public void onSuccess() {
                if (L.isLoggable()) {
                    Toast.makeText(mContext, "发送成功1", Toast.LENGTH_LONG).show();
                }
                DRecent recent = new DRecent();
                recent.setJson(json);
                recent.setLaunchUser(UserHelper.getCurrentUser());
                User targetUser = new User();
                targetUser.setObjectId(message.getTargetUserObjectId());
                recent.setTargetUser(targetUser);
                recent.save(mContext);
                L.v(TAG, "推送成功1");
            }

            @Override
            public void onFailure(int id, String msg) {
                Toast.makeText(mContext, "发送失败1 ： " + id + msg, Toast.LENGTH_LONG).show();
                L.e(TAG, "推送失败1 ： " + id + msg);
            }
        });

*/

        mDPush.Code(mContext, message.getMsg1(), json, manager.getQuery().getWhere(), new PushListener() {
            @Override
            public void onSuccess() {
                if (L.isLoggable()) {
                    Toast.makeText(mContext, "发送成功", Toast.LENGTH_LONG).show();
                }
                L.d(TAG, "推送成功2", json);
                DRecent recent = new DRecent();
                recent.setJson(json);
                recent.setLaunchUser(BmobUser.getCurrentUser(mContext, BmobChatUser.class));
                BmobChatUser targetUser = new BmobChatUser();
                targetUser.setObjectId(message.getTargetUserObjectId());
                recent.setTargetUser(targetUser);
                recent.save(mContext);

            }

            @Override
            public void onFailure(int id, String msg) {
                // Toast.makeText(mContext, "发送失败" , Toast.LENGTH_LONG).show();
                L.e(TAG, "推送失败2 ： " + id + msg);
            }
        });

    }

    public void readAll() {
        NotifyManager.getInstance(mContext).cancelAll();
        MsgDao.getInstance(mContext).readAll();
        notifyNumberChange(MsgDao.getInstance(mContext).getUnReadMsgNumber());
    }

/*    public void readAll(int messageType) {
        for (DMessage message : MsgDao.getInstance(mContext).getMessage(messageType)) {
            message.setIsRead(DMessage.STATUS.READ);
            MsgDao.getInstance(mContext).update(message);
        }
        notifyRead(messageType);
        notifyNumberChange(MsgDao.getInstance(mContext).getUnReadMsgNumber());
    }*/

    public void addMessage(final DMessage msg) {
        L.e(TAG, msg);
        if (sCacheMsg == null) {
            L.e(TAG + 1, msg);
            sCacheMsg = msg;
        } else if (sCacheMsg.getMsg1().equals(msg.getMsg1()) && sCacheMsg.getFromObjectId().equals(msg.getFromObjectId()) && sCacheMsg.getObjectId().equals(msg.getObjectId())) {
            L.e(TAG + 2, msg);
            return;
        } else {
            L.e(TAG + 3, msg);
            sCacheMsg = msg;
        }
        L.e(TAG + 4, msg);
        MsgDao.getInstance(mContext).create(msg);
        showNotify(msg);
        notifyAdd(msg);
        notifyNumberChange(MsgDao.getInstance(mContext).getUnReadMsgNumber());

    }

    protected void showNotify(DMessage msg) {
    }


    /**
     * 消息处理
     */
    @Override
    public void onMessage(int messageType, int unReadNumber) {
        L.e(TAG, messageType, unReadNumber + "");
    }

    @Override
    public void onRead(int message) {
        L.e(TAG, message);
    }

    @Override
    public void onNumberChange(int number) {
        L.e(TAG, number);
    }

    @Override
    public void onAdd(DMessage message, int type1) {
//        L.e(TAG, message);
    }

    public void notifyAllMsgs() {
        for (int i = 0; i < 4; i++) {
            int size = MsgDao.getInstance(mContext).getUnReadMsg(i).size();
            if (mListeners != null && mListeners.size() != 0) {
                for (PushMsgHandler l : mListeners) {
                    l.onMessage(i, size);
                }
            }
        }
    }

    public void notifyMessage(int type) {
        int size = MsgDao.getInstance(mContext).getUnReadMsg(type).size();
        if (mListeners != null && mListeners.size() != 0) {
            for (PushMsgHandler l : mListeners) {
                l.onMessage(type, size);
            }
        }
    }

    public void notifyRead(int messageType) {
        if (MsgDao.getInstance(mContext).getMessage(messageType) != null) {
            if (mListeners != null && mListeners.size() != 0) {
                for (PushMsgHandler l : mListeners) {
                    l.onRead(messageType);
                }
            }
        }

    }

    public void notifyAdd(DMessage message) {
        if (mListeners != null && mListeners.size() != 0) {
            for (PushMsgHandler l : mListeners) {
                l.onAdd(message, MsgDao.getInstance(mContext).getUnReadMsgNumber(message.getType1()));
            }
        }
    }

    public void notifyNumberChange(int number) {
        if (mListeners != null && mListeners.size() != 0) {
            for (PushMsgHandler l : mListeners) {
                l.onNumberChange(number);
            }
        }
    }

    public ArrayList<PushMsgHandler> getListeners() {
        return mListeners;
    }

    public void setListeners(ArrayList<PushMsgHandler> listeners) {
        mListeners = listeners;
    }

    public void register(PushMsgHandler listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    public void unRegister(PushMsgHandler listener) {
        mListeners.remove(listener);
    }

    public void removeListener(PushMsgHandler listener) {
        mListeners.remove(listener);
    }

    public void inactivate() {
        //    NotifyManager.getInstance(mContext).cancelAll();
        isActive = false;
        mListeners.clear();
    }

    public final void Code(Context var1, JSONObject var2, JSONObject var3, final PushListener var4) {
        JSONObject params = new JSONObject();
        of requestCommand;
        try {

            JSONObject var5;
            (var5 = new JSONObject()).put("data", var2);
            if (var3 != null) {
                var5.put("where", var3);
            }

            params.put("badge", new JSONObject("1"));
            params.put("alert", new JSONObject("有新消息"));
            params.put("sound", new JSONObject("default"));
            params.put("data", var5);
            L.json(TAG, params);
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        HashMap var7;
        (var7 = new HashMap()).put("params", params);
        cn.bmob.v3.a.b.This rApi$6fc396a8 = new cn.bmob.v3.a.b.This(var1, 1, "api", "/8/push", var7);
        (requestCommand = of.I(var1)).V(rApi$6fc396a8, new XListener() {
            public final void onSuccess(V data) {
                if (var4 != null) {
                    var4.onSuccess();
                }

            }

            public final void onFailure(int code, String e) {
                if (var4 != null) {
                    var4.onFailure(code, e);
                } else {
                    Log.e("BmobPush", "Push Message Error: " + e);
                }
            }
        });
    }
}
