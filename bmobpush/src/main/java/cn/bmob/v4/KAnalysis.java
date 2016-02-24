package cn.bmob.v4;

import android.content.Context;
import android.util.Log;

import com.diandi.klob.sdk.bus.BusHandler;
import com.diandi.klob.sdk.util.L;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.CountListener;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-01  .
 * *********    Time : 10:13 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class KAnalysis {
    public final static String CREATED_AT = "createdAt";
    public final static String BMOB_FORMAT = "yyyy-MM-dd";
    private static final String TAG = "KAnalysis";
    Context mContext;
    KQuery mKQuery;
    BusHandler mHandler;
    Calendar mCalendar = Calendar.getInstance();
    SimpleDateFormat mFormat = new SimpleDateFormat(BMOB_FORMAT);

    public KAnalysis() {
        mCalendar.setTime(new Date());
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
    }

    public KAnalysis(Context context, KQuery kQuery, Class<?> objClz, BusHandler handler) {
        this();
        mKQuery = kQuery;
        mKQuery.setObjclass(objClz);
        mContext = context;
        mHandler = handler;

    }

    public void getTodayData() {
        mKQuery.clear();
        mKQuery.addWhereGreaterThan(CREATED_AT, wrapDate(getToday()));
        L.d(TAG, wrapDate(getToday()).getDate());
        mKQuery.count(mContext, new CountListener() {
            @Override
            public void onSuccess(int i) {
                mHandler.sendMsg(0, i);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public void getThisWeekData() {
        mKQuery.clear();
        L.e("week", getThisWeek());
        mKQuery.addWhereGreaterThan(CREATED_AT, wrapDate(getThisWeek()));
        mKQuery.count(mContext, new CountListener() {
            @Override
            public void onSuccess(int i) {
                mHandler.sendMsg(1, i);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public void getThisMonthData() {
        mKQuery.clear();
        mKQuery.addWhereGreaterThan(CREATED_AT, wrapDate(getThisMonth()));

        mKQuery.count(mContext, new CountListener() {
            @Override
            public void onSuccess(int i) {
                mHandler.sendMsg(2, i);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public void getTotalData() {
        mKQuery.clear();
        mKQuery.count(mContext, new CountListener() {
            @Override
            public void onSuccess(int i) {
                mHandler.sendMsg(3, i);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public void getAll() {
        getTodayData();
        getThisWeekData();
        getThisMonthData();
        getTotalData();
    }

    private BmobDate wrapDate(Date date) {
        return new BmobDate(date);
    }

    private Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.AM_PM,Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        SimpleDateFormat format = new SimpleDateFormat(BMOB_FORMAT);
        return calendar.getTime();
    }

    private Date getThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.AM_PM,Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        // calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, -day_of_week + 1);

        return calendar.getTime();

    }

    private Date getThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.AM_PM,Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();

    }

}
