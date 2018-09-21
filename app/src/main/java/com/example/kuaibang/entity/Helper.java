package com.example.kuaibang.entity;

import cn.bmob.v3.BmobObject;

public class Helper extends BmobObject{

    private Post post;
    private MyUser user;  // 此处为帮助该帖子的用户，即帮助者
    private Integer helpState;  // 帮助状态
    private String helpRemark;   // 帮助备注

    public Post getPost() {
        return post;
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
