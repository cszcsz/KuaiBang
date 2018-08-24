package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Test;

import java.util.List;

public class MessageHelpingRvAdapter extends BaseQuickAdapter<Test,BaseViewHolder> {

    public MessageHelpingRvAdapter(int layoutResId, @Nullable List<Test> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test item) {
        helper.addOnClickListener(R.id.message_helping_rv_item_userImg)
                .addOnClickListener(R.id.message_helping_rv_item_chatBtn)
                .addOnClickListener(R.id.message_helping_rv_item_stopBtn);

    }
}
