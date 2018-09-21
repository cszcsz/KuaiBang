package com.example.kuaibang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PersonalInfoEditActivity extends TitleActivity{

    private EditText editText;
    private Button saveButton;
    private ImageView clearImage;

    private CharSequence initialInfo;

    private static final int INTRODUCE = 3;
    private static final int USERNAME = 4;
    private static final int TRUENAME = 5;
    private static final int STUDENTID = 6;
    private static final int SCHOOL = 7;
    private static final int COLLEGE = 8;
    private static final int MAJOR = 9;
    private static int kind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListeners();
        initData();
    }

    public void setContentView(){
        setContentView(R.layout.personal_info_edit);
    }

    public  void initView(){
        super.initView();

        kind = getIntent().getIntExtra("kind",0);
        switch (kind){
            case INTRODUCE:
                setTitle(R.string.personal_info_introduce);
                break;
            case USERNAME:
                setTitle(R.string.personal_info_username);
                break;
            case TRUENAME:
                setTitle(R.string.personal_info_truename);
                break;
            case STUDENTID:
                setTitle(R.string.personal_info_truename);
                break;
            case SCHOOL:
                setTitle(R.string.personal_info_truename);
                break;
            case COLLEGE:
                setTitle(R.string.personal_info_truename);
                break;
            case MAJOR:
                setTitle(R.string.personal_info_truename);
                break;
            default:
        }

        showSaveButton();

        editText = findViewById(R.id.personal_info_edit_text);
        saveButton = findViewById(R.id.button_save);
        clearImage = findViewById(R.id.personal_info_edit_clear);

    }

    public  void initData(){
        Intent intent = getIntent();
        CharSequence info = intent.getCharSequenceExtra("info");
        editText.setText(info);

        initialInfo = info;
    }

    public  void initListeners(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("info",editText.getText());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        clearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editText.setSelection(charSequence.length());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() > 19){
                    ShowToast("输入字数超出限制！");
                }
            }
        });
    }



}
