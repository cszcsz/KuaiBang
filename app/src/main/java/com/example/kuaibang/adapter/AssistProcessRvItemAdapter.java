package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.kuaibang.IMApplication;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Helper;
import com.example.kuaibang.entity.ChatListItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AssistProcessRvItemAdapter extends BaseQuickAdapter<Helper,BaseViewHolder> {

    public AssistProcessRvItemAdapter(@Nullable List<Helper> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Helper>() {
            @Override
            protected int getItemType(Helper entity) {
                return entity.getHelpState();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(Helper.HELPS_STATE, R.layout.assist_process_helps_item)
                .registerItemType(Helper.HELPING_STATE,R.layout.assist_process_helping_item)
                .registerItemType(Helper.HELPED_STATE,R.layout.null_layout)
                .registerItemType(Helper.INVALID_STATE,R.layout.null_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, Helper item) {
        try{
            switch (helper.getItemViewType()){
                case Helper.HELPS_STATE:
                    if (item.getUser().getHead().getFileUrl()!=""){
                        Glide.with(IMApplication.getContext()).load(item.getUser().getHead().getFileUrl()).into((CircleImageView)helper.getView(R.id.assist_process_helps_item_userHead));
                    }else {
                        Glide.with(IMApplication.getContext()).load(R.mipmap.ic_userhead_boy).into((CircleImageView)helper.getView(R.id.assist_process_helps_item_userHead));
                    }
                    // 显示男女标识符号，用true表示男，用false表示女
                    if(item.getUser().getSex()!=null){
                        if(item.getUser().getSex() == true){
                            Glide.with(IMApplication.getContext()).load(R.mipmap.ic_boy_symbol).into((ImageView) helper.getView(R.id.assist_process_helps_item_userSexImg));
                        }else {
                            Glide.with(IMApplication.getContext()).load(R.mipmap.ic_girl_symbol).into((ImageView) helper.getView(R.id.assist_process_helps_item_userSexImg));
                        }
                    }

                    helper.addOnClickListener(R.id.assist_process_helps_item_acceptBtn)
                            .addOnClickListener(R.id.assist_process_helps_item_userHead)
                            .setText(R.id.assist_process_helps_item_userName,item.getUser().getUserName())
                            .setText(R.id.assist_process_helping_item_creditValue,item.getUser().getCredit())
                            .setText(R.id.assist_process_helps_item_helpRemark,item.getHelpRemark())
                            .setText(R.id.assist_process_helping_item_userHelpNum,"帮助过别人"+item.getUser().getHelpNum().toString()+"次");

                    break;
                case Helper.HELPING_STATE:
                    if (item.getUser().getHead().getFileUrl()!=""){
                        Glide.with(IMApplication.getContext()).load(item.getUser().getHead().getFileUrl()).into((CircleImageView)helper.getView(R.id.assist_process_helping_item_userHead));
                    }else {
                        Glide.with(IMApplication.getContext()).load(R.mipmap.ic_userhead_boy).into((CircleImageView)helper.getView(R.id.assist_process_helping_item_userHead));
                    }
                    // 显示男女标识符号，用true表示男，用false表示女
                    if(item.getUser().getSex()!=null){
                        if(item.getUser().getSex() == true){
                            Glide.with(IMApplication.getContext()).load(R.mipmap.ic_boy_symbol).into((ImageView) helper.getView(R.id.assist_process_helping_item_userSexImg));
                        }else {
                            Glide.with(IMApplication.getContext()).load(R.mipmap.ic_girl_symbol).into((ImageView) helper.getView(R.id.assist_process_helping_item_userSexImg));
                        }
                    }
                    helper.addOnClickListener(R.id.assist_process_helping_item_chatBtn)
                            .addOnClickListener(R.id.assist_process_helping_item_finishBtn)
                            .addOnClickListener(R.id.assist_process_helping_item_stopBtn)
                            .addOnClickListener(R.id.assist_process_helping_item_userHead)
                            .setText(R.id.assist_process_helping_item_userName,item.getUser().getUserName())
                            .setText(R.id.assist_process_helping_item_creditValue,item.getUser().getCredit())
                            .setText(R.id.assist_process_helping_item_helpRemark,item.getHelpRemark())
                            .setText(R.id.assist_process_helping_item_userHelpNum,"帮助过别人"+item.getUser().getHelpNum().toString()+"次");
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
