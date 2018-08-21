package com.example.kuaibang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

public class HelpDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private Button helpBtn;
    private Dialog dialog;
    private Button confirmBtn;
    private Button concelBtn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListeners();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.help_detail);
    }

    @Override
    public void initView() {
        backBtn = findViewById(R.id.help_detail_back_btn);
        helpBtn = findViewById(R.id.help_detail_help_btn);
    }

    @Override
    public void initListeners() {
        backBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    // 启动活动的最佳写法,para1和para2为传递到该活动的数据参数,到时候根据实际情况来传数据
    public static void startMyActivity(Context context,String para1,String para2){
        Intent intent = new Intent(context,HelpDetailActivity.class);
        intent.putExtra("param1",para1);
        intent.putExtra("param2",para2);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.help_detail_back_btn:
                finish();
                break;
            case R.id.help_detail_help_btn:
                showDialog();
                Toast.makeText(HelpDetailActivity.this,"弹出对话框",Toast.LENGTH_SHORT).show();
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
                ShowToast("备注成功！");
            }
        });

        concelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
