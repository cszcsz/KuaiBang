package com.example.kuaibang;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kuaibang.fragment.HomeDiscussFragment;
import com.example.kuaibang.fragment.HomeMainFragment;
import com.example.kuaibang.fragment.HomeShareFragment;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg_tab_bar;
    private RadioButton rb_tab_help;

    private HomeMainFragment helpFragment;
    private HomeShareFragment shareFragment;
    private HomeDiscussFragment discussFragment;

    private Fragment currentFragment = new Fragment();
    private FragmentManager fragmentManager;

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
    }

    @Override
    public void initListeners() {
        // 该函数为视图中的元素设置事件监听器
        rg_tab_bar.setOnCheckedChangeListener(this);
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
}
