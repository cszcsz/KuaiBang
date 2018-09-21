package com.example.kuaibang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.adapter.ChatMessageItemRvItemAdapter;
import com.example.kuaibang.entity.Message;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;

public class ChatActivity extends TitleActivity implements View.OnClickListener,MessageListHandler {

    private static final String TAG = "ChatActivity";
    
    private EditText editText;
    private Button sendBtn;

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private List<Message> datas;
    private ChatMessageItemRvItemAdapter chatMessageItemRvItemAdapter;
    private LinearLayoutManager layoutManager;

    boolean isConnect = false;
    boolean isOpenConversation = false;

    String userID = "40d45074ee";

    BmobIMConversation mBmobIMConversation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        // 获取从chatList碎片传过来的会话
        mBmobIMConversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) getIntent().getSerializableExtra("c"));
        initAdapter();
        initListeners();
        initData();
        setTitle(mBmobIMConversation.getConversationTitle());

    }

    @Override
    protected void onResume(){
        BmobIM.getInstance().addMessageListHandler(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移除页面消息监听器
        BmobIM.getInstance().removeMessageListHandler(this);
        super.onPause();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.chat_activity);
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView = findViewById(R.id.chat_activity_recycle_view);
        refreshLayout = findViewById(R.id.chat_activity_refresh_layout);
        editText = findViewById(R.id.chat_activity_editText);
        sendBtn = findViewById(R.id.chat_activity_send_btn);
    }

    private void initAdapter(){
        layoutManager = new LinearLayoutManager(ChatActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        datas = new ArrayList<>();
        mBmobIMConversation.queryMessages(null, 20, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if(e==null){
                    Log.i(TAG,"查询信息成功！");

                    if(list!=null && list.size() > 0){
                        Log.i(TAG, "查询信息成功且list不为空");
                        Message msg;
                        for(int i=0;i<list.size();i++){
                            msg = new Message();
                            msg.setContent(list.get(i).getContent());
                            if(list.get(i).getBmobIMUserInfo().getUserId().equals(userID)){
                                msg.setType(Message.MESSAGE_SEND);
                            }else if(list.get(i).getBmobIMUserInfo().getUserId().equals(mBmobIMConversation.getConversationId())){
                                msg.setType(Message.MESSAGE_RECEIVE);
                            }
                            datas.add(msg);
                            Log.i(TAG, list.get(i).getBmobIMUserInfo().getUserId());
                            Log.i(TAG,mBmobIMConversation.getConversationId());
                        }

                    }
                }else {
                    Log.i(TAG, "查询信息出错！");
                }
            }
        });


        chatMessageItemRvItemAdapter = new ChatMessageItemRvItemAdapter(datas);
        chatMessageItemRvItemAdapter.openLoadAnimation();
        recyclerView.setAdapter(chatMessageItemRvItemAdapter);

    }

    @Override
    public void initListeners() {
        sendBtn.setOnClickListener(this);

        // 为rv中的每个item条目设置点击事件
        chatMessageItemRvItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
//        chatMessageItemRvItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (view.getId()){

//                }
//            }
//        });


        // 为recycleView设置下拉加载，上拉刷新功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                List<Message> newDatas = new ArrayList<>();
//                Message newData;
//                for (int i=0;i<3;i++){
//                    newData = new Message();
//                    newData.setContent("呵呵，我是新的第" + i + "条内容");
//                    if(i<1){
//                        newData.setType(Message.MESSAGE_RECEIVE);
//                    }else {
//                        newData.setType(Message.MESSAGE_SEND);
//                    }
//                    newDatas.add(newData);
//                }
//                chatMessageItemRvItemAdapter.setNewData(newDatas);
                refreshLayout.finishRefresh(1000); // 第一个参数表示延时时间，第二个参数若传入false表示刷新失败
            }
        });

//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                List<Message> moreDatas = new ArrayList<>();
//                Message moreData;
//                for (int i=0;i<4;i++){
//                    moreData = new Message();
//                    moreData.setContent("第" + i + "条内容");
//                    moreData.setType(Message.MESSAGE_RECEIVE);
//                    moreDatas.add(moreData);
//                }
//                chatMessageItemRvItemAdapter.getData().addAll(moreDatas);
//                chatMessageItemRvItemAdapter.notifyDataSetChanged();
//                refreshLayout.finishLoadMore(1000);
//            }
//        });
    }

    @Override
    public void initData() {
        recyclerView.scrollToPosition(chatMessageItemRvItemAdapter.getItemCount());
    }

    public static void startMyActivity(Context context, String para1, String para2){
        Intent intent = new Intent(context,ChatActivity.class);
        intent.putExtra("param1",para1);
        intent.putExtra("param2",para2);
        context.startActivity(intent);
    }

    @Override
    public void onClickBack(View view) {
//        BmobIM.getInstance().deleteConversation(mBmobIMConversation);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat_activity_send_btn:
                sendMessage();
                break;
            default:
                break;
        }
    }


    private void sendMessage() {
        BmobIMTextMessage msg = new BmobIMTextMessage();
        msg.setContent(editText.getText().toString());
        mBmobIMConversation.sendMessage(msg, new MessageSendListener() {

            @Override
            public void onStart(BmobIMMessage msg) {
                super.onStart(msg);
                recyclerView.scrollToPosition(chatMessageItemRvItemAdapter.getItemCount());
                Message newMsg = new Message();
                newMsg.setContent(msg.getContent());
                newMsg.setType(Message.MESSAGE_SEND);
                chatMessageItemRvItemAdapter.getData().add(newMsg);
                chatMessageItemRvItemAdapter.notifyDataSetChanged();
                editText.setText("");

            }

            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                if (e == null) {
                    Log.i(TAG, "发送消息成功！！！！");
                    ShowToast("发送消息成功！！");
                } else {
                    Log.i(TAG, "发送消息失败~");
                    ShowToast("发送消息失败~,请重试");
                }
            }
        });
    }

    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        //当注册页面消息监听时候，有消息（包含离线消息）到来时会回调该方法
        List<Message> receivedMsgs = new ArrayList<>();
        Message msg;
        for (int i=0;i<list.size();i++){
            msg = new Message();
            BmobIMMessage bmobIMMessage = list.get(i).getMessage();
            msg.setContent(bmobIMMessage.getContent());
            msg.setType(Message.MESSAGE_RECEIVE);
            receivedMsgs.add(msg);
        }
        chatMessageItemRvItemAdapter.getData().addAll(receivedMsgs);
        chatMessageItemRvItemAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatMessageItemRvItemAdapter.getItemCount());

    }
}
