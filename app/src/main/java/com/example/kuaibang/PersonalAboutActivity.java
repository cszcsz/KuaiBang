package com.example.kuaibang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PersonalAboutActivity extends TitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
    }

    @Override
    public void initView() {
        super.initView();

        setTitle(R.string.personal_about_title);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.personal_about);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
