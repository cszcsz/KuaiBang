package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Test;

import java.util.List;

public class HomeRvItemAdapter extends BaseQuickAdapter<Test,BaseViewHolder> {

    public HomeRvItemAdapter(int layoutResId, @Nullable List<Test> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test item) {
        helper.setText(R.id.home_rv_item_title,item.getTitle())
                .setText(R.id.home_rv_item_content,item.getContent())
                .setImageResource(R.id.home_rv_item_img,R.mipmap.ic_launcher);
    }
}
