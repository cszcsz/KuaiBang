package com.example.kuaibang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.bmob.v3.b.This;


public class HelpDetailActivity extends TitleActivity implements View.OnClickListener {

    private Button helpBtn;
    private Dialog dialog;
    private Dialog success_dialog;
    private Button confirmBtn;
    private Button concelBtn;
    private EditText editText;

    boolean helpBtnIsShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpBtnIsShow = getIntent().getBooleanExtra("showBtn",true);
        setContentView();
        initView();
        initListeners();
        initData();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.help_detail);
    }

    @Override
    public void initView() {
        super.initView();
        helpBtn = findViewById(R.id.help_detail_help_btn);
        if(!helpBtnIsShow){
            helpBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListeners() {
        helpBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitle(R.string.help_detail_title);
    }

    // 启动活动的最佳写法,para1和para2为传递到该活动的数据参数,到时候根据实际情况来传数据
    public static void startMyActivity(Context context,boolean isShowBtn){
        Intent intent = new Intent(context,HelpDetailActivity.class);
        intent.putExtra("showBtn",isShowBtn);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.help_detail_help_btn:
                showDialog();
                break;
            default:
        }
    }

    private void showDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.help_detail_dialog,null);
        dialog.setContentView(dialogView);
        dialog.show();
        dialog.setCancelable(false);
        initDialogView(dialogView);
        initDialogListener();

    }

    private void initDialogView(View view){
        confirmBtn = view.findViewById(R.id.help_detail_confirm_btn);
        concelBtn = view.findViewById(R.id.help_dialog_cancel_btn);
        editText = view.findViewById(R.id.help_detail_dialog_edit);
        editText.setSingleLine(false);
        editText.setHorizontallyScrolling(false);
    }

    private void initDialogListener(){
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showSuccessDialog();
            }
        });

        concelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void showSuccessDialog(){
        success_dialog = new Dialog(this);
        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(this);
        View successDialogView = inflater.inflate(R.layout.help_detail_success_dialog,null);
        success_dialog.setContentView(successDialogView);
        success_dialog.setCancelable(false);
        success_dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                success_dialog.dismiss();
                finish();
            }
        },2000);
    }
}
