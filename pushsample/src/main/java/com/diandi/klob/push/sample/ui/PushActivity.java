package com.diandi.klob.push.sample.ui;

import android.os.Bundle;
import android.view.View;

import com.diandi.klob.push.DMessage;
import com.diandi.klob.push.sample.FeedPushManager;
import com.diandi.klob.push.sample.R;
import com.diandi.klob.push.sample.User;
import com.diandi.klob.sdk.util.CollectionUtils;
import com.diandi.klob.sdk.widget.TopBar;

import java.util.List;

import cn.bmob.listener.SimpleFindListener;
import cn.bmob.v3.BmobQuery;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-02-22  .
 * *********    Time : 10:35 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class PushActivity extends BaseActivity {
    User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = User.getCurrentUser(mContext, User.class);
        setContentView(R.layout.activity_push);
        initTopBarForRight("推送消息", R.drawable.perm_group_messages, new TopBar.onRightImageButtonClickListener() {
            @Override
            public void onClick() {
                startAnimActivity(MessageActivity.class);
            }
        });
    }

    public void sendToMe(View view) {
        pushMsg(me);
    }

    private void pushMsg(User user) {
        String msg = getEditText(R.id.msg);
        DMessage dMessage = new DMessage(user);
        dMessage.setMsg1(msg);
        FeedPushManager.getInstance(this).push(dMessage);
    }

    public void sendToOther(View view) {
        String username = getEditText(R.id.targetUserName);
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", username);
        bmobQuery.findObjects(mContext, new SimpleFindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (CollectionUtils.isNotNull(list)) {
                    pushMsg(list.get(0));
                }
            }
        });
    }
}
