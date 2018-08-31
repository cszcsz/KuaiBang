package com.example.kuaibang.fragment;

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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.AssistProcessActivity;
import com.example.kuaibang.HelpDetailActivity;
import com.example.kuaibang.R;
import com.example.kuaibang.adapter.AssistMainRvItemAdapter;
import com.example.kuaibang.entity.Test;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MessageAssistFragment extends Fragment {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private List<Test> datas;
    private AssistMainRvItemAdapter assistMainAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_assist_fg,container,false);

        recyclerView = view.findViewById(R.id.assist_main_recycle_view);
        refreshLayout = view.findViewById(R.id.assist_main_refresh_layout);
        datas = new ArrayList<>();
        Test testData;
        int i;
        for (i = 0; i < 10; i++) {
            testData = new Test();
            testData.setTitle("我是第" + i + "条标题");
            testData.setContent("第" + i + "条内容");

            if(i<3){
                testData.setType(Test.HELPING_STATE);
            }else {
                testData.setType(Test.HELPS_STATE);
            }
            datas.add(testData);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        assistMainAdapter = new AssistMainRvItemAdapter(datas);

        // 为rv中的每个item条目设置点击事件
        assistMainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AssistProcessActivity.startMyActivity(getContext(),"data1","data2");
            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
        assistMainAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()){
                    case R.id.assist_helps_item_more:
                        HelpDetailActivity.startMyActivity(getContext(),false);
                        break;
                    case R.id.assist_helping_item_more:
                        HelpDetailActivity.startMyActivity(getContext(),false);
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
                    if(i<2){
                        newData.setType(Test.HELPING_STATE);
                    }else {
                        newData.setType(Test.HELPS_STATE);
                    }
                    newDatas.add(newData);
                }
                assistMainAdapter.setNewData(newDatas);
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
                assistMainAdapter.getData().addAll(moreDatas);
                assistMainAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(1000);
            }
        });


        assistMainAdapter.openLoadAnimation();
        recyclerView.setAdapter(assistMainAdapter);


        return view;
    }
}
