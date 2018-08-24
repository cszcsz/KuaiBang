package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kuaibang.entity.Test;

import java.util.List;

public class MessageHelpedRvAdapter extends BaseQuickAdapter<Test,BaseViewHolder> {

    public MessageHelpedRvAdapter(int layoutResId, @Nullable List<Test> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test item) {

    }
}
