package cn.bmob.v4;

import com.diandi.klob.sdk.util.CollectionUtils;

import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-28  .
 * *********    Time : 22:17 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class KLikeObject extends KObject {
    private ArrayList<String> likeList = new ArrayList<>();
    private BmobRelation lovers;

    private void removeLover(String userId) {
        likeList.remove(userId);
    }

    private void addLover(String userId) {
        if (!this.likeList.contains(userId)) {
            if (CollectionUtils.isNull(likeList)) {
                likeList = new ArrayList<>();
            }
            likeList.add(userId);
        }
    }

    public ArrayList<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(ArrayList<String> likeList) {
        this.likeList = likeList;
    }

    public BmobRelation getLovers() {
        if (lovers.get__op().equals("AddRelation")) {
            addLover(lovers.getObjects().get(0).getObjectId());
        } else {
            removeLover(lovers.getObjects().get(0).getObjectId());
        }

        return lovers;
    }

    public void setLovers(BmobRelation lovers) {
        this.lovers = lovers;
    }
}
