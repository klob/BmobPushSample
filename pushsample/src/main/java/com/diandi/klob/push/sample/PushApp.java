package com.diandi.klob.push.sample;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.db.BaseDao;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.util.Logger;

import cn.bmob.crash.BaseSqliteOpenHelper;
import cn.bmob.im.BmobChat;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-02-22  .
 * *********    Time : 10:31 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class PushApp extends XApplication {
    public static PushApp sInstance;
    public MediaPlayer mMediaPlayer;

    public static PushApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BmobChat.getInstance(this).init("e674af878bd9db2cd1ddf1d401fa6187");
        sInstance = this;
        BmobChat.DEBUG_MODE = BuildConfig.DEBUG;
        Logger.init("klob").setMethodCount(3).hideThreadInfo();
        L.setLoggable(BuildConfig.DEBUG);
        //开启轮询
        BmobChat.getInstance(this).startPollService(10);

        mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
        BaseDao.sOrmLiteSqliteOpenHelper = BaseSqliteOpenHelper.class;
    }

    public synchronized MediaPlayer getMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        return mMediaPlayer;
    }
}
