package com.example.kuaibang.adapter;


import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Test;

import java.util.List;

public class AssistMainRvItemAdapter extends BaseQuickAdapter<Test,BaseViewHolder>{

    public AssistMainRvItemAdapter(@Nullable List<Test> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Test>() {
            @Override
            protected int getItemType(Test entity) {
                return entity.getType();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(Test.HELPS_STATE, R.layout.message_assist_helps_item)
                .registerItemType(Test.HELPING_STATE,R.layout.message_assist_helping_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test item) {
        switch (helper.getItemViewType()){
            case Test.HELPS_STATE:
                helper.addOnClickListener(R.id.assist_helps_item_more);
                break;
            case Test.HELPING_STATE:
                helper.addOnClickListener(R.id.assist_helping_item_more);
                break;
        }
    }
}
