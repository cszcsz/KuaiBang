package com.example.kuaibang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PersonalCenterActivity extends TitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);
        initView();
        setTitle(R.string.personal_center_title);

    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

}
