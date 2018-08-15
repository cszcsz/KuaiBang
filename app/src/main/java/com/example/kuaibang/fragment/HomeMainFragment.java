package com.example.kuaibang.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kuaibang.R;
import com.example.kuaibang.adapter.HomeRvItemAdapter;
import com.example.kuaibang.entity.Test;

import java.util.ArrayList;
import java.util.List;

public class HomeMainFragment extends Fragment {

    private RecyclerView recyclerView;
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
        datas = new ArrayList<>();
        Test testData;
        for (int i = 0; i < 15; i++) {
            testData = new Test();
            testData.setTitle("我是第" + i + "条标题");
            testData.setContent("第" + i + "条内容");
            datas.add(testData);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeRvItemAdapter(R.layout.home_rv_item,datas);
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

        return view;
    }
}
