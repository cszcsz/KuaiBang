package com.example.kuaibang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kuaibang.config.Constants;
import com.example.kuaibang.entity.MyUser;

import java.util.List;

import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class FindPwdActivity extends TitleActivity implements View.OnClickListener{

    private TextInputEditText phoneNumberTextInput;
    private TextInputEditText verifyCodeTextInput;
    private TextInputEditText passwordTextInput;
    private TextInputEditText passwordConfirmTextInput;

    private TextInputLayout phoneTextLayout;
    private TextInputLayout pwd1TextLayout;
    private TextInputLayout pwd2TextLayout;

    private Button getVerifyCodeBtn;
    private Button confirmfindPwdBtn;

    private boolean isVerify = false;

    private String phoneNum;
    private String verifyCode;

    private boolean isQuerySuccess = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cn.bmob.sms.BmobSMS.initialize(FindPwdActivity.this, Constants.Bmob_APPID);
        setContentView();
        initView();
        initListeners();
        initData();
        setTitle("找回密码");
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.find_password_layout);
    }

    @Override
    public void initView() {
        super.initView();
        phoneNumberTextInput = findViewById(R.id.findPwd_phoneNumber_editText);
        verifyCodeTextInput = findViewById(R.id.findPwd_verifyCode_editText);
        passwordTextInput = findViewById(R.id.findPwd_password_editText);
        passwordConfirmTextInput = findViewById(R.id.findPwd_verifyPassWord_editText);
        getVerifyCodeBtn = findViewById(R.id.findPwd_getVerifyCode_btn);
        confirmfindPwdBtn = findViewById(R.id.findPwd_confirm_btn);
        phoneTextLayout = findViewById(R.id.findPwd_phone_layout);
        pwd1TextLayout = findViewById(R.id.findPwd_password1_layout);
        pwd2TextLayout = findViewById(R.id.findPwd_password2_layout);
    }

    @Override
    public void initListeners() {
        getVerifyCodeBtn.setOnClickListener(this);
        confirmfindPwdBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findPwd_confirm_btn:
                checkIfSignUp();
                if(isQuerySuccess){
                    verifyCode();
                }else {
                    ShowToast("该手机号尚未注册，请先注册");
                }
                break;
            case R.id.findPwd_getVerifyCode_btn:
                getVerifyCode();
                break;
        }
    }

    public static void startMyActivity(Context context, String para){
        Intent intent = new Intent(context,FindPwdActivity.class);
        intent.putExtra("param",para);
        context.startActivity(intent);
    }

    private void updateUser(){
        String phoneNum = phoneNumberTextInput.getText().toString();
        String pwd1 = passwordTextInput.getText().toString();
        final String pwd2 = passwordConfirmTextInput.getText().toString();
        if (pwd2.equals("")||pwd1.equals("")){
            ShowToast("密码不能为空");
            return;
        }
        if(pwd1.equals(pwd2)){
            BmobQuery<MyUser> query = new BmobQuery<MyUser>();
            query.addWhereEqualTo("username",phoneNum);
            query.findObjects(new FindListener<MyUser>() {
                @Override
                public void done(List<MyUser> list, BmobException e) {
                    if(e==null){
                        MyUser newUser = new MyUser();
                        newUser.setPassword(pwd2);
                        newUser.update(list.get(0).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    ShowToast("更换密码成功");
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 1000);//1秒后执行Runnable中的run方法
                                }else {
                                    ShowToast("更换密码失败");
                                    Log.i("更换密码", e.getMessage());
                                }
                            }
                        });
                    }else {
                        Log.i("查询", e.getMessage());
                    }

                }
            });

        }else {
            ShowToast("两次密码输入不一样，请重新输入");
            passwordConfirmTextInput.clearComposingText();
        }
    }

    private void getVerifyCode(){
        phoneNum = phoneNumberTextInput.getText().toString();
        cn.bmob.sms.BmobSMS.requestSMSCode(FindPwdActivity.this, phoneNum,"模板1", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                if (e==null){
                    ShowToast("发送短信成功~");
                }else {
                    ShowToast("发送失败");
                    Log.i("发送短信信息", e.getMessage());
                }
            }
        });
    }

    private void verifyCode(){
        verifyCode = verifyCodeTextInput.getText().toString();
        cn.bmob.sms.BmobSMS.verifySmsCode(FindPwdActivity.this, phoneNum, verifyCode, new VerifySMSCodeListener() {
            @Override
            public void done(cn.bmob.sms.exception.BmobException e) {
                if (e==null){
                    isVerify = true;
                    updateUser();
                }else {
                    ShowToast("短信验证失败");
                    Log.i("短信验证", e.getMessage());
                }
            }
        });
    }


    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    private void checkIfSignUp(){
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username",phoneNum);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if(e==null&&list.size()>0){
                    Log.i("查询信息", "查询成功，且查到了该用户");
                        isQuerySuccess = true;
                }else {
                    isQuerySuccess = false;
                    Log.i("查询信息", e.getMessage());
                }

            }
        });
    }


}
