package com.example.kuaibang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;


import com.example.kuaibang.config.Constants;

import cn.bmob.v3.Bmob;


// 所有活动的基类
public abstract class BaseActivity extends AppCompatActivity {

    protected int mScreenWidth;
    protected int mScreenHeight;
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName());

       // Bmob.initialize(this, Constants.Bmob_APPID);

        // 获取当前手机的屏幕信息
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;

//        setContentView();  //顺序不能乱
//        initView();
//        initListeners();
//        initData();
    }

    public abstract void setContentView();

    public abstract void initView();

    public abstract void initListeners();

    public abstract void initData();

    // 将显示Toast进行简单封装，以后在活动中调用该函数即可
    Toast mToast;
    public void ShowToast(String text){
        if(!TextUtils.isEmpty(text)){
            if(mToast==null){
                mToast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
            }else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

}
