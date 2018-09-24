package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.kuaibang.IMApplication;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Message;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMessageItemRvItemAdapter extends BaseQuickAdapter<Message,BaseViewHolder> {

    public ChatMessageItemRvItemAdapter(@Nullable List<Message> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Message>() {
            @Override
            protected int getItemType(Message entity) {
                return entity.getType();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(Message.MESSAGE_SEND, R.layout.item_chat_send_message)
                .registerItemType(Message.MESSAGE_RECEIVE,R.layout.item_chat_received_message);

    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        switch (helper.getItemViewType()){
            case Message.MESSAGE_SEND:
                if (item.getAvatar()==""){
                    Glide.with(IMApplication.getContext()).load(R.mipmap.ic_userhead_boy).into((CircleImageView)helper.getView(R.id.item_chat_right_userAvatar));
                }else {
                    Glide.with(IMApplication.getContext()).load(item.getAvatar()).into((CircleImageView)helper.getView(R.id.item_chat_right_userAvatar));
                }
                helper.setText(R.id.item_chat_right_message,item.getContent());
                break;
            case Message.MESSAGE_RECEIVE:
                if (item.getAvatar()==""){
                    Glide.with(IMApplication.getContext()).load(R.mipmap.ic_userhead_girl).into((CircleImageView)helper.getView(R.id.item_chat_left_userAvatar));
                }else {
                    Glide.with(IMApplication.getContext()).load(item.getAvatar()).into((CircleImageView)helper.getView(R.id.item_chat_left_userAvatar));
                }
                helper.setText(R.id.item_chat_left_message,item.getContent());
                break;
        }
    }
}
