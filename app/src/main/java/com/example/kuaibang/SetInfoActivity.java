package com.example.kuaibang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

public class SetInfoActivity extends TitleActivity{


    private EditText userNameText;
    private RadioGroup sexGroup;
    private Button continueButton;

    private CharSequence userName = "";
    private boolean hasNameSetted = false;
    private Boolean userSex; //0表示男
    private boolean hasSexSetted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListeners();
        initData();
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.setinfo_main);
    }

    @Override
    public void initView() {
        super.initView();
        hideBackButton();
        setTitle(R.string.personal_info_title);


        userNameText = findViewById(R.id.username_set_text);
        sexGroup = findViewById(R.id.sex_group);
        continueButton = findViewById(R.id.set_info_continue);

    }

    @Override
    public void initListeners() {

        userNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userName = s;
                if(userName.length() > 0 && userName.length() < 17)
                    hasNameSetted = true;
                else if(userName.length() >= 17){
                    ShowToast("输入字符超出长度限制");
                    hasNameSetted = false;
                }else
                    hasNameSetted = false;


                if(hasNameSetted && hasSexSetted)
                    continueButton.setEnabled(true);
                else
                    continueButton.setEnabled(false);
            }
        });

        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hasSexSetted = true;

                if(R.id.sex_man == checkedId)
                    userSex = false;
                else if(R.id.sex_woman == checkedId)
                    userSex = true;

                if(hasNameSetted && hasSexSetted)
                    continueButton.setEnabled(true);
                else
                    continueButton.setEnabled(false);

            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetInfoActivity.this,MainActivity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("userSex",userSex);
                ShowToast("可点击");
            }
        });

    }

    @Override
    public void initData() {

    }

}
