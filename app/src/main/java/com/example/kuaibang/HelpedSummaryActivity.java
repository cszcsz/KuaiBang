package com.example.kuaibang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        userName.setText("");
    }


    public static void startMyActivity(Context context, String para1, String para2){
        Intent intent = new Intent(context,HelpedSummaryActivity.class);
        intent.putExtra("param1",para1);
        intent.putExtra("param2",para2);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.helped_summary_bottom_view1:
               HelpDetailActivity.startMyActivity(HelpedSummaryActivity.this,false);
                break;
            case R.id.helped_summary_bottom_view2:
                ShowToast("进入备注页面");
                break;
            default:
        }
    }
}
