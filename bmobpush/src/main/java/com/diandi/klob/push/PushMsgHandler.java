package com.diandi.klob.push;



/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-19  .
 * *********    Time : 13:35 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */
/**
 * 推送消息监听
 * */
public interface PushMsgHandler {




    /**
     * call when the specific msgs of the messageType unReadNumber have been changed
     *
     * @param messageType  the specific type
     * @param unReadNumber the number of unread msg of the specific type
     */
    void onMessage(int messageType, int unReadNumber);

    /**
     * call when the all msgs of the specific type has been read
     *
     * @param messageType the specific type of msg
     */
    void onRead(int messageType);


    /**
     * call when the number of unread msg changes
     *
     * @param unReadNumber the number of unread msg
     */
    void onNumberChange(int unReadNumber);

    /**
     * call when new msg received
     *
     * @param message      the new msg
     * @param unReadNumber the number of unread msg which has the same type of the msg
     */
    void onAdd(DMessage message, int unReadNumber);


}
