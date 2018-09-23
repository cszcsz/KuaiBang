package com.example.kuaibang.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class MyUser extends BmobUser {

//        BmobUser已经包含以下字段和一些方便用户管理的方法
//        username: 用户的用户名（必需）
//        password: 用户的密码（必需）
//        email: 用户的电子邮件地址（可选）
//        emailVerified:邮箱认证状态（可选）
//        mobilePhoneNumber：手机号码（可选）
//        mobilePhoneNumberVerified：手机号码的认证状态（可选）
//如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息

    private String userName; // 用户昵称
    private Integer age;
    private BmobFile head;  // 头像
    private String introduce;
    private Boolean sex;
    private String trueName;
    private String studentId;
    private String school;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    private String college;
    private String major;
    private String grade;
    private String score;
    private String credit;
    private Integer assistNum;
    private Integer helpNum;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BmobFile getHead() {
        return head;
    }

    public void setHead(BmobFile head) {
        this.head = head;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Integer getAssistNum() {
        return assistNum;
    }

    public void setAssistNum(Integer assistNum) {
        this.assistNum = assistNum;
    }

    public Integer getHelpNum() {
        return helpNum;
    }

    public void setHelpNum(Integer helpNum) {
        this.helpNum = helpNum;
    }
}
