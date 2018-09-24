package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kuaibang.IMApplication;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.ChatListItem;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageChatListItemAdapter extends BaseQuickAdapter<ChatListItem,BaseViewHolder> {

    public MessageChatListItemAdapter(int layoutResId, @Nullable List<ChatListItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatListItem item) {
        helper.addOnClickListener(R.id.message_chat_list_person_headImg)
                .setText(R.id.message_chat_list_item_content,item.getContent())
                .setText(R.id.message_chat_list_person_name,item.getTitle());
        Glide.with(IMApplication.getContext()).load(item.getImgUrl()).into((CircleImageView)helper.getView(R.id.message_chat_list_person_headImg));
    }
}
