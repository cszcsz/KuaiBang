package com.example.kuaibang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PersonalExperienceActivity extends TitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
    }

    @Override
    public void initView() {
        super.initView();

        setTitle(R.string.personal_experience_title);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.personal_experience);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
