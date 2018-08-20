package com.example.kuaibang;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelpDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.help_detail);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListeners() {

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
}
