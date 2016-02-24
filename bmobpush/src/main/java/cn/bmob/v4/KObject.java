package cn.bmob.v4;

import android.content.Context;

import com.b.a.V;
import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.util.CollectionUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.listener.SimpleFindListener;
import cn.bmob.listener.SimpleGetListener;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.a.b.This;
import cn.bmob.v3.c.acknowledge;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.XListener;
import cn.bmob.v3.requestmanager.of;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-07-22  .
 * *********    Time : 13:14 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public class KObject extends BmobObject implements Cloneable {

    public final String TAG = getClass().getName();
    private ArrayList<DataHandler> mListeners;
    public final static String IS_DELETE = "isDelete";
    public final static String ORDER = "order";
    public final static String _HOT="-hot";
    private int hot = 0;
    private int order = 0;
    /**
     * 是否删除
     */
    private Boolean isDelete = false;

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public KObject clone() throws CloneNotSupportedException {
        super.clone();
        KObject obj = new KObject();
        obj.setObjectId(getObjectId());
        return obj;

    }

    public final static String CREATED_AT = "-createdAt";
    public final static String UPDATED_AT = "-updatedAt";


    // private String timeTag = System.currentTimeMillis() + "";

    public KObject() {
        super();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void update(Context context, String listener, UpdateListener updateListener) {
        hot++;
        super.update(context, listener, updateListener);
    }


  /*  public void saveAndGet(Context context, final SaveListener insertListener) {
        JSONObject var3 = new JSONObject();

        try {
            var3.put("data", this.gjData());
            var3.put("c", this.getTableName());
        } catch (JSONException var5) {
            var5.printStackTrace();
        }

        if (context == null) {
            if (insertListener != null) {
                insertListener.onFailure(9012, "context is null.");
            }

        } else if (!acknowledge.Code(this.getTableName(), 1, 49) && !"_Installation".equals(this.getTableName()) && !"_Role".equals(this.getTableName())) {
            if (insertListener != null) {
                insertListener.onFailure(9013, "BmobObject Object name(database table name) format is not correct.");
            }

        } else {
            HashMap var4;
            (var4 = new HashMap()).put("params", var3);
            This var6 = new This(context, 1, "api", "/8/create", var4);
            of.I(context).V(var6, new XListener() {
                public final void onSuccess(V data) {
                    setObjectId(data.cA().ap("objectId").getAsString());
                    setCreatedAt(data.cA().ap("createdAt").getAsString());
                    if (insertListener != null) {
                        insertListener.onSuccess();
                    }

                }

                public final void onFailure(int code, String e) {
                    if (insertListener != null) {
                        insertListener.onFailure(code, e);
                    }

                }

                public final void onStart() {
                    if (insertListener != null) {
                        insertListener.onStart();
                    }

                }

                public final void onFinish() {
                    if (insertListener != null) {
                        insertListener.onFinish();
                    }

                }
            });
        }
    }*/

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }


    public void getRealTime() {
        if (CollectionUtils.isNull(mListeners))
            return;
        mListeners.get(0).getRealTime();
    }

    public void getData(SimpleGetListener listener) {
        if (CollectionUtils.isNull(mListeners)) {
            mListeners = new ArrayList<>();
        }
        mListeners.add(new DataHandler<>(listener));
        getRealTime();
    }

    @Deprecated
    public void getNewData(SimpleFindListener listener) {
        if (CollectionUtils.isNull(mListeners)) {
            mListeners = new ArrayList<>();
        }
        mListeners.add(new DataHandler<>(listener));
        getRealTime();
    }

    @Deprecated
    public KObject addListener(SimpleGetListener listener) {
        if (CollectionUtils.isNull(mListeners)) {
            mListeners = new ArrayList<>();
        }
        mListeners.add(new DataHandler<>(listener));
        return this;
    }


    private void getSingleData(SimpleGetListener listener) {
        new SimpleDataHandler<>(listener).getRealTime();
    }


    @Override
    public boolean equals(Object o) {
        return (o instanceof KObject) && ((KObject) o).getObjectId().equals(getObjectId());
    }

    public String getId(BmobRelation relation) {
        return relation.getObjects().get(0).getObjectId();
    }

    public void relate(List<String> list, BmobRelation relation) {
        if (relation.get__op().equals("AddRelation")) {
            if (!list.contains(getId(relation))) {
                list.add(getId(relation));
            }
        } else {
                list.remove(getId(relation));
        }
    }

    public class DataHandler<T extends KObject> implements Serializable {
        private SimpleGetListener<T> mGetListener;
        private SimpleFindListener<T> mFindListener;

        public DataHandler(SimpleGetListener<T> listener) {
            mGetListener = listener;
        }

        public DataHandler(SimpleFindListener<T> listener) {
            mFindListener = listener;
        }

        @SuppressWarnings("UnChecked")
        public void getRealTime() {
            BmobQuery<T> mTBmobQuery = new BmobQuery<>();
            mTBmobQuery.getObject(XApplication.getInstance(), getObjectId(), mGetListener);
            mListeners = null;
        }


    }

    public class SimpleDataHandler<T extends KObject> implements Serializable {
        private SimpleGetListener<T> mGetListener;

        public SimpleDataHandler(SimpleGetListener<T> listener) {
            mGetListener = listener;
        }


        public void getRealTime() {
            BmobQuery<T> mTBmobQuery = new BmobQuery<>();
            mTBmobQuery.getObject(XApplication.getInstance(), getObjectId(), mGetListener);
        }
    }


}
