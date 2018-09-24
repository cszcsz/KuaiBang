package com.example.kuaibang;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kuaibang.entity.MyUser;

import java.io.FileNotFoundException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static cn.bmob.newim.core.BmobIMClient.getContext;

public class PersonalCenterActivity extends TitleActivity implements View.OnClickListener{

    private RelativeLayout personalExperience;
    private RelativeLayout personalInfo;
    private RelativeLayout personalSettings;
    private RelativeLayout personalAbout;
    private RelativeLayout personalLogout;

    private TextView userName;
    private ImageView userHead;
    private TextView userScore;
    private TextView userCredit;

    private static final int EXPERIENCE = 0;
    private static final int INFO = 1;
    private static final int SETTINGS = 2;
    private static final int ABOUT = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListeners();
        initData();


    }

    @Override
    public void setContentView() {
        setContentView(R.layout.personal_center);

    }

    @Override
    public void initView() {
        super.initView();
        setTitle(R.string.personal_center_title);

        personalExperience = findViewById(R.id.personal_experience);
        personalInfo =  findViewById(R.id.personal_info);
        personalSettings =  findViewById(R.id.personal_settings);
        personalAbout =  findViewById(R.id.personal_about);
        personalLogout =  findViewById(R.id.personal_logout);

        userName = findViewById(R.id.username_text);
        userHead = findViewById(R.id.userhead_image);
        userScore = findViewById(R.id.userscore_text);
        userCredit = findViewById(R.id.usercredit_text);


    }

    @Override
    public void initListeners() {
        personalExperience.setOnClickListener(this);
        personalInfo.setOnClickListener(this);
        personalSettings.setOnClickListener(this);
        personalAbout.setOnClickListener(this);
        personalLogout.setOnClickListener(this);
        
    }

    @Override
    public void initData() {
        initPersonalInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.personal_experience:
//                Intent experienceIntent = new Intent(PersonalCenterActivity.this,PersonalExperienceActivity.class);
//                startActivityForResult(experienceIntent,EXPERIENCE);
//                break;
            case R.id.personal_info:
                Intent infoIntent = new Intent(PersonalCenterActivity.this,PersonalInfoActivity.class);
                startActivityForResult(infoIntent,INFO);
                break;
            case R.id.personal_settings:
                Intent settingsIntent = new Intent(PersonalCenterActivity.this,PersonalSettingsActivity.class);
                startActivityForResult(settingsIntent,SETTINGS);
                break;
            case R.id.personal_about:
                Intent aboutIntent = new Intent(PersonalCenterActivity.this,PersonalAboutActivity.class);
                startActivityForResult(aboutIntent,ABOUT);
                break;
            case R.id.personal_logout:
                showExitDialog();
                break;
            default:


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case INFO:
                if(resultCode == RESULT_OK){
                     userName.setText(data.getCharSequenceExtra("username"));
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(
                                new Uri.Builder().appendPath(data.getStringExtra("headUri")).build()
                        ));
                        userHead.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void showExitDialog(){

        final Dialog logoutDialog = new Dialog(PersonalCenterActivity.this);
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(PersonalCenterActivity.this);
        View logoutDialogView = inflater.inflate(R.layout.personal_logout_dialog,null);
        logoutDialog.setContentView(logoutDialogView);
        logoutDialog.setCancelable(false);

        TextView logoutCancel =  logoutDialogView.findViewById(R.id.personal_logout_cancel);
        TextView logoutEnsure =  logoutDialogView.findViewById(R.id.personal_logout_ensure);

        logoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
            }
        });
        logoutEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        logoutDialog.show();

    }

    private void initPersonalInfo(){

        MyUser myUser = MyUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.getObject(myUser.getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e == null){
                    String path = myUser.getHead().getUrl();
                    Glide.with(PersonalCenterActivity.this).load(path).into(userHead);
                    userName.setText(myUser.getUserName());
                    userCredit.setText(myUser.getCredit());
                    userScore.setText(myUser.getScore());
                }
            }
        });
    }


}
