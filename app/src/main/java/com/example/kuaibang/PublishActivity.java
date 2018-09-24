package com.example.kuaibang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.kuaibang.adapter.PicturesGridAdapter;
import com.example.kuaibang.entity.MyUser;
import com.example.kuaibang.entity.Picture;
import com.example.kuaibang.entity.Post;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PublishActivity extends TitleActivity implements View.OnClickListener {

    private static final String TAG = "PublishActivity";


//    Bmob相关
    MyUser myUser = MyUser.getCurrentUser(MyUser.class);
    Post post;


//    照片选择
    private GridView picturesGrid;
    private ArrayList<String> picUrlList = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    private PicturesGridAdapter picturesGridAdapter;
    private static final int MAX_PIC_NUM = 3;


//    选择器
    private final List<String> regionOptionsItems = new ArrayList<>();
    private OptionsPickerView regionPicker;
    private final List<String> helperNumOptionsItems = new ArrayList<>();
    private OptionsPickerView helperNumPicker;
    private List<String> dayOptionsItems = new ArrayList<>();
    private List<List<String>> hourOptionsItems = new ArrayList<>();
    private List<List<List<String>>> minOptionsItems = new ArrayList<>();
    private OptionsPickerView durationPicker;
    private long duration;


//    布局
    private TextView textRegion;
    private TextView textHelperNum;
    private TextView textDuration;
    private EditText textTopic;
    private EditText textContent;
    private EditText textAddress;
    private EditText textScore;
    private RelativeLayout regionLayout;
    private RelativeLayout helperNumLayout;
    private RelativeLayout durationLayout;
    private Button publishButton;

    private static final int PLUS_PIC = 0;

    private boolean bugFlag = true;

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
        setContentView(R.layout.publish_post);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle(R.string.publish_title);
        setRightTitle(R.string.publish_button);

        picturesGrid = findViewById(R.id.pictures_grid);

        textDuration = findViewById(R.id.publish_duration_edit);
        textHelperNum = findViewById(R.id.publish_helper_num_edit);
        textRegion = findViewById(R.id.publish_region_edit);
        textAddress = findViewById(R.id.publish_address_edit);
        textTopic = findViewById(R.id.publish_topic_edit);
        textContent = findViewById(R.id.publish_content_edit);
        textScore = findViewById(R.id.publish_score_edit);

        regionLayout = findViewById(R.id.region_picker);
        durationLayout = findViewById(R.id.duration_picker);
        helperNumLayout = findViewById(R.id.helper_num_picker);

        publishButton = findViewById(R.id.button_save);
    }

    @Override
    public void initListeners() {
        initGridView();

        regionLayout.setOnClickListener(this);
        durationLayout.setOnClickListener(this);
        helperNumLayout.setOnClickListener(this);

        publishButton.setOnClickListener(this);
    }

    @Override
    public void initData() {
        regionOptionsItems.add("南教");
        regionOptionsItems.add("东环");
        regionOptionsItems.add("西环");
        regionOptionsItems.add("东廊");
        regionOptionsItems.add("西廊");
        regionOptionsItems.add("图书馆");
        regionOptionsItems.add("荟萃广场");
        regionOptionsItems.add("基础实验楼A");
        regionOptionsItems.add("基础实验楼B");
        regionOptionsItems.add("基础实验楼C");
        regionOptionsItems.add("基础实验楼D");
        regionOptionsItems.add("唐岛湾餐厅");
        regionOptionsItems.add("玉兰苑餐厅");
        regionOptionsItems.add("荟萃餐厅");

        for(int i = 1 ; i < 100 ; ++i)
            helperNumOptionsItems.add(String.valueOf(i));

        for(int i = 0 ; i < 30 ; ++i)
            dayOptionsItems.add(String.valueOf(i));

        for (int i = 0 ; i < 30 ; ++i){
            List<String> dayTohour = new ArrayList<>();
            for (int j = 0 ; j < 60 ; ++j){
                dayTohour.add(String.valueOf(j));
            }
            hourOptionsItems.add(dayTohour);
        }

        for (int i = 0 ; i < 30 ; ++i){
            List<List<String>> dayTohour = new ArrayList<>();
            for (int j = 0 ; j < 60 ; ++j){
                List<String> hourTomin = new ArrayList<>();
                for (int k = 0 ; k < 60 ; ++k)
                    hourTomin.add(String.valueOf(k));
                dayTohour.add(hourTomin);
            }
            minOptionsItems.add(dayTohour);
        }

//
//        for(int i = 0 ; i < 24 ; ++i)
//            minTimeOptionsItems.add(String.valueOf(i));

        initOptionPicker();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.region_picker:
                regionPicker.show();
                break;
            case R.id.duration_picker:
                durationPicker.show();
                break;
            case R.id.helper_num_picker:
                helperNumPicker.show();
                break;
            case R.id.button_save:
                createPost();
                break;

             default:
        }

    }

    @Override
    public void onClickBack(View view) {
        super.onClickBack(view);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PLUS_PIC:
                break;
            case PictureConfig.CHOOSE_REQUEST:
                selectList = PictureSelector.obtainMultipleResult(data);
                refreshAdapter(selectList);
                break;
        }
    }

    private void initGridView(){
        picturesGridAdapter = new PicturesGridAdapter(this,picUrlList);
        picturesGrid.setAdapter(picturesGridAdapter);
        picturesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getChildCount() -1){
                    if(picUrlList.size() == MAX_PIC_NUM){
                        viewPlusPic(position);
                    }else{
                        addPics(MAX_PIC_NUM);
                    }
                }else {
                    viewPlusPic(position);
                }
            }
        });
    }

    private void viewPlusPic(int position){
        Intent intent = new Intent(PublishActivity.this,PlusPictureActivity.class);
        intent.putStringArrayListExtra("picUrlList",picUrlList);
        intent.putExtra("picPosition",position);
        startActivityForResult(intent,PLUS_PIC);
    }

    private void addPics(int currentMaxNum){
        PictureSelector.create(PublishActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_yellow_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(currentMaxNum)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void refreshAdapter(List<LocalMedia> selectList){
        picUrlList.clear();
        for(LocalMedia localMedia : selectList){
                String path = localMedia.getPath();
                picUrlList.add(path);
        }
        Log.d(TAG,String.valueOf(picUrlList.size())+"个图片已选中");
        picturesGridAdapter.notifyDataSetChanged();

//        if(bugFlag){
//            ImageView pic = (ImageView) picturesGridAdapter.getView(1,null,picturesGrid);
//            pic.setImageResource(R.mipmap.ic_addpics);
//            bugFlag = false;
//        }
    }


    private void initOptionPicker() {

        regionPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                textRegion.setText(regionOptionsItems.get(options1));
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

        helperNumPicker = new OptionsPickerBuilder(this,new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                textHelperNum.setText(helperNumOptionsItems.get(options1));
            }
        })
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setTitleText("")
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.rgb(217, 217, 217))
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();


        durationPicker = new OptionsPickerBuilder(this,new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                int d = options1 , h = options2 , m = options3;
                duration =  ((d*24*60+h*60+m)*1000);
                String durationText = dayOptionsItems.get(options1)+"  天  "
                        +hourOptionsItems.get(options1).get(options2)+"  时  "
                        +minOptionsItems.get(options1).get(options2).get(options3)+"  分  ";
                textDuration.setText(durationText);
            }
        })
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GRAY)//设置分割线的颜色
                .setSelectOptions(0, 0, 0)//默认选中项
                .setTitleText("")
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.rgb(217, 217, 217))
                .setTextColorCenter(Color.BLACK)
                .setLabels("天","时","分")
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();

        regionPicker.setPicker(regionOptionsItems);
        helperNumPicker.setPicker(helperNumOptionsItems);
        durationPicker.setPicker(dayOptionsItems,hourOptionsItems,minOptionsItems);
    }

    private void createPost(){
        post = new Post();
        //设置基本属性
        post.setTitle(textTopic.getText().toString());
        post.setContent(textContent.getContext().toString());
        post.setRegion(textRegion.getText().toString());
        post.setAddress(textAddress.getText().toString());
        Date date = new Date();
        date.setTime(date.getTime()+duration);
        BmobDate endTime = new BmobDate(date);
        post.setEndTime(endTime);
        post.setHelperNum(Integer.getInteger(textHelperNum.getText().toString()));
        post.setScore(Integer.getInteger(textScore.getText().toString()));
        //设置隐含属性
        post.setUser(myUser);
        post.setState(-1);

        post.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    ShowToast("发布成功！");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);//1秒后执行Runnable中的run方法
                }else{
                    Log.i("发布", e.getMessage());
                }
            }
        });

        List<Picture> pictures = new ArrayList<>();
        for(String picUrl : picUrlList){
            Picture picture = new Picture();

            BmobFile img = new BmobFile(new File(picUrl));

            picture.setImg(img);
            picture.setPost(post);

            picture.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e == null){

                    }else{

                    }
                }
            });
        }


    }
}


