package com.example.kuaibang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kuaibang.BaseActivity;

public abstract class TitleActivity extends BaseActivity{

    private TextView mTitleTextView;
    private Button mBackwardbButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }


    @Override
    public void initView() {
        mBackwardbButton = (Button) findViewById(R.id.button_backward);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
    }

    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }


    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }


    public void onClickBack(View view) {
            finish();
    }
}

