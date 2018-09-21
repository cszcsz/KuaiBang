package com.example.kuaibang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class SetInfoActivity extends TitleActivity implements View.OnClickListener {


    private EditText userNameText;
    private ImageButton sexMan;
    private ImageButton sexWoman;
    private Button continueButton;
    private Boolean userSex;


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
        setTitle(R.id.personal_info);

        userNameText = findViewById(R.id.username_set_text);
        sexMan = findViewById(R.id.sex_man);
        sexWoman = findViewById(R.id.sex_woman);
        continueButton = findViewById(R.id.set_info_continue);

    }

    @Override
    public void initListeners() {

        sexMan.setOnClickListener(this);
        sexWoman.setOnClickListener(this);
        continueButton.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.sex_man:
                break;
            case R.id.sex_woman:
                break;
            case R.id.set_info_continue:
                break;
            default:
        }

    }
}
