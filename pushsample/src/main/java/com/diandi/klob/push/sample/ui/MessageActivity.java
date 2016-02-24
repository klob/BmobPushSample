package com.diandi.klob.push.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.diandi.klob.push.DMessage;
import com.diandi.klob.push.MsgDao;
import com.diandi.klob.push.sample.FeedPushManager;
import com.diandi.klob.push.sample.R;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.widget.DialogUtils;
import com.diandi.klob.sdk.widget.TopBar;
import com.diandi.klob.sdk.widget.klist.KListAdapter;
import com.diandi.klob.sdk.widget.klist.KListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-02-22  .
 * *********    Time : 10:36 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class MessageActivity extends BaseActivity implements AdapterView.OnItemClickListener, KListView.IXListViewListener {
    protected KListView mListView;
    protected KListAdapter<DMessage> mAdapter;
    protected List<DMessage> mListItems = new ArrayList<>();


    @Override
    public void init() {
        initTopBarForBoth("消息列表", R.drawable.top_bar_delete_btn, new TopBar.onRightImageButtonClickListener() {
            @Override
            public void onClick() {
                DialogUtils.showSelectDialog(mContext, "是否删除所有通知", "将不能恢复", new DialogUtils.ConfirmListener() {
                    @Override
                    public void onClick() {
                        MsgDao.getInstance(mContext).deleteAll();
                        mAdapter.removeAll();
                    }
                });
            }
        });
        FeedPushManager.getInstance(mContext).readAll();
        mListItems = MsgDao.getInstance(mContext).queryAllInOrder();
        mAdapter = new MessageAdapter(mContext, mListItems);
        mListView.setAdapter(mAdapter);
    }

    public void initViews() {
        mListView = (KListView) findViewById(R.id.fragment_base_listview);
        initXListView();
    }

    public void initXListView() {
        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initViews();
        init();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DMessage recent;
        recent = mAdapter.getItem(position - 1);
        L.d(TAG, recent);
    }

    public void refreshLoad() {
        if (mListView.getPullLoading()) {
            mListView.stopLoadMore();
        }
    }

    public void refreshPull() {
        if (mListView.getPullRefreshing()) {
            mListView.stopRefresh();
        }
    }

    @Override
    public void onRefresh() {
        mAdapter.setList(MsgDao.getInstance(mContext).queryAllInOrder());
        refreshPull();
    }

    @Override
    public void onLoadMore() {

    }
}
