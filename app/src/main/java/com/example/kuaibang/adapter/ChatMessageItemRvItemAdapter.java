package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Message;
import com.example.kuaibang.entity.Test;

import java.util.List;

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
                helper.setText(R.id.item_chat_right_message,item.getContent());
                break;
            case Message.MESSAGE_RECEIVE:
                break;
        }
    }
}
