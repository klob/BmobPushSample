package com.diandi.klob.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.diandi.klob.sdk.util.L;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-02  .
 * *********    Time : 12:48 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * NotificationManager 消息提醒
 */
public class NotifyManager {
    public static final int NOTIFY_ID = 0;
    private final static String TAG = " NotifyManager";
    public static NotificationManager mNotificationManager;
    private static volatile NotifyManager sNotifyManager;
    private static Object INSTANCE_LOCK = new Object();
    private Context mContext;

    public NotifyManager() {
    }

    public static NotifyManager getInstance(Context var0) {
        if (sNotifyManager == null) {
            synchronized (INSTANCE_LOCK) {
                if (sNotifyManager == null) {
                    sNotifyManager = new NotifyManager();
                }

                sNotifyManager.init(var0);
            }
        }

        return sNotifyManager;
    }

    public void init(Context context) {
        this.mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

  /*  public void showNotify(boolean isAllowVoice, boolean isAllowVibrate, int id, String var4, String var5, String var6, Intent activityClz) {
        long var8 = System.currentTimeMillis();
        Notification notification;
        (notification = new Notification(id, var4, var8)).flags = 16;
        if (isAllowVoice) {
            notification.defaults |= 1;
        }

        if (isAllowVibrate) {
            notification.defaults |= 2;
        }

        notification.contentView = null;
        Intent intent;
        (intent = new Intent(activityClz)).addFlags(536870912);
        L.e(TAG, intent.getExtras());
        PendingIntent contentPendingIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        notification.setLatestEventInfo(this.mContext, var5, var6, contentPendingIntent);
        mNotificationManager.notify(0, notification);
    }

    */


    public void showNotify(boolean isAllowVoice, boolean isAllowVibrate, int icon, String tickerText, String contentTitle, String contentText, Intent activityClz) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            long when = System.currentTimeMillis();
            Notification.Builder builder = new Notification.Builder(mContext);
            builder.setSmallIcon(icon).setTicker(tickerText).setWhen(when);
            int defaultValue = 0;
            if (isAllowVoice) {
                defaultValue |= 1;
            }
            if (isAllowVibrate) {
                defaultValue |= 2;
            }
            builder.setDefaults(defaultValue);
            builder.setContentText(contentText).setContentTitle(contentTitle);
            Intent intent = new Intent(activityClz).addFlags(536870912);
            PendingIntent contentPendingIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            builder.setContentIntent(contentPendingIntent);
            L.e(TAG, intent.getExtras());
            mNotificationManager.notify(0, builder.build());
        } else {

            long var8 = System.currentTimeMillis();
            Notification notification;
            (notification = new Notification(icon, tickerText, var8)).flags = 16;
            L.d(TAG, notification.defaults);
            L.d(TAG, notification.flags);
            if (isAllowVoice) {
                notification.defaults |= 1;
            }

            if (isAllowVibrate) {
                notification.defaults |= 2;
            }

            notification.contentView = null;
            Intent intent;
            (intent = new Intent(activityClz)).addFlags(536870912);
            L.e(TAG, intent.getExtras());
            PendingIntent contentPendingIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            notification.setLatestEventInfo(this.mContext, contentTitle, contentText, contentPendingIntent);
            mNotificationManager.notify(0, notification);
        }

    }


    public void cancelNotify() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(0);
        }

    }

    public void cancelAll() {
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
        }

    }

}
