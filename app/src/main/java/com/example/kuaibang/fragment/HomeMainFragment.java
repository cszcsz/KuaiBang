package com.example.kuaibang.fragment;

import android.os.Bundle;
import android.os.Handler;
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

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.R;
import com.example.kuaibang.adapter.HomeRvItemAdapter;
import com.example.kuaibang.entity.Test;

import java.util.ArrayList;
import java.util.List;

public class HomeMainFragment extends Fragment {

    private RecyclerView recyclerView;
    private EasyRefreshLayout easyRefreshLayout;
    private List<Test> datas;
    private HomeRvItemAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_main_fg,container,false);
        recyclerView = view.findViewById(R.id.home_recycle_view);
        easyRefreshLayout = view.findViewById(R.id.home_refresh_layout);
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

        adapter = new HomeRvItemAdapter(R.layout.home_rv_item,datas);

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
                Snackbar.make(view,"你点击了第"+(position+1)+"位用户头像",Snackbar.LENGTH_SHORT).show();
            }
        });


        // 为recycleView设置下拉加载，上拉刷新功能，PS:若需要，可以自定义加载和刷新的UI界面
        easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<Test> moreDatas = new ArrayList<>();
                        Test moreData;
                        for (int i=0;i<5;i++){
                            moreData = new Test();
                            moreData.setTitle("我是第"+i+"条标题");
                            moreData.setContent("第" + i + "条内容");
                            moreDatas.add(moreData);
                        }
                        adapter.getData().addAll(moreDatas);
                        adapter.notifyDataSetChanged();
                        easyRefreshLayout.loadMoreComplete();
                    }
                },1000);
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<Test> newDatas = new ArrayList<>();
                        Test newData;
                        for (int i=0;i<5;i++){
                            newData = new Test();
                            newData.setTitle("哈哈，我是新的第"+i+"条标题");
                            newData.setContent("呵呵，我是新的第" + i + "条内容");
                            newDatas.add(newData);
                        }
                        adapter.setNewData(newDatas);
                        easyRefreshLayout.refreshComplete();
                        Toast.makeText(getContext(),"refresh success!",Toast.LENGTH_SHORT).show();
                    }
                },1000);
            }
        });

        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

        return view;
    }
}
