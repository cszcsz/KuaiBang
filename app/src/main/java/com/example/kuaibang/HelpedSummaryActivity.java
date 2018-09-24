package com.example.kuaibang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kuaibang.entity.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class HelpedSummaryActivity extends TitleActivity implements View.OnClickListener {

    private TextView userName;
    private CircleImageView userImg;

    private TextView helpState;
    private TextView helpContent;
    private TextView helpDate;
    private TextView helpScore;
    private TextView helpTime;
    private TextView helpPerson;
    private TextView helpInfo;
    private TextView helpRate;

    private View bottomHelpDetail;
    private View bottomHelpNote;

    private String helperId;

    private String remarkInfo;

    private static final String TAG = "HelpedSummaryActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        helperId = intent.getStringExtra("helperId");
        setContentView();
        initView();
        initListeners();
        initData();
        setTitle(R.string.helped_summary_head_title);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.message_helped_summary);
    }

    @Override
    public void initView() {
        super.initView();
        userName = findViewById(R.id.helped_summary_user_name);
        userImg = findViewById(R.id.helped_summary_userimg);
        helpState = findViewById(R.id.helped_summary_state_text);
        helpContent = findViewById(R.id.helped_summary_state_content);
        helpDate = findViewById(R.id.helped_summary_state_date);
        helpScore = findViewById(R.id.helped_summary_state_score);
        helpTime = findViewById(R.id.helped_summary_state_time);
        helpPerson = findViewById(R.id.helped_summary_state_persons);
        helpInfo = findViewById(R.id.helped_summary_state_help_info);
        helpRate = findViewById(R.id.helped_summary_state_success_rate);

        bottomHelpDetail = findViewById(R.id.helped_summary_bottom_view1);
        bottomHelpNote = findViewById(R.id.helped_summary_bottom_view2);
    }

    @Override
    public void initListeners() {
        bottomHelpDetail.setOnClickListener(this);
        bottomHelpNote.setOnClickListener(this);
    }

    @Override
    public void initData() {
        BmobQuery<Helper> query = new BmobQuery<>();
        query.include("user,postUser,post");
        query.getObject(helperId, new QueryListener<Helper>() {
            @Override
            public void done(Helper helper, BmobException e) {
                if (e==null){
                    Log.i(TAG, "查询helper数据成功");
                    try{
                        Glide.with(HelpedSummaryActivity.this).load(helper.getPostUser().getHead().getFileUrl()).into((CircleImageView)findViewById(R.id.helped_summary_userimg));
                        userName.setText(helper.getPostUser().getUserName());
                        helpContent.setText(helper.getPost().getTitle());

                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        Date date = new Date(BmobDate.getTimeStamp(helper.getPost().getEndTime().getDate()));
                        String dateTime = format.format(date);
                        helpDate.setText(dateTime);
                        helpScore.setText(helper.getPost().getScore()+"积分");
                        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date2 = sDateFormat.parse(helper.getPost().getCreatedAt());
                        helpTime.setText(transferDateToHours(date2,date)+"小时");
                        helpPerson.setText(helper.getPost().getHelperNum()+"人");
                        helpInfo.setText("帮助过别人"+helper.getUser().getHelpNum()+"次");
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });
    }

    private String transferDateToHours(Date start,Date end){
        return  String.valueOf((end.getTime()-start.getTime())/1000/(60*60));
    }

    public static void startMyActivity(Context context, String helperId, String para2){
        Intent intent = new Intent(context,HelpedSummaryActivity.class);
        intent.putExtra("helperId",helperId);
        intent.putExtra("param2",para2);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.helped_summary_bottom_view1:
                BmobQuery<Helper> query = new BmobQuery<>();
                query.include("post,postUser,user");
                query.getObject(helperId, new QueryListener<Helper>() {
                    @Override
                    public void done(Helper helper, BmobException e) {
                        if (e==null){
                            Bundle bundle = new Bundle();
                            Intent intent= new Intent(HelpedSummaryActivity.this,HelpDetailActivity.class);
                            bundle.putString("userHead",helper.getPostUser().getHead().getFileUrl());
                            bundle.putBoolean("userSex",helper.getPostUser().getSex());
                            bundle.putString("userName",helper.getPostUser().getUserName());
                            bundle.putString("userCredit",helper.getPostUser().getCredit());
                            bundle.putString("postScore",helper.getPost().getScore().toString());
                            bundle.putString("postHelperNum",helper.getPost().getHelperNum().toString());
                            bundle.putString("postAddress",helper.getPost().getAddress());
                            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                            java.util.Date date = new java.util.Date(BmobDate.getTimeStamp(helper.getPost().getEndTime().getDate()));
                            String dateTime = format.format(date);
                            bundle.putString("postEndTime",dateTime);
                            bundle.putString("postContent",helper.getPost().getContent());
                            bundle.putBoolean("isShowBtn",false);
                            bundle.putString("postId",helper.getPost().getObjectId());
                            intent.putExtra("data",bundle);
                            remarkInfo = helper.getHelpRemark();
                            startActivity(intent);
                        }else {
                            e.printStackTrace();
                            Toast.makeText(HelpedSummaryActivity.this,"查询帖子详细信息出错",Toast.LENGTH_SHORT).show();
                            HelpDetailActivity.startMyActivity(HelpedSummaryActivity.this,false);
                        }
                    }
                });
                break;
            case R.id.helped_summary_bottom_view2:
                ShowToast("备注信息为："+remarkInfo);
                break;
            default:
        }
    }
}
