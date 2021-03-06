package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kuaibang.IMApplication;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageHelpedRvAdapter extends BaseQuickAdapter<Helper,BaseViewHolder> {

    public MessageHelpedRvAdapter(int layoutResId, @Nullable List<Helper> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Helper item) {
        try{


            if (item.getPostUser().getHead().getFileUrl()!=""){
                Glide.with(IMApplication.getContext()).load(item.getPostUser().getHead().getFileUrl()).into((CircleImageView)helper.getView(R.id.message_helped_rv_item_userImg));
            }else {
                Glide.with(IMApplication.getContext()).load(R.mipmap.ic_userhead_boy).into((CircleImageView)helper.getView(R.id.message_helped_rv_item_userImg));
            }

            // 显示男女标识符号，用true表示男，用false表示女
            if(item.getPostUser().getSex()!=null){
                if(item.getPostUser().getSex() == true){
                    Glide.with(IMApplication.getContext()).load(R.mipmap.ic_boy_symbol).into((ImageView) helper.getView(R.id.message_helped_rv_item_userSexImg));
                }else {
                    Glide.with(IMApplication.getContext()).load(R.mipmap.ic_girl_symbol).into((ImageView) helper.getView(R.id.message_helped_rv_item_userSexImg));
                }
            }

            helper.addOnClickListener(R.id.message_helped_rv_item_userImg)
                    .setText(R.id.message_helped_rv_item_userName,item.getPostUser().getUserName())
                    .setText(R.id.message_helped_rv_item_title,item.getPost().getTitle())
                    .setText(R.id.message_helped_rv_item_content,item.getPost().getContent())
                    .setText(R.id.message_helped_rv_item_location,item.getPost().getRegion())
                    .setText(R.id.message_helped_rv_item_score,item.getPost().getScore().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
