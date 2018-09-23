package com.example.kuaibang.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

public class AssistMainRvItemAdapter extends BaseQuickAdapter<Post,BaseViewHolder>{

    public AssistMainRvItemAdapter(@Nullable List<Post> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Post>() {
            @Override
            protected int getItemType(Post entity) {
                return entity.getState();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(Post.HELPS_STATE, R.layout.message_assist_helps_item)
                .registerItemType(Post.HELPING_STATE,R.layout.message_assist_helping_item)
                .registerItemType(Post.HELPED_STATE,R.layout.message_assist_helped_item)
                .registerItemType(Post.INVALID_STATE,R.layout.message_assist_invalid_item);

    }

    @Override
    protected void convert(BaseViewHolder helper, Post item) {
        switch (helper.getItemViewType()){
            case Post.HELPS_STATE:
                try{
                    // 显示帖子结束时间
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    Date date = new Date(BmobDate.getTimeStamp(item.getEndTime().getDate()));
                    String dateTime = format.format(date);

                    helper.addOnClickListener(R.id.assist_helps_item_more) //点击用户头像事件
                            .setText(R.id.assist_invalid_item_title,item.getTitle())
                            .setText(R.id.assist_invalid_item_content,item.getContent())
                            .setText(R.id.assist_invalid_item_person,item.getHelperNum().toString())
                            .setText(R.id.assist_helps_item_time,dateTime);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case Post.HELPING_STATE:
                try{
                    // 显示帖子结束时间
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    Date date = new Date(BmobDate.getTimeStamp(item.getEndTime().getDate()));
                    String dateTime = format.format(date);

                    helper.addOnClickListener(R.id.assist_helping_item_more) //点击用户头像事件
                            .setText(R.id.assist_helping_item_title,item.getTitle())
                            .setText(R.id.assist_helping_item_content,item.getContent())
                            .setText(R.id.assist_helping_item_person,item.getHelperNum().toString())
                            .setText(R.id.assist_helping_item_time,dateTime);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case Post.HELPED_STATE:
                try {
                    helper.addOnClickListener(R.id.assist_helped_item_more) //点击用户头像事件
                            .setText(R.id.assist_helped_item_title,item.getTitle())
                            .setText(R.id.assist_helped_item_content,item.getContent())
                            .setText(R.id.assist_helped_item_person,item.getHelperNum().toString());

                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Post.INVALID_STATE:
                try {
                    helper.addOnClickListener(R.id.assist_invalid_item_more) //点击用户头像事件
                            .setText(R.id.assist_invalid_item_title,item.getTitle())
                            .setText(R.id.assist_invalid_item_content,item.getContent())
                            .setText(R.id.assist_invalid_item_person,item.getHelperNum().toString());

                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
