package com.example.kuaibang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kuaibang.fragment.HomeDiscussFragment;
import com.example.kuaibang.fragment.HomeMainFragment;
import com.example.kuaibang.fragment.HomeShareFragment;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {

    private RadioGroup rg_tab_bar;
    private RadioButton rb_tab_help;

    private HomeMainFragment helpFragment;
    private HomeShareFragment shareFragment;
    private HomeDiscussFragment discussFragment;

    private Fragment currentFragment = new Fragment();
    private FragmentManager fragmentManager;

    private ImageButton myMessageBtn;

    private long startTime = 0; // 用于实现首页在间隔时间内按两次back键退出app的功能

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListeners();
        initFragment();
        showFragment(helpFragment);
    }

    @Override
    public void setContentView() {
        // 该函数专门用于设置活动对应的视图
        setContentView(R.layout.home_activity_main);
//        StatusBarUtil.setColor(MainActivity.this,getResources().getColor(R.color.header_bar_yellow));
    }

    @Override
    public void initView() {
        // 该函数专门用于初始化视图中的元素对象
        rg_tab_bar = findViewById(R.id.home_rg_tab_bar);
        rb_tab_help = findViewById(R.id.home_rb_help);
        rb_tab_help.setChecked(true);
        myMessageBtn = findViewById(R.id.home_header_message_btn);
    }

    @Override
    public void initListeners() {
        // 该函数为视图中的元素设置事件监听器
        rg_tab_bar.setOnCheckedChangeListener(this);
        myMessageBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //可在此处为各种adapter填充数据,查询数据库数据可参考Bmob实例代码
    }

    private void initFragment(){
        fragmentManager = getSupportFragmentManager();
        helpFragment = new HomeMainFragment();
        shareFragment = new HomeShareFragment();
        discussFragment = new HomeDiscussFragment();
    }

    private void showFragment(Fragment fragment){
        if(currentFragment!=fragment){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(currentFragment);
            currentFragment = fragment;
            if(!fragment.isAdded()){
                fragmentTransaction.add(R.id.home_fg_layout,fragment).show(fragment).commit();
            }else {
                fragmentTransaction.show(fragment).commit();
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.home_rb_help:
                showFragment(helpFragment);
                break;
            case R.id.home_rb_share:
                showFragment(shareFragment);
                break;
            case R.id.home_rb_discuss:
                showFragment(discussFragment);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_header_message_btn:  // home页面头部工具栏左侧点击事件
                MessageActivity.startMyActivity(MainActivity.this,"data1","data2");
                break;
            default:
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
