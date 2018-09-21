package com.example.kuaibang.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kuaibang.IMApplication;
import com.example.kuaibang.MainActivity;
import com.example.kuaibang.R;
import com.example.kuaibang.entity.Post;
import com.example.kuaibang.entity.HomeRvItem;
import com.example.kuaibang.fragment.HomeMainFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeRvItemAdapter extends BaseQuickAdapter<Post,BaseViewHolder> {

    public HomeRvItemAdapter(int layoutResId, @Nullable List<Post> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post item) {
        try{
            // 显示帖子结束时间
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date date = new Date(BmobDate.getTimeStamp(item.getEndTime().getDate()));
            String dateTime = format.format(date);

            if (item.getUser().getHead().getFileUrl()!=""){
                Glide.with(IMApplication.getContext()).load(item.getUser().getHead().getFileUrl()).into((CircleImageView)helper.getView(R.id.home_rv_item_userImg));
            }else {
                Glide.with(IMApplication.getContext()).load(R.mipmap.ic_userhead_boy).into((CircleImageView)helper.getView(R.id.home_rv_item_userImg));
            }

            // 显示男女标识符号，用true表示男，用false表示女
            if(item.getUser().getSex()!=null){
                if(item.getUser().getSex() == true){
                    Glide.with(IMApplication.getContext()).load(R.mipmap.ic_boy_symbol).into((ImageView) helper.getView(R.id.home_rv_item_userSexImg));
                }else {
                    Glide.with(IMApplication.getContext()).load(R.mipmap.ic_girl_symbol).into((ImageView) helper.getView(R.id.home_rv_item_userSexImg));
                }
            }

            helper.addOnClickListener(R.id.home_rv_item_userImg) //点击用户头像事件
                    .setText(R.id.home_rv_item_title,item.getTitle())
                    .setText(R.id.home_rv_item_content,item.getContent())
                    .setText(R.id.home_rv_item_location,item.getRegion())
                    .setText(R.id.home_rv_item_score,Integer.toString(item.getScore()))
                    .setText(R.id.home_rv_item_time,dateTime)
                    .setText(R.id.home_rv_item_userName,item.getUser().getTrueName());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
