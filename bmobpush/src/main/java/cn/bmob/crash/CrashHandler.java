package cn.bmob.crash;


import android.content.Context;

import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.common.KCrashHandler;
import com.diandi.klob.sdk.processor.WorkHandler;
import com.diandi.klob.sdk.util.CollectionUtils;
import com.diandi.klob.sdk.util.L;

import java.util.List;

import cn.bmob.common.KUserHelper;
import cn.bmob.listener.SimpleFindListener;
import cn.bmob.listener.SimpleSaveListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v4.FileManager;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-07-26  .
 * *********    Time : 18:41 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 崩溃管理
 */
public class CrashHandler extends KCrashHandler {
    public static String versionName = "1.0.0";
    static CrashHandler sCrashHandler;

    protected CrashHandler() {
        super();
    }

    public static KCrashHandler getInstance(final Context context) {
        synchronized (CrashHandler.class) {
            if (sCrashHandler == null) {
                {
                    sCrashHandler = new CrashHandler();
                    sCrashHandler.init(context);
                    Global.execute(new WorkHandler() {
                        @Override
                        public void start() {
                            List<LocalCrash> crashList = null;
                            try {
                                crashList = CrashDao.getInstance(context).queryNotSend();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            L.d(TAG, crashList);
                            if (CollectionUtils.isNotNull(crashList)) {
                                for (LocalCrash localCrash : crashList) {
                                    if (localCrash.getIsSend() != 1) {
                                        sCrashHandler.saveToServer(localCrash);
                                    }
                                }
                            }
                        }

                        @Override
                        public void over() {

                        }
                    });

                }
            }
        }

        return sCrashHandler;
    }


    @Override
    public void saveToLocal(String path, String all, final String finalInfo, final String causes, final String summary) {
        if (filter(causes))
            return;
        LocalCrash localCrash = new LocalCrash();
        localCrash.setLogFilePath(path);
        localCrash.setUserId(KUserHelper.getUserId());
        localCrash.setInfo(finalInfo);
        localCrash.setTitle(causes);
        localCrash.setSummary(summary);
        CrashDao.getInstance(mContext).create(localCrash);
    }

    public void saveToServer(final LocalCrash localCrash) {
        BmobQuery<Crash> crashBmobQuery = new BmobQuery<>();
        String summary = localCrash.getSummary();
        if (summary.length() > 200) {
            summary = summary.substring(0, 199);
        }
        crashBmobQuery.addWhereStartsWith("summary", summary);
        crashBmobQuery.findObjects(mContext, new SimpleFindListener<Crash>() {
            @Override
            public void onSuccess(List<Crash> list) {
                if (CollectionUtils.isNotNull(list)) {

                } else {
                    FileManager.getInstance(mContext).upload(localCrash.getLogFilePath(), new FileManager.DUploadListener() {
                        @Override
                        public void onSuccess(String fileName, String url, BmobFile file1) {
                            L.d(TAG, url);
                            final Crash crash = new Crash();
                            crash.setInfo(localCrash.getInfo());
                            crash.setTitle(localCrash.getTitle());
                            crash.setSummary(localCrash.getSummary());
                            crash.setVersionName(versionName);
                            BmobUser user = new BmobUser();
                            user.setObjectId(localCrash.getUserId());
                            crash.setUser(user);
                            crash.setLogFile(file1);
                            crash.save(mContext, new SimpleSaveListener() {
                                @Override
                                public void onSuccess() {
                                    localCrash.setIsSend(1);
                                    CrashDao.getInstance(mContext).update(localCrash);
                                    L.d(TAG);
                                }
                            });
                        }

                        @Override
                        public void onProgress(int progress) {

                        }

                        @Override
                        public void onError(int statusCode, String errorMsg) {
                            L.e(TAG, statusCode, errorMsg);
                        }
                    });
                }
            }
        });


    }

    @Override
    public void saveToServer(String path, String all, String finalInfo, String causes, String summary) {

    }

    public boolean filter(String info) {
        if (info.contains("java.lang.NegativeArraySizeException"))
            return true;
        return false;
    }
}