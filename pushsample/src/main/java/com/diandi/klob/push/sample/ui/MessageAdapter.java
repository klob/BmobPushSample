package com.diandi.klob.push.sample.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diandi.klob.push.DMessage;
import com.diandi.klob.push.sample.R;
import com.diandi.klob.sdk.widget.klist.KListAdapter;

import java.util.List;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-10-13  .
 * *********    Time : 15:30 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class MessageAdapter extends KListAdapter<DMessage> {

    public MessageAdapter(Context context, List<DMessage> list) {
        super(context, list);
    }


    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.simple_list_item_1, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DMessage entity = mDataList.get(position);
        holder.text.setText(entity.getFromUsername() + "   " + entity.getMsg1());
        return convertView;

    }

    class ViewHolder {
        TextView text;
    }
}
