package com.example.kuaibang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kuaibang.entity.Helper;
import com.example.kuaibang.entity.MyUser;
import com.example.kuaibang.entity.Picture;
import com.example.kuaibang.entity.Post;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class HelpDetailActivity extends TitleActivity implements View.OnClickListener {

    private Button helpBtn;
    private Dialog dialog;
    private Dialog success_dialog;
    private Button confirmBtn;
    private Button concelBtn;
    private EditText editText;

    private CircleImageView circleImageView;
    private TextView userName;
    private ImageView userSex;
    private TextView userCredit;
    private TextView postScore;
    private TextView postHelerNum;
    private TextView postAddress;
    private TextView postEndTime;
    private TextView postContnt;

    private ImageView postImg1;
    private ImageView postImg2;
    private ImageView postImg3;

    private String postId;

    MyUser currentUser;
    
    boolean helpBtnIsShow = true;
    private static final String TAG = "HelpDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = BmobUser.getCurrentUser(MyUser.class);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        helpBtnIsShow = bundle.getBoolean("isShowBtn");
        setContentView();
        initView();
        initListeners();
        initData();
        getIntentData();
        initializePostImg();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.help_detail);
    }

    @Override
    public void initView() {
        super.initView();
        helpBtn = findViewById(R.id.help_detail_help_btn);
        if(!helpBtnIsShow){
            helpBtn.setVisibility(View.GONE);
        }
        circleImageView = findViewById(R.id.help_detail_userhead);
        userName = findViewById(R.id.help_detail_user_name);
        userSex = findViewById(R.id.help_detail_user_sexsymbol);
        userCredit = findViewById(R.id.help_detail_user_credit);
        postScore = findViewById(R.id.help_detail_score);
        postHelerNum = findViewById(R.id.help_detail_persons);
        postAddress = findViewById(R.id.help_detail_location);
        postEndTime = findViewById(R.id.help_detail_time);
        postContnt = findViewById(R.id.help_detail_content);
        postImg1 = findViewById(R.id.help_detail_img1);
        postImg2 = findViewById(R.id.help_detail_img2);
        postImg3 = findViewById(R.id.help_detail_img3);
    }

    @Override
    public void initListeners() {
        helpBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitle(R.string.help_detail_title);
    }

    // 启动活动的最佳写法,para1和para2为传递到该活动的数据参数,到时候根据实际情况来传数据
    public static void startMyActivity(Context context,boolean isShowBtn){
        Intent intent = new Intent(context,HelpDetailActivity.class);
        intent.putExtra("showBtn",isShowBtn);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.help_detail_help_btn:
                showDialog();
                break;
            default:
        }
    }

    private void checkSelfUserHelp(){
        BmobQuery<Post> query = new BmobQuery<>();
        query.include("user");
        query.getObject(postId, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                if (e==null){
                    if(post.getUser().getObjectId().equals(currentUser.getObjectId())){
                        helpBtn.setVisibility(View.GONE);
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getIntentData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Glide.with(HelpDetailActivity.this).load(bundle.getString("userHead")).into((CircleImageView)findViewById(R.id.help_detail_userhead));
        if(bundle.getBoolean("userSex")==true){
            userSex.setImageResource(R.mipmap.ic_boy_symbol);
        }else {
            userSex.setImageResource(R.mipmap.ic_girl_symbol);
        }
        userName.setText(bundle.getString("userName"));
        userCredit.setText(bundle.getString("userCredit"));
        postScore.setText(bundle.getString("postScore"));
        postHelerNum.setText(bundle.getString("postHelperNum"));
        postAddress.setText(bundle.getString("postAddress"));
        postEndTime.setText(bundle.getString("postEndTime"));
        postContnt.setText(bundle.getString("postContent"));
        postId = bundle.getString("postId");

    }

    private void initializePostImg(){
        BmobQuery<Picture> query = new BmobQuery<>();
        query.addWhereEqualTo("post",postId);
        Log.i(TAG, "initializePostImg: "+postId);
        query.findObjects(new FindListener<Picture>() {
            @Override
            public void done(List<Picture> list, BmobException e) {
                if (e==null){
                    Log.i(TAG, String.valueOf(list.size()));
                    switch (list.size()){
                        case 1:
                            Glide.with(HelpDetailActivity.this).load(list.get(0).getImg().getFileUrl()).into((ImageView) findViewById(R.id.help_detail_img1));
                            break;
                        case 2:
                            Glide.with(HelpDetailActivity.this).load(list.get(0).getImg().getFileUrl()).into((ImageView) findViewById(R.id.help_detail_img1));
                            Glide.with(HelpDetailActivity.this).load(list.get(1).getImg().getFileUrl()).into((ImageView) findViewById(R.id.help_detail_img2));
                            break;
                        case 3:
                            Glide.with(HelpDetailActivity.this).load(list.get(0).getImg().getFileUrl()).into((ImageView) findViewById(R.id.help_detail_img1));
                            Glide.with(HelpDetailActivity.this).load(list.get(1).getImg().getFileUrl()).into((ImageView) findViewById(R.id.help_detail_img2));
                            Glide.with(HelpDetailActivity.this).load(list.get(2).getImg().getFileUrl()).into((ImageView) findViewById(R.id.help_detail_img3));
                            break;
                    }
                    Log.i(TAG, "查询帖子对应的图片信息成功");
                }else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void craetHelper(){
        BmobQuery<Post> query = new BmobQuery<>();
        query.include("user");
        query.getObject(postId, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                if (e==null){
                    Helper newHelper = new Helper();
                    newHelper.setHelpRemark(editText.getText().toString());
                    newHelper.setUser(currentUser);
                    newHelper.setPost(post);
                    newHelper.setPostUser(post.getUser());
                    newHelper.setHelpState(1);
                    newHelper.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                Log.i(TAG, "创建新的helper成功");
                            }else {
                                e.printStackTrace();
                            }
                        }
                    });
                    showSuccessDialog();
                }else{
                    e.printStackTrace();
                }
            }
        });
    }


    private void showDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.help_detail_dialog,null);
        dialog.setContentView(dialogView);
        dialog.show();
        dialog.setCancelable(false);
        initDialogView(dialogView);
        initDialogListener();

    }

    private void initDialogView(View view){
        confirmBtn = view.findViewById(R.id.help_detail_confirm_btn);
        concelBtn = view.findViewById(R.id.help_dialog_cancel_btn);
        editText = view.findViewById(R.id.help_detail_dialog_edit);
        editText.setSingleLine(false);
        editText.setHorizontallyScrolling(false);
    }

    private void initDialogListener(){
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                craetHelper();
                dialog.dismiss();

            }
        });

        concelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void showSuccessDialog(){
        success_dialog = new Dialog(this);
        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(this);
        View successDialogView = inflater.inflate(R.layout.help_detail_success_dialog,null);
        success_dialog.setContentView(successDialogView);
        success_dialog.setCancelable(false);
        success_dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                success_dialog.dismiss();
                finish();
            }
        },2000);
    }
}
