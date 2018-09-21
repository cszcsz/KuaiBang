package com.example.kuaibang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kuaibang.entity.MyUser;
import com.wang.avi.AVLoadingIndicatorView;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextInputEditText userAccountTextInput;
    private TextInputEditText userPasswordTextInput;
    private CheckBox memoryAccountCheckBox;
    private Button loginBtn;
    private TextView signUpTextView;
    private TextView findPasswordTextView;

    private AVLoadingIndicatorView loadingView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private long startTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListeners();
        initData();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        checkIfRememberPassword();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.login_main);
    }

    @Override
    public void initView() {
        userAccountTextInput = findViewById(R.id.login_account_editText);
        userPasswordTextInput = findViewById(R.id.login_password_editText);
        memoryAccountCheckBox = findViewById(R.id.login_checkBox);
        loginBtn = findViewById(R.id.login_btn);
        signUpTextView = findViewById(R.id.login_signUp_textView);
        findPasswordTextView = findViewById(R.id.login_findPwd_textView);
        loadingView = findViewById(R.id.login_loadingView);

        loadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initListeners() {
        loginBtn.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
        findPasswordTextView.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                login();
                break;
            case R.id.login_signUp_textView:
                SignUpActivity.startMyActivity(LoginActivity.this,"data1");
                break;
            case R.id.login_findPwd_textView:
                FindPwdActivity.startMyActivity(LoginActivity.this,"data1");
        }
    }

    private void login(){
        loadingView.setVisibility(View.VISIBLE);
        MyUser user = new MyUser();
        final String account = userAccountTextInput.getText().toString();
        final String pwd = userPasswordTextInput.getText().toString();
        user.setUsername(account);
        user.setPassword(pwd);
        user.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e==null){
                    editor = sharedPreferences.edit();
                    if(memoryAccountCheckBox.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);
                        editor.putString("password",pwd);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    ShowToast("登录成功");
                    loadingView.setVisibility(View.INVISIBLE);
                    MainActivity.startMyActivity(LoginActivity.this,"data");
                    finish();

                }else {
                    Log.i("登录", e.getMessage());
                    loadingView.setVisibility(View.INVISIBLE);
                    ShowToast("登录失败，请检查手机号和密码~");
                }
            }
        });
    }

    private void checkIfRememberPassword(){
        boolean isRemember = sharedPreferences.getBoolean("remember_password",false);
        if(isRemember){
            //将账号和密码都设置到文本中
            String account = sharedPreferences.getString("account","");
            String pwd = sharedPreferences.getString("password","");
            userAccountTextInput.setText(account);
            userPasswordTextInput.setText(pwd);
            memoryAccountCheckBox.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) >= 2000) {
            ShowToast("再按一次退出应用");
            startTime = currentTime;
        } else {
            finish();
        }
    }

}
