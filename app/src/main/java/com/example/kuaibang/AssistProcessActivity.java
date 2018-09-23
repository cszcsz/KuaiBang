package com.example.kuaibang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.adapter.AssistProcessRvItemAdapter;
import com.example.kuaibang.entity.Helper;
import com.example.kuaibang.entity.MyUser;
import com.example.kuaibang.entity.Post;
import com.example.kuaibang.entity.Test;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AssistProcessActivity extends TitleActivity {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private AssistProcessRvItemAdapter adapter;
    private LinearLayoutManager layoutManager;

    private Dialog dialog;
    private TextView dialogSubTitle;
    private Button confirmBtn;
    private Button concelBtn;
    private EditText editText;

    private List<Helper> datas;
    private List<Helper> allDatas;

    private int rvCounter = 0;

    String currentPostId;

    private static final String TAG = "AssistProcessActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currentPostId = intent.getStringExtra("postId");
        Log.i(TAG, "帖子id为:"+currentPostId);
        setContentView();
        initView();
        initializeData();

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

    private void initializeData(){

        datas = new ArrayList<>();
        allDatas = new ArrayList<>();
        BmobQuery<Helper> query = new BmobQuery<>();
        query.addWhereEqualTo("post", currentPostId);
        query.include("user,post,postUser");
        query.order("-helpState,endTime");
        query.findObjects(new FindListener<Helper>() {
            @Override
            public void done(List<Helper> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        Log.i(TAG, "没查到对象");
                        Toast.makeText(AssistProcessActivity.this,"该条求助还没有人选择帮助，请耐心等待哦~",Toast.LENGTH_LONG).show();
                    }else if(list.size()<5){
                        for (rvCounter=0;rvCounter<list.size();rvCounter++){
                            datas.add(list.get(rvCounter));
                        }
                    }else if(list.size()>=5){
                        for (rvCounter=0;rvCounter<5;rvCounter++){
                            datas.add(list.get(rvCounter));
                        }
                    }
                    for (int i=0;i<list.size();i++){
                        allDatas.add(list.get(i));
                    }
                    Log.i(TAG, "查询初始helper数据成功!");
                }else{
                   ShowToast("初始化数据失败");
                    e.printStackTrace();
                }
                initAdapter();
            }
        });

    }

    private void initAdapter(){
        layoutManager = new LinearLayoutManager(AssistProcessActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AssistProcessRvItemAdapter(datas);
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
        initListeners();
    }

    @Override
    public void initListeners() {
        // 为rv中的每个item条目设置点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
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
                    case R.id.assist_process_helping_item_userHead:
                        ShowToast("进入用户详情页面");
                        break;
                    case R.id.assist_process_helps_item_userHead:
                        ShowToast("进入用户详情页面2");
                        break;
                }

            }
        });


        // 为recycleView设置下拉加载，上拉刷新功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Date currentDate = new Date(System.currentTimeMillis());
                BmobQuery<Helper> query = new BmobQuery<>();
                query.setLimit(5);
                query.addWhereEqualTo("post",currentPostId);
                query.addWhereGreaterThanOrEqualTo("createdAt",new BmobDate(currentDate));
                query.findObjects(new FindListener<Helper>() {
                    @Override
                    public void done(List<Helper> list, BmobException e) {
                        if(e==null){
                            if(list.size()==0){
                                ShowToast("暂无更新的求助信息");
                            }
                            adapter.getData().addAll(list);
                            adapter.notifyDataSetChanged();
                        }else {
                            e.printStackTrace();

                        }
                    }
                });
                refreshLayout.finishRefresh(1000); // 第一个参数表示延时时间，第二个参数若传入false表示刷新失败
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                try{
                    List<Helper> moreData = new ArrayList<>();
                    for (int i=0;i<3&&rvCounter<allDatas.size();i++){
                        moreData.add(allDatas.get(rvCounter));
                        rvCounter++;
                    }
                    adapter.getData().addAll(moreData);
                    adapter.notifyDataSetChanged();
                    refreshLayout.finishLoadMore(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    public static void startMyActivity(Context context, String para1, String para2){
        Intent intent = new Intent(context,AssistProcessActivity.class);
        intent.putExtra("postId",para1);
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
