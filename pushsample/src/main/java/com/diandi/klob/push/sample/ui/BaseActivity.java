package com.diandi.klob.push.sample.ui;

import android.widget.EditText;
import android.widget.TextView;

import com.diandi.klob.sdk.ui.common.KActivity;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-02-22  .
 * *********    Time : 10:33 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class BaseActivity extends KActivity {
    @Override
    public void initViews() {

    }

    @Override
    public void init() {

    }

    @Override
    public void bindEvent() {

    }

    public String getEditText(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }

}
