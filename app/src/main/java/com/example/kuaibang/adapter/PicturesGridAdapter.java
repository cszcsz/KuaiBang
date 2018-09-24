package com.example.kuaibang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.kuaibang.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class PicturesGridAdapter extends BaseAdapter {


    private static final String TAG = "PicturesGridAdapter";
    private Context context;
    private List<String> picUrlList;
    private LayoutInflater inflater;
    private static final int MAX_PIC_NUM = 3;

    public PicturesGridAdapter(Context context,List<String> picUrlList){
        Log.d(TAG,"适配器初始化,准备图片加载");
        this.context = context;
        this.picUrlList = picUrlList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        int count = (picUrlList == null ? 1 : picUrlList.size() + 1);

        if(count > MAX_PIC_NUM)
            return MAX_PIC_NUM;
        else
            return count;
    }

    @Override
    public Object getItem(int position) {
        return picUrlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//
//        convertView = inflater.inflate(R.layout.publish_picture_item,parent,false);
//        ImageView picView = convertView.findViewById(R.id.picture_item);
//        if(position < picUrlList.size()){
//            String picUrl = picUrlList.get(position);
//            Log.d(TAG,"位置"+String.valueOf(position)+"的图片"+picUrl.toString()+"正在缓冲");
//            Glide.with(context).load(picUrl).into(picView);
//            Log.d(TAG,"图片宽度为"+String.valueOf(picView.getWidth()));
//        }else{
//            Log.d(TAG,"添加图片标志进行缓冲");
//            picView.setImageResource(R.mipmap.ic_addpic);
//        }
//        return convertView;

        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.publish_picture_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.pic = convertView.findViewById(R.id.picture_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Log.d(TAG,"当前图片数"+String.valueOf(getCount()));
        Log.d(TAG,"当前照片数"+String.valueOf(picUrlList.size()));
        Log.d(TAG,"位置"+String.valueOf(position)+"的图片正在缓冲");

        if(position == picUrlList.size() && position < MAX_PIC_NUM){
            viewHolder.pic.setImageResource(R.mipmap.ic_addpics);
        }else if(position < picUrlList.size()){
            Glide.with(context).load(picUrlList.get(position)).into(viewHolder.pic);
            //使用glide更改尺寸 requestOptions
        }

        return convertView;
    }

    private static class ViewHolder{
        ImageView pic;
    }
}
