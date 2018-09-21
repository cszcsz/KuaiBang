package com.example.kuaibang.entity;

public class Message {

    public static final int MESSAGE_SEND = 1;  // 发送的消息
    public static final int MESSAGE_RECEIVE = 2;  // 接受的消息

    private int type;
    private String content;
    private String avatar;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
