package com.example.kuaibang.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.HelpDetailActivity;
import com.example.kuaibang.MainActivity;
import com.example.kuaibang.R;
import com.example.kuaibang.adapter.HomeRvItemAdapter;
import com.example.kuaibang.entity.HomeRvItem;
import com.example.kuaibang.entity.MyUser;
import com.example.kuaibang.entity.Post;
import com.example.kuaibang.entity.Test;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class HomeMainFragment extends Fragment {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private List<Post> datas;
    private HomeRvItemAdapter adapter;

    private static final String TAG = "HomeMainFragment";

    List<Post> moreDatas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.getObject("3a90cdd344", new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    Log.i(TAG, myUser.getHead().getFileUrl());
                } else {
                    Log.i(TAG, e.getMessage());
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_main_fg,container,false);
        recyclerView = view.findViewById(R.id.home_recycle_view);
        refreshLayout = view.findViewById(R.id.home_refresh_layout);
        datas = new ArrayList<>();
//        initializeMainData();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeRvItemAdapter(R.layout.home_rv_item,datas);

        // 为rv中的每个item条目设置点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                beginHelpDetailActivity();
//              Toast.makeText(getContext(),"你点击了第"+(position+1)+"条求助帖",Toast.LENGTH_SHORT).show();
            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Snackbar.make(view,"你点击了第"+(position+1)+"位用户头像",Snackbar.LENGTH_SHORT).show();
            }
        });


        // 为recycleView设置下拉加载，上拉刷新功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                List<Post> newDatas = new ArrayList<>();
                Post newData;
                for (int i=0;i<5;i++){
                    newData = new Post();
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
                moreDatas = new ArrayList<>();
                BmobQuery<Post> query = new BmobQuery<>();
                query.include("user");
                query.findObjects(new FindListener<Post>() {
                    @Override
                    public void done(List<Post> list, BmobException e) {
                        if (e==null){
                            adapter.getData().addAll(list);
                            adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
                            Log.i(TAG,e.getMessage());
                        }
                    }
                });
                refreshLayout.finishLoadMore(1000);
            }
        });


        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void beginHelpDetailActivity(){
        try{
            HelpDetailActivity.startMyActivity(getActivity(),true);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }



    private void initializeMainData(){
        BmobQuery<Post> query = new BmobQuery<Post>();
//        //查询playerName叫“比目”的数据
//        query.addWhereEqualTo("playerName", "比目");
//        //返回50条数据，如果不加上这条语句，默认返回10条数据
//        query.setLimit(50)
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e==null){
                    Log.i(TAG, "查询初始帖子数据成功!");
                }else{
                    Toast.makeText(getContext(),"初始化数据失败",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, e.getMessage());
                }
            }
        });
    }

    private void createFakeData(){
        Post post = new Post();
    }



}
