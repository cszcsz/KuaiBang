package com.example.kuaibang.entity;


import java.sql.Date;
import java.sql.Time;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class Post extends BmobObject {

    private MyUser user;
    private Integer state;
    private String title;
    private String content;
    private String region;
    private String address;
    private Integer score;
    private BmobDate endTime;
    private BmobDate completeTime;
    private Integer helperNum;


    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    public Integer getHelperNum() {
        return helperNum;
    }

    public void setHelperNum(Integer helperNum) {
        this.helperNum = helperNum;
    }

    public BmobDate getEndTime() {
        return endTime;
    }

    public void setEndTime(BmobDate endTime) {
        this.endTime = endTime;
    }

    public BmobDate getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(BmobDate completeTime) {
        this.completeTime = completeTime;
    }
}
