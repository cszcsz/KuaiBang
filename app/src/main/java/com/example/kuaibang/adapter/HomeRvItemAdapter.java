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
        helper.addOnClickListener(R.id.home_rv_item_userImg);   //点击用户头像事件

// TODO: 通过后台获取实体对象后，在此将实体数据显示到item上
//        helper.setText(R.id.home_rv_item_title,item.getTitle())
//                .setText(R.id.home_rv_item_content,item.getContent())
//                .setImageResource(R.id.home_rv_item_img,R.mipmap.ic_launcher);
//        Glide使用示例
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) viewHolder.getView(R.id.iv));
    }
}
