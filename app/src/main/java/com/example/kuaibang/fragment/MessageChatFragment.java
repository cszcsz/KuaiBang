package com.example.kuaibang.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.ChatActivity;
import com.example.kuaibang.R;
import com.example.kuaibang.adapter.HomeRvItemAdapter;
import com.example.kuaibang.adapter.MessageChatListItemAdapter;
import com.example.kuaibang.entity.Test;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageChatFragment extends Fragment{

    private static final String TAG = "MessageChatFragment";

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private List<Test> datas;
    private MessageChatListItemAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_chat_list_fg,container,false);

        recyclerView = view.findViewById(R.id.message_chat_list_recycle_view);
        refreshLayout = view.findViewById(R.id.message_chat_list_refresh_layout);

        datas = new ArrayList<>();
        Test testData;
        int i;
        for (i = 0; i < 4; i++) {
            testData = new Test();
            testData.setTitle("我是第" + i + "条标题");
            testData.setContent("第" + i + "条内容");
            datas.add(testData);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageChatListItemAdapter(R.layout.message_chat_list_item,datas);

        // 为rv中的每个item条目设置点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                openConversation(position);
            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Snackbar.make(view,"进入第"+(position+1)+"位用户的个人中心",Snackbar.LENGTH_SHORT).show();
            }
        });


        // 为recycleView设置下拉加载，上拉刷新功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                List<Test> newDatas = new ArrayList<>();
                Test newData;
                for (int i=0;i<3;i++){
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
                for (int i=0;i<2;i++){
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

    private void openConversation(final int position){
            BmobIMUserInfo info = new BmobIMUserInfo();
            info.setAvatar("填写接受者的头像");
            info.setUserId("ef259ac375");
            info.setName("居老师");
            BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
                @Override
                public void done(BmobIMConversation conversation, BmobException e) {
                    if(e==null){
                        // 在此跳转到聊天页面
                        conversation.queryMessages(null, 1, new MessagesQueryListener() {
                            @Override
                            public void done(List<BmobIMMessage> list, BmobException e) {
                                if(e==null){
                                    Log.i(TAG, "Fragment页面查询消息成功");
                                    if(list!=null && list.size()>0){
                                        Log.i(TAG, list.get(0).getContent());
                                        datas.get(0).setContent(list.get(0).getContent());
                                        adapter.notifyDataSetChanged();
                                    }
                                }else {
                                    Log.i(TAG, "Fragment页面查询消息失败");
                                }

                            }
                        });

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("c",conversation);
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        intent.setClass(getContext(),ChatActivity.class);
                        startActivity(intent);
                        Log.i(TAG, "开启会话成功！！！");
                    }
                    else {
                        Log.i(TAG, "开启会话失败");
                    }
                }
            });

        }




}
