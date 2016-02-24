package com.diandi.klob.push;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-04-15  .
 * *********    Time : 12:25 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 数据库链接
 * */
public class MessageImpl extends BaseDaoImpl<DMessage, Integer> {

    public MessageImpl(ConnectionSource connectionSource, DatabaseTableConfig<DMessage> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

}