package com.diandi.klob.push.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.diandi.klob.push.sample.R;
import com.diandi.klob.push.sample.User;
import com.diandi.klob.sdk.util.L;

import java.util.List;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v4.Installation;


public class StartActivity extends BaseActivity {
    String username;
    String psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setClearContentView(R.layout.activity_start);
        setSwipeBackEnable(false);

    }

    public void register(View view) {
        register();
    }

    public void login(View view) {
        getContent();
        login();
    }

    private void login() {
        BmobUser.loginByAccount(this, username, psd, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e != null && e.getMessage() != null) {
                    ShowToast(e.getMessage());

                } else {
                    setInstallId(user);
                }
            }
        });
    }

    private void register() {
        getContent();
        if (TextUtils.isEmpty(username)) {
            ShowToast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(psd)) {
            ShowToast("密码不能为空");
            return;
        }

        final User user = new User();
        user.setInstallId(BmobInstallation.getInstallationId(mContext));
        user.setUsername(username.toLowerCase());
        user.setPassword(psd);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                sendBroadcast(new Intent("register.success.finish"));
                BmobUserManager.getInstance(mContext).bindInstallationForRegister(username.toLowerCase());
                setInstallId(user);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private void getContent() {
        username = getEditText(R.id.username);
        psd = getEditText(R.id.password);
    }


    public void setInstallId(final User user) {
        BmobQuery<Installation> query = new BmobQuery<Installation>();
        query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(this));
        query.findObjects(this, new FindListener<Installation>() {

            @Override
            public void onSuccess(List<Installation> object) {
                if (object.size() > 0) {
                    Installation mbi = object.get(0);
                    mbi.setTargetUserObjectId(user.getObjectId());
                    mbi.update(mContext, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            goMain();
                            L.i("bmob", "设备信息更新成功");
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            L.i("bmob", "设备信息更新失败:" + msg);
                        }
                    });
                }
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }

    void goMain() {
        startAnimActivity(PushActivity.class);
        finish();
    }
}
