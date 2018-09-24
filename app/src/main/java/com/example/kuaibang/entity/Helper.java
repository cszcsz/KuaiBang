package com.example.kuaibang.entity;

import cn.bmob.v3.BmobObject;

public class Helper extends BmobObject{

    public static final int HELPS_STATE = 1;    // 待帮助状态
    public static final int HELPING_STATE = 2;  // 正在帮助状态
    public static final int HELPED_STATE = 0;   // 已帮助状态
    public static final int INVALID_STATE = -1;   // 帮助中止状态

    private Post post;
    private MyUser user;  // 此处为帮助该帖子的用户，即帮助者
    private MyUser postUser; // 此为发布该帖子的主人
    private Integer helpState;  // 帮助状态
    private String helpRemark;   // 帮助备注

    public Post getPost() {
        return post;
    }

    public MyUser getPostUser() {
        return postUser;
    }

    public void setPostUser(MyUser postUser) {
        this.postUser = postUser;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Integer getHelpState() {
        return helpState;
    }

    public void setHelpState(Integer helpState) {
        this.helpState = helpState;
    }

    public String getHelpRemark() {
        return helpRemark;
    }

    public void setHelpRemark(String helpRemark) {
        this.helpRemark = helpRemark;
    }
}
