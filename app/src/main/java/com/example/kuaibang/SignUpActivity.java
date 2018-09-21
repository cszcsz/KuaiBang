package com.example.kuaibang;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kuaibang.config.Constants;
import com.example.kuaibang.entity.MyUser;

import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobSmsState;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.example.kuaibang.IMApplication.getContext;

public class SignUpActivity extends TitleActivity implements View.OnClickListener{

    private TextInputEditText phoneNumberTextInput;
    private TextInputEditText verifyCodeTextInput;
    private TextInputEditText passwordTextInput;
    private TextInputEditText passwordConfirmTextInput;

    private TextInputLayout phoneTextLayout;
    private TextInputLayout pwd1TextLayout;
    private TextInputLayout pwd2TextLayout;

    private Button getVerifyCodeBtn;
    private Button confirmSignUpBtn;

    private boolean isVerify = false;

    private String phoneNum;
    private String verifyCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cn.bmob.sms.BmobSMS.initialize(SignUpActivity.this, Constants.Bmob_APPID);
        setContentView();
        initView();
        initListeners();
        initData();
        setTitle("");
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.signup_main);
    }

    @Override
    public void initView() {
        super.initView();
        phoneNumberTextInput = findViewById(R.id.signUp_phoneNumber_editText);
        verifyCodeTextInput = findViewById(R.id.signUp_verifyCode_editText);
        passwordTextInput = findViewById(R.id.signUp_password_editText);
        passwordConfirmTextInput = findViewById(R.id.signUp_verifyPassWord_editText);
        getVerifyCodeBtn = findViewById(R.id.signUp_getVerifyCode_btn);
        confirmSignUpBtn = findViewById(R.id.signUp_confirm_btn);
        phoneTextLayout = findViewById(R.id.signup_phone_layout);
        pwd1TextLayout = findViewById(R.id.signup_password1_layout);
        pwd2TextLayout = findViewById(R.id.signup_password2_layout);
    }

    @Override
    public void initListeners() {
        getVerifyCodeBtn.setOnClickListener(this);
        confirmSignUpBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp_confirm_btn:
                verifyCode();
                break;
            case R.id.signUp_getVerifyCode_btn:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                } else {
                    getVerifyCode();
                }
                break;
        }
    }

    public static void startMyActivity(Context context, String para){
        Intent intent = new Intent(context,SignUpActivity.class);
        intent.putExtra("param",para);
        context.startActivity(intent);
    }

    private void signUpNewUser(){
        String phoneNum = phoneNumberTextInput.getText().toString();
        String pwd1 = passwordTextInput.getText().toString();
        String pwd2 = passwordConfirmTextInput.getText().toString();
        if(pwd1.equals(pwd2)){
            MyUser newUser = new MyUser();
            newUser.setUsername(phoneNum);
            newUser.setPassword(pwd2);
            newUser.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if (e==null){
                        ShowToast("注册成功！");

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                              finish();
                            }
                        }, 1000);//1秒后执行Runnable中的run方法
                    }else{
                        Log.i("注册", e.getMessage());
                    }
                }
            });
        }else {
            ShowToast("两次密码输入不一样，请重新输入");
        }
    }

    private void getVerifyCode(){
        phoneNum = phoneNumberTextInput.getText().toString();
        cn.bmob.sms.BmobSMS.requestSMSCode(SignUpActivity.this, phoneNum,"模板1", new RequestSMSCodeListener() {
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
        cn.bmob.sms.BmobSMS.verifySmsCode(SignUpActivity.this, phoneNum, verifyCode, new VerifySMSCodeListener() {
            @Override
            public void done(cn.bmob.sms.exception.BmobException e) {
                if (e==null){
                    isVerify = true;
                    signUpNewUser();
                }else {
                    ShowToast("短信验证失败");
                    Log.i("短信验证", e.getMessage());
                }
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getVerifyCode();
                }else {
                    Toast.makeText(getContext(),"你拒绝了授予权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


}
