package cn.bmob.v4;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;


import com.diandi.klob.sdk.util.CollectionUtils;
import com.diandi.klob.sdk.util.L;

import java.lang.reflect.Field;
import java.util.List;

import cn.bmob.BmobControler;
import cn.bmob.listener.SimpleFindListener;
import cn.bmob.listener.SimpleGetListener;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.a.a.thing;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-07-22  .
 * *********    Time : 15:16 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public class KQuery<T> extends BmobQuery<T> {
    public final static String ORDER = "-order";
    public final static String CREATED_AT = "-createdAt";
    public final static String UPDATED_AT = "-updatedAt";
    private final static String TAG = "KQuery";
    public static int Version = BmobControler.VERSION;
    public static int LIMIT_COUNT = 20;
    public int count = -1;
    public int queridCount = 0;
    Class<?> objclass;
    private int currentPageNum = 0;

    public KQuery() {
        super();
        setLimit(20);
        if (Version == 2) {
            L.i(TAG, "KQuery code is " + Version);
            addWhereEqualTo(KObject.IS_DELETE, new Boolean(false));
        }
    }

    public void clear() {
        try {
            Field thing = BmobQuery.class.getDeclaredField("m");
            thing.setAccessible(true);
            thing.set(this, new thing());
        } catch (Exception e) {
            L.e(TAG, e);
        }
    }

    @Override
    public void setLimit(int newLimit) {
        super.setLimit(newLimit);
        LIMIT_COUNT = newLimit;
    }

    public int getLIMIT_COUNT() {
        return LIMIT_COUNT;
    }

    public void setLIMIT_COUNT(int limit) {
        LIMIT_COUNT = limit;
    }

    public Class<?> getObjclass() {
        return objclass;
    }

    public void setObjclass(Class<?> objclass) {
        this.objclass = objclass;
    }

    public void queryByPage(final Context context, final int pagenum, final SimpleFindListener<T> findListener) {
        if (count == -1) {
            count(context, new CountListener() {
                @Override
                public void onSuccess(int i) {
                    count = i;
                    currentPageNum = pagenum;
                    find(context, currentPageNum * LIMIT_COUNT, findListener);
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else if (queridCount < count) {
            currentPageNum = pagenum;
            find(context, currentPageNum * LIMIT_COUNT, findListener);

        }
    }

    public void nextPage(final Context context, final SimpleFindListener<T> findListener) {
        if (count == -1) {
            count(context, new CountListener() {
                @Override
                public void onSuccess(int i) {
                    count = i;
                    find(context, ++currentPageNum * KQuery.LIMIT_COUNT, findListener);
                }

                @Override
                public void onFailure(int i, String s) {
                }
            });
        } else if (haveMore()) {
            find(context, ++currentPageNum * KQuery.LIMIT_COUNT, findListener);
        }

    }

    public void find(Context context, int skip, final SimpleFindListener<T> listener) {
        setSkip(skip);
        findObjects(context, listener);
        queridCount = LIMIT_COUNT * currentPageNum;
        currentPageNum++;
    }

    public boolean haveMore() {
        return queridCount < count;
    }

    public void count(Context context, final CountListener countListener) {
        count(context, objclass, new CountListener() {
            @Override
            public void onSuccess(int i) {
                countListener.onSuccess(i);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }


    public void getObjectWithOneCondition(Context context) {

    }

    public void getObjectWithOneCondition(Context context, String key, Object value, final SimpleGetListener<T> listener) {
        clear();
        addWhereEqualTo(key, value);
        findObjects(context, new SimpleFindListener<T>() {
            @Override
            public void onSuccess(List<T> list) {
                if (CollectionUtils.isNotNull(list)) {
                    if (listener != null) {
                        listener.onSuccess(list.get(0));
                    }
                } else {
                    if (listener != null) {
                        listener.onSuccess(null);
                    }
                }
            }


        });
    }

}
