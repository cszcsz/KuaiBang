package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Test;
import java.util.List;

public class MessageChatListItemAdapter extends BaseQuickAdapter<Test,BaseViewHolder> {

    public MessageChatListItemAdapter(int layoutResId, @Nullable List<Test> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test item) {
        helper.addOnClickListener(R.id.message_chat_list_person_headImg)
                .setText(R.id.message_chat_list_item_content,item.getContent());
    }
}
