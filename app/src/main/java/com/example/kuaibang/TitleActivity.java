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
    private Button mSaveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }


    @Override
    public void initView() {
        mBackwardbButton =  findViewById(R.id.button_backward);
        mTitleTextView = findViewById(R.id.text_title);
        mSaveButton = findViewById(R.id.button_save);
    }

    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }


    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }


    public void setRightTitle(int titleId){
        mSaveButton.setText(titleId);
        showSaveButton();
    }

    public void showSaveButton(){
        mSaveButton.setVisibility(View.VISIBLE);
    }
    public void hideTitle(){
        mTitleTextView.setVisibility(View.INVISIBLE);
    }
    public void hideBackButton(){
        mBackwardbButton.setVisibility(View.INVISIBLE);}
    public void onClickBack(View view) {
        finish();
    }
}

