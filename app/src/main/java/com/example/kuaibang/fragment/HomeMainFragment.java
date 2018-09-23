package com.example.kuaibang.fragment;

import android.Manifest;
import android.content.Intent;
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
import com.example.kuaibang.entity.Helper;
import com.example.kuaibang.entity.HomeRvItem;
import com.example.kuaibang.entity.MyUser;
import com.example.kuaibang.entity.Picture;
import com.example.kuaibang.entity.Post;
import com.example.kuaibang.entity.Test;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.Bmob;
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
    private HomeRvItemAdapter adapter;
    private List<Post> datas;
    private List<Post> allDatas;

    private int rvCounter = 0;


    private static final String TAG = "HomeMainFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_main_fg,container,false);
        recyclerView = view.findViewById(R.id.home_recycle_view);
        refreshLayout = view.findViewById(R.id.home_refresh_layout);

        datas = new ArrayList<>();
        allDatas = new ArrayList<>();
        BmobQuery<Post> query = new BmobQuery<>();
        query.include("user");
        query.setLimit(40);
        query.order("endTime");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        Log.i(TAG, "没查到对象");
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
                    Log.i(TAG, "查询初始帖子数据成功!");
                }else{
                    Toast.makeText(getContext(),"初始化数据失败",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, e.getMessage());
                }
                initializeAdapter();
            }
        });

        return view;
    }

    private void initializeAdapter(){

        adapter = new HomeRvItemAdapter(R.layout.home_rv_item,datas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

        // 为rv中的每个item条目设置点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                BmobQuery<Post> query = new BmobQuery<>();
                query.include("user");
                query.getObject(datas.get(position).getObjectId(), new QueryListener<Post>() {
                            @Override
                            public void done(Post post, BmobException e) {
                                if (e==null){
                                    Bundle bundle = new Bundle();
                                    Intent intent= new Intent(getContext(),HelpDetailActivity.class);
                                    bundle.putString("userHead",post.getUser().getHead().getFileUrl());
                                    bundle.putBoolean("userSex",post.getUser().getSex());
                                    bundle.putString("userName",post.getUser().getUserName());
                                    bundle.putString("userCredit",post.getUser().getCredit());
                                    bundle.putString("postScore",post.getScore().toString());
                                    bundle.putString("postHelperNum",post.getHelperNum().toString());
                                    bundle.putString("postAddress",post.getAddress());
                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                    java.util.Date date = new java.util.Date(BmobDate.getTimeStamp(post.getEndTime().getDate()));
                                    String dateTime = format.format(date);
                                    bundle.putString("postEndTime",dateTime);
                                    bundle.putString("postContent",post.getContent());
                                    bundle.putBoolean("isShowBtn",true);
                                    bundle.putString("postId",datas.get(position).getObjectId());
                                    intent.putExtra("data",bundle);
                                    startActivity(intent);
                                }else {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(),"查询帖子详细信息出错",Toast.LENGTH_SHORT).show();
                                    beginHelpDetailActivity();
                                }
                            }
                        });

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
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {

                Date currentDate = new Date(System.currentTimeMillis());
                BmobQuery<Post> query = new BmobQuery<>();
                query.setLimit(5);
                query.addWhereGreaterThanOrEqualTo("createdAt",new BmobDate(currentDate));
                query.findObjects(new FindListener<Post>() {
                    @Override
                    public void done(List<Post> list, BmobException e) {
                        if(e==null){
                            if(list.size()==0){
                                Toast.makeText(getContext(),"暂无更新的求助信息",Toast.LENGTH_SHORT).show();
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
                List<Post> moreData = new ArrayList<>();
                for (int i=0;i<3&&rvCounter<allDatas.size();i++){
                    moreData.add(allDatas.get(rvCounter));
                    rvCounter++;
                }
                adapter.getData().addAll(moreData);
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(1000);
            }
        });

    }


    private void beginHelpDetailActivity(){
        try{
            HelpDetailActivity.startMyActivity(getActivity(),true);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }





    private void createFakeData(){
        Post post = new Post();
        post.setContent("我在南教急需一个充电宝");
        post.setTitle("充电宝急需");
        post.setUser(BmobUser.getCurrentUser(MyUser.class));
        post.setRegion("南教");
        post.setState(1);
        post.setAddress("南教201");
        post.setHelperNum(2);
        post.setScore(100);
        post.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Log.i(TAG, "存储假数据成功");
                }else {
                    Log.i(TAG, e.getMessage());
                }
            }
        });

//        int n = 10;
//        Picture picture;
//        while (n>0){
//            picture = new Picture();
//            picture.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if (e==null){
//                        Log.i(TAG, "done: 成功！");
//                    }else {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            n--;
//        }

    }



}
