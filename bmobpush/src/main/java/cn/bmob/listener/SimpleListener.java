package cn.bmob.listener;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-12  .
 * *********    Time : 12:52 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public interface SimpleListener {

    public void onFailure();

    public void onSuccess(Object info);
}
