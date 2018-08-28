package com.example.kuaibang.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;


import cn.bmob.v3.BmobObject;

public class Test extends BmobObject{

    public static final int HELPS_STATE = 1;    // 待帮助状态
    public static final int HELPING_STATE = 2;  // 正在帮助状态
    public static final int HELPED_STATE = 3;   // 已帮助状态

    private String title;
    private String content;
    private String imgUrl;
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
