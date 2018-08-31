package com.example.kuaibang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.adapter.AssistProcessRvItemAdapter;
import com.example.kuaibang.entity.Test;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class AssistProcessActivity extends TitleActivity {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private List<Test> datas;
    private AssistProcessRvItemAdapter assistProcessAdapter;
    private LinearLayoutManager layoutManager;

    private Dialog dialog;
    private TextView dialogSubTitle;
    private Button confirmBtn;
    private Button concelBtn;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initAdapter();
        initListeners();
        initData();
        setTitle(R.string.assist_process_title);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.assist_process_activity_main);
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView = findViewById(R.id.assist_process_recycle_view);
        refreshLayout = findViewById(R.id.assist_process_refresh_layout);


    }

    private void initAdapter(){
        layoutManager = new LinearLayoutManager(AssistProcessActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        datas = new ArrayList<>();
        Test testData;
        int i;
        for (i = 0; i < 10; i++) {
            testData = new Test();
            testData.setTitle("我是第" + i + "条标题");
            testData.setContent("第" + i + "条内容");

            if(i<1){
                testData.setType(Test.HELPING_STATE);
            }else {
                testData.setType(Test.HELPS_STATE);
            }
            datas.add(testData);
        }

        assistProcessAdapter = new AssistProcessRvItemAdapter(datas);
        assistProcessAdapter.openLoadAnimation();
        recyclerView.setAdapter(assistProcessAdapter);
    }

    @Override
    public void initListeners() {
        // 为rv中的每个item条目设置点击事件
        assistProcessAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
        assistProcessAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.assist_process_helping_item_chatBtn:
                        ShowToast("进入 找他详聊 界面");
                        break;
                    case R.id.assist_process_helping_item_finishBtn:
                       ShowToast("完成该帮助");
                        break;
                    case R.id.assist_process_helping_item_stopBtn:
                        showDialog();
                        break;
                    case R.id.assist_process_helps_item_acceptBtn:
                        ShowToast("接受帮助，该帖子状态改变");
                        break;
                    case R.id.assist_process_helping_item_userImg:
                        ShowToast("进入用户详情页面");
                        break;
                    case R.id.assist_process_helps_item_userImg:
                        ShowToast("进入用户详情页面2");
                        break;
                }

            }
        });


        // 为recycleView设置下拉加载，上拉刷新功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                List<Test> newDatas = new ArrayList<>();
                Test newData;
                for (int i=0;i<5;i++){
                    newData = new Test();
                    newData.setTitle("哈哈，我是新的第"+i+"条标题");
                    newData.setContent("呵呵，我是新的第" + i + "条内容");
                    if(i<1){
                        newData.setType(Test.HELPING_STATE);
                    }else {
                        newData.setType(Test.HELPS_STATE);
                    }
                    newDatas.add(newData);
                }
                assistProcessAdapter.setNewData(newDatas);
                refreshLayout.finishRefresh(1000); // 第一个参数表示延时时间，第二个参数若传入false表示刷新失败
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                List<Test> moreDatas = new ArrayList<>();
                Test moreData;
                for (int i=0;i<4;i++){
                    moreData = new Test();
                    moreData.setTitle("我是第"+i+"条标题");
                    moreData.setContent("第" + i + "条内容");
                    moreData.setType(Test.HELPS_STATE);
                    moreDatas.add(moreData);
                }
                assistProcessAdapter.getData().addAll(moreDatas);
                assistProcessAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(1000);
            }
        });
    }

    @Override
    public void initData() {

    }

    public static void startMyActivity(Context context, String para1, String para2){
        Intent intent = new Intent(context,AssistProcessActivity.class);
        intent.putExtra("param1",para1);
        intent.putExtra("param2",para2);
        context.startActivity(intent);
    }

    private void showDialog(){
        dialog = new Dialog(AssistProcessActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(AssistProcessActivity.this);
        View dialogView = inflater.inflate(R.layout.help_detail_dialog,null);
        dialog.setContentView(dialogView);
        dialog.show();
        dialog.setCancelable(false);
        initDialogView(dialogView);
        initDialogListener();

    }

    private void initDialogView(View view){
        dialogSubTitle = view.findViewById(R.id.help_detail_subTitle);
        dialogSubTitle.setText("请输入终止原因");
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
