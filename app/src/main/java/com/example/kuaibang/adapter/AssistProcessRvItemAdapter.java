package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Test;

import java.util.List;

public class AssistProcessRvItemAdapter extends BaseQuickAdapter<Test,BaseViewHolder> {

    public AssistProcessRvItemAdapter(@Nullable List<Test> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Test>() {
            @Override
            protected int getItemType(Test entity) {
                return entity.getType();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(Test.HELPS_STATE, R.layout.assist_process_helps_item)
                .registerItemType(Test.HELPING_STATE,R.layout.assist_process_helping_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test item) {
        switch (helper.getItemViewType()){
            case Test.HELPS_STATE:
                helper.addOnClickListener(R.id.assist_process_helps_item_acceptBtn)
                        .addOnClickListener(R.id.assist_process_helps_item_userImg);
                break;
            case Test.HELPING_STATE:
                helper.addOnClickListener(R.id.assist_process_helping_item_chatBtn)
                        .addOnClickListener(R.id.assist_process_helping_item_finishBtn)
                        .addOnClickListener(R.id.assist_process_helping_item_stopBtn)
                        .addOnClickListener(R.id.assist_process_helping_item_userImg);
                break;
        }
    }
}
