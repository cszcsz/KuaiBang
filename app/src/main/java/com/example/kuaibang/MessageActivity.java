package com.example.kuaibang;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kuaibang.fragment.MessageAssistFragment;
import com.example.kuaibang.fragment.MessageChatFragment;
import com.example.kuaibang.fragment.MessageHelpFragment;

public class MessageActivity extends TitleActivity implements View.OnClickListener {

    private TextView helpView;
    private TextView assistView;
    private TextView chatView;


    private Fragment currentFragment = new Fragment();
    private FragmentManager fragmentManager;

    private MessageHelpFragment helpFragment;
    private MessageAssistFragment assistFragment;
    private MessageChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListeners();
        initFragment();
        assistView.setSelected(true);
        setTitle(R.string.message_assist_title);
        showFragment(assistFragment);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.message_main);
    }

    @Override
    public void initView() {
        super.initView();
        helpView = findViewById(R.id.message_tab_helpTextView);
        assistView = findViewById(R.id.message_tab_assistTextView);
        chatView = findViewById(R.id.message_tab_chatTextView);
    }

    @Override
    public void initListeners() {
        helpView.setOnClickListener(this);
        assistView.setOnClickListener(this);
        chatView.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    private void initFragment(){
        fragmentManager = getSupportFragmentManager();
        helpFragment = new MessageHelpFragment();
        assistFragment = new MessageAssistFragment();
        chatFragment = new MessageChatFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message_tab_helpTextView:
                resetTextViewSelected();
                helpView.setSelected(true);
                setTitle(R.string.message_help_title);
                showFragment(helpFragment);
                break;
            case R.id.message_tab_assistTextView:
                resetTextViewSelected();
                assistView.setSelected(true);
                setTitle(R.string.message_assist_title);
                showFragment(assistFragment);
                break;
            case R.id.message_tab_chatTextView:
                resetTextViewSelected();
                chatView.setSelected(true);
                setTitle(R.string.message_chat_title);
                showFragment(chatFragment);
                break;
            default:
        }
    }

    private void resetTextViewSelected(){
        helpView.setSelected(false);
        assistView.setSelected(false);
        chatView.setSelected(false);
    }

    private void showFragment(Fragment fragment){
        if(currentFragment!=fragment){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(currentFragment);
            currentFragment = fragment;
            if(!fragment.isAdded()){
                fragmentTransaction.add(R.id.message_fg_layout,fragment).show(fragment).commit();
            }else {
                fragmentTransaction.show(fragment).commit();
            }
        }
    }

    public static void startMyActivity(Context context, String para1, String para2){
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra("param1",para1);
        intent.putExtra("param2",para2);
        context.startActivity(intent);
    }
}
