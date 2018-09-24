package com.example.kuaibang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.kuaibang.entity.MyUser;
import com.example.kuaibang.entity.Post;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MessageAssistFragment extends Fragment {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;

    private AssistMainRvItemAdapter adapter;

    private List<Post> allDatas;

    private int rvCounter = 0;

    private static final String TAG = "MessageAssistFragment";

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


//        allDatas = new ArrayList<>();
//        BmobQuery<Post> query = new BmobQuery<>();
//        query.addWhereEqualTo("user", BmobUser.getCurrentUser(MyUser.class));
//        query.include("user");
//        query.order("-state,endTime");
//        query.findObjects(new FindListener<Post>() {
//            @Override
//            public void done(List<Post> list, BmobException e) {
//                if (e==null){
//                    if (list.size()==0){
//                        Log.i(TAG, "没查到对象");
//                    }
//                    for (int i=0;i<list.size();i++){
//                        allDatas.add(list.get(i));
//                    }
//                    Log.i(TAG, "查询初始帖子数据成功!");
//                }else{
//                    Toast.makeText(getContext(),"初始化数据失败",Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                initializeAdapter();
//            }
//        });


        return view;
    }


    private void initializeAdapter(){

        adapter = new AssistMainRvItemAdapter(allDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

        // 为rv中的每个item条目设置点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (allDatas.get(position).getState()==1||allDatas.get(position).getState()==2){
                    AssistProcessActivity.startMyActivity(getContext(),allDatas.get(position).getObjectId(),"data2");
                }
            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view,final int position) {
                BmobQuery<Post> query = new BmobQuery<>();
                query.include("user");
                query.getObject(allDatas.get(position).getObjectId(), new QueryListener<Post>() {
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
                            bundle.putBoolean("isShowBtn",false);
                            bundle.putString("postId",allDatas.get(position).getObjectId());
                            intent.putExtra("data",bundle);
                            startActivityForResult(intent,1);
                        }else {
                            e.printStackTrace();
                            Toast.makeText(getContext(),"查询帖子详细信息出错",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        // 为recycleView设置下拉加载，上拉刷新功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {

                BmobQuery<Post> query = new BmobQuery<>();
                query.addWhereEqualTo("user", BmobUser.getCurrentUser(MyUser.class));
                query.include("user");
                query.order("-state,endTime");
                query.findObjects(new FindListener<Post>() {
                    @Override
                    public void done(List<Post> list, BmobException e) {
                        if(e==null){
                            if(list.size()==adapter.getItemCount()){
                                Toast.makeText(getContext(),"暂无更新的求助信息",Toast.LENGTH_SHORT).show();
                            }
                            allDatas = list;
                            adapter.setNewData(list);
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

                refreshLayout.finishLoadMore(1000);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (allDatas==null){
            allDatas = new ArrayList<>();
        }
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(MyUser.class));
        query.include("user");
        query.order("-state,endTime");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    allDatas = list;
                    if (adapter==null){
                        initializeAdapter();
                    }
                    adapter.setNewData(list);
                }else {
                    e.printStackTrace();

                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {// 通过requestCode来辨别数据来自哪个activity返回的
            case 1:// 取

                break;
                }

        }

    }
