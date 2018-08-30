package com.example.kuaibang.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.R;
import com.example.kuaibang.adapter.MessageHelpingRvAdapter;
import com.example.kuaibang.entity.Test;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HelpingFragment extends Fragment {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private List<Test> datas;
    private MessageHelpingRvAdapter adapter;

    private Dialog dialog;
    private TextView dialogSubTitle;
    private Button confirmBtn;
    private Button concelBtn;
    private EditText editText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.helping_fg,container,false);

        recyclerView = view.findViewById(R.id.message_helping_recycle_view);
        refreshLayout = view.findViewById(R.id.message_helping_refresh_layout);
        datas = new ArrayList<>();
        Test testData;
        int i;
        for (i = 0; i < 5; i++) {
            testData = new Test();
            testData.setTitle("我是第" + i + "条标题");
            testData.setContent("第" + i + "条内容");
            datas.add(testData);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageHelpingRvAdapter(R.layout.message_helping_rv_item,datas);

        // 为rv中的每个item条目设置点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               Toast.makeText(getContext(),"你点击了第"+(position+1)+"条求助帖",Toast.LENGTH_SHORT).show();
            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()){
                    case R.id.message_helping_rv_item_userImg:
                        Snackbar.make(view,"你点击了第"+(position+1)+"位用户头像",Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.message_helping_rv_item_chatBtn:
                        Toast.makeText(getContext(),"进入和ta详聊界面",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.message_helping_rv_item_stopBtn:
                        showDialog();
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
                    newDatas.add(newData);
                }
                adapter.setNewData(newDatas);
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
                    moreDatas.add(moreData);
                }
                adapter.getData().addAll(moreDatas);
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(1000);
            }
        });


        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

        return view;
    }

    public static HelpingFragment newInstance() {
        HelpingFragment fragment = new HelpingFragment();
        return fragment;
    }

    private void showDialog(){
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(getContext());
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
