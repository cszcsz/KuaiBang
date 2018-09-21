package com.example.kuaibang;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.app.Dialog;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PersonalInfoActivity extends TitleActivity implements View.OnClickListener{

    private RelativeLayout infoHead;
    private RelativeLayout infoIntroduce;
    private RelativeLayout infoId;
    private RelativeLayout infoUserName;
    private RelativeLayout infoUserSex;
    private RelativeLayout infoTrueName;
    private RelativeLayout infoStudentId;
    private RelativeLayout infoSchool;
    private RelativeLayout infoCollege;
    private RelativeLayout infoMajor;
    private RelativeLayout infoGrade;

    private TextView textIntroduce;
    private TextView textUserName;
    private TextView textCollege;
    private TextView textTrueName;
    private TextView textStudentId;
    private TextView textSchool;
    private TextView textMajor;
    private TextView textUserSex;
    private TextView textGrade;

    private ImageView userHead;
    private Uri imageUri;

    private final List<String> userSexOptionsItems = new ArrayList<>();
    private OptionsPickerView userSexPicker;
    private final List<String> gradeOptionsItems = new ArrayList<>();
    private OptionsPickerView gradePicker;

    private static final int TAKE = 1;
    private static final int ALBUM = 2;
    private static final int INTRODUCE = 3;
    private static final int USERNAME = 4;
    private static final int TRUENAME = 5;
    private static final int STUDENTID = 6;
    private static final int SCHOOL = 7;
    private static final int COLLEGE = 8;
    private static final int MAJOR = 9;
    private static final int USERSEX = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListeners();
        initData();
    }

    @Override
    public void initView() {
        super.initView();
        setTitle(R.string.personal_info_title);

        infoHead = findViewById(R.id.personal_info_head);
        infoIntroduce = findViewById(R.id.personal_info_introduce);
        infoId = findViewById(R.id.personal_info_id);
        infoUserName = findViewById(R.id.personal_info_username);
        infoUserSex = findViewById(R.id.personal_info_usersex);
        infoTrueName = findViewById(R.id.personal_info_truename);
        infoStudentId = findViewById(R.id.personal_info_studentid);
        infoSchool = findViewById(R.id.personal_info_school);
        infoCollege = findViewById(R.id.personal_info_college);
        infoMajor = findViewById(R.id.personal_info_major);
        infoGrade = findViewById(R.id.personal_info_grade);

        userHead = findViewById(R.id.userhead_image);

        textIntroduce = findViewById(R.id.personal_info_introduce_text);
        textUserName = findViewById(R.id.personal_info_username_text);
        textTrueName = findViewById(R.id.personal_info_truename_text);
        textStudentId = findViewById(R.id.personal_info_studentid_text);
        textSchool = findViewById(R.id.personal_info_school_text);
        textCollege = findViewById(R.id.personal_info_college_text);
        textMajor = findViewById(R.id.personal_info_major_text);
        textUserSex = findViewById(R.id.personal_info_usersex_text);
        textGrade = findViewById(R.id.personal_info_grade_text);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.personal_info);
    }

    @Override
    public void initListeners() {
        infoHead.setOnClickListener(this);
        infoIntroduce.setOnClickListener(this);
        infoId.setOnClickListener(this);
        infoUserName.setOnClickListener(this);
        infoUserSex.setOnClickListener(this);
        infoTrueName.setOnClickListener(this);
        infoStudentId.setOnClickListener(this);
        infoSchool.setOnClickListener(this);
        infoCollege.setOnClickListener(this);
        infoMajor.setOnClickListener(this);
        infoGrade.setOnClickListener(this);

    }

    @Override
    public void initData() {

        userSexOptionsItems.add("男");
        userSexOptionsItems.add("女");

        gradeOptionsItems.add("2015级");
        gradeOptionsItems.add("2016级");
        gradeOptionsItems.add("2017级");
        gradeOptionsItems.add("2018级");


        initOptionPicker();

    }

    @Override
    public void onClickBack(View view) {
        Intent intent = new Intent();
        intent.putExtra("username",textUserName.getText());
        intent.putExtra("headUri",imageUri.toString());
        setResult(RESULT_OK,intent);
        super.onClickBack(view);
    }

    @Override
    public void onClick(View view) {
        Log.d("edit","onclick");
        switch(view.getId())
        {
            case R.id.personal_info_head:{
                showChangeHeadDialog();
                break;
            }


            case R.id.personal_info_introduce:{
                Intent intent = new Intent(PersonalInfoActivity.this,PersonalInfoEditActivity.class);
                intent.putExtra("kind",INTRODUCE);
                intent.putExtra("info",textIntroduce.getText());
                startActivityForResult(intent,INTRODUCE);
                break;
            }


            case R.id.personal_info_username:{
                Intent intent = new Intent(PersonalInfoActivity.this,PersonalInfoEditActivity.class);
                intent.putExtra("kind",USERNAME);
                intent.putExtra("info",textUserName.getText());
                startActivityForResult(intent,USERNAME);
                break;
            }

            case R.id.personal_info_truename:{
                Intent intent = new Intent(PersonalInfoActivity.this,PersonalInfoEditActivity.class);
                intent.putExtra("kind",TRUENAME);
                intent.putExtra("info",textTrueName.getText());
                startActivityForResult(intent,TRUENAME);
                break;
            }

            case R.id.personal_info_studentid:{
                Intent intent = new Intent(PersonalInfoActivity.this,PersonalInfoEditActivity.class);
                intent.putExtra("kind",STUDENTID);
                intent.putExtra("info",textStudentId.getText());
                startActivityForResult(intent,STUDENTID);
                break;
            }

            case R.id.personal_info_school:{
                Intent intent = new Intent(PersonalInfoActivity.this,PersonalInfoEditActivity.class);
                intent.putExtra("kind",SCHOOL);
                intent.putExtra("info",textSchool.getText());
                startActivityForResult(intent,SCHOOL);
                break;
            }

            case R.id.personal_info_college:{
                Intent intent = new Intent(PersonalInfoActivity.this,PersonalInfoEditActivity.class);
                intent.putExtra("kind",COLLEGE);
                intent.putExtra("info",textCollege.getText());
                startActivityForResult(intent,COLLEGE);
                break;
            }

            case R.id.personal_info_major:{
                Intent intent = new Intent(PersonalInfoActivity.this,PersonalInfoEditActivity.class);
                intent.putExtra("kind",MAJOR);
                intent.putExtra("info",textMajor.getText());
                startActivityForResult(intent,MAJOR);
                break;
            }

            case R.id.personal_info_usersex:{
                userSexPicker.show();
                break;
            }

            case R.id.personal_info_grade:{
                gradePicker.show();
                break;
            }

            default:
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE:
                if(resultCode == RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        userHead.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM:
                if(resultCode == RESULT_OK){

                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            case INTRODUCE:
                if(resultCode == RESULT_OK){
                    CharSequence introduce = data.getCharSequenceExtra("info");
                    textIntroduce.setText(introduce);
                }
                break;

            case USERNAME:
                if(resultCode == RESULT_OK){
                    CharSequence username = data.getCharSequenceExtra("info");
                    textUserName.setText(username);
                }
                break;

            case TRUENAME:
                if(resultCode == RESULT_OK){
                    CharSequence truename = data.getCharSequenceExtra("info");
                    textTrueName.setText(truename);
                }
                break;

            case STUDENTID:
                if(resultCode == RESULT_OK){
                    CharSequence studentid = data.getCharSequenceExtra("info");
                    textStudentId.setText(studentid);
                }
                break;

            case SCHOOL:
                if(resultCode == RESULT_OK){
                    CharSequence school = data.getCharSequenceExtra("info");
                    textSchool.setText(school);
                }
                break;

            case COLLEGE:
                if(resultCode == RESULT_OK){
                    CharSequence college = data.getCharSequenceExtra("info");
                    textCollege.setText(college);
                }
                break;

            case MAJOR:
                if(resultCode == RESULT_OK){
                    CharSequence major = data.getCharSequenceExtra("info");
                    textMajor.setText(major);
                }
                break;

            default:
                break;
        }

        }


    private void showChangeHeadDialog(){

        final Dialog changeHeadDialog = new Dialog(PersonalInfoActivity.this);
        changeHeadDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        changeHeadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final LayoutInflater inflater = LayoutInflater.from(PersonalInfoActivity.this);
        View changeHeadDialogView = inflater.inflate(R.layout.personal_change_head_dialog,null);
        changeHeadDialog.setContentView(changeHeadDialogView);

        TextView changeByTake = changeHeadDialogView.findViewById(R.id.personal_change_head_take);
        TextView changeFromAlbum = changeHeadDialogView.findViewById(R.id.personal_change_head_album);

        changeByTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeadDialog.dismiss();
                File headImage = new File(getExternalCacheDir(),"head_image.jpg");
                try{
                    if(headImage.exists())
                        headImage.delete();
                    headImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(PersonalInfoActivity.this
                    ,"com.example.kuaibang.PersonalInfoActivity",headImage);
                }else{
                    imageUri = Uri.fromFile(headImage);
                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE);
            }
        });

        changeFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeadDialog.dismiss();
                if(ContextCompat.checkSelfPermission(PersonalInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PersonalInfoActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });


        changeHeadDialog.show();

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,ALBUM);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "未取得权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            userHead.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取图像失败", Toast.LENGTH_SHORT).show();
        }
    }


    private void initOptionPicker() {//条件选择器初始化

        userSexPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                textUserSex.setText(userSexOptionsItems.get(options1));
            }
        })
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setTitleText("")
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.rgb(217, 217, 217))
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();

        gradePicker = new OptionsPickerBuilder(this,new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                textGrade.setText(gradeOptionsItems.get(options1));
            }
        })
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setTitleText("")
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.rgb(217, 217, 217))
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();

        userSexPicker.setPicker(userSexOptionsItems);
        gradePicker.setPicker(gradeOptionsItems);
    }

}


