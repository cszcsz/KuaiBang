package com.example.kuaibang.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.kuaibang.R;
import com.example.kuaibang.adapter.MessageHelpsRvAdapter;
import com.example.kuaibang.entity.Helper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class HelpsFragment extends Fragment{

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;

    private MessageHelpsRvAdapter adapter;

    private Dialog dialog;
    private TextView dialogSubTitle;
    private Button confirmBtn;
    private Button concelBtn;
    private EditText editText;

    private List<Helper> allData;

    private static final String TAG = "HelpsFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.helps_fg,container,false);

        recyclerView = view.findViewById(R.id.message_helps_recycle_view);
        refreshLayout = view.findViewById(R.id.message_helps_refresh_layout);

        allData = new ArrayList<>();

        List<BmobQuery<Helper>> andQuerys = new ArrayList<BmobQuery<Helper>>();
        BmobQuery<Helper> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("user", BmobUser.getCurrentUser().getObjectId());
        BmobQuery<Helper> query2 = new BmobQuery<>();
        query2.addWhereEqualTo("helpState",1);
        andQuerys.add(query1);
        andQuerys.add(query2);
        BmobQuery<Helper> query = new BmobQuery<>();
        query.and(andQuerys);
        query.include("post,postUser");
        query.order("endTime");
        query.findObjects(new FindListener<Helper>() {
            @Override
            public void done(List<Helper> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        Log.i(TAG, "没查到数据");
                    }
                    allData = list;




                    Log.i(TAG, "查询初始数据成功!"+"list大小为"+String.valueOf(list.size()));

                }else{
                    Toast.makeText(getContext(),"初始化数据失败",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                initializeAdapter();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initializeAdapter(){

        adapter = new MessageHelpsRvAdapter(R.layout.message_helps_rv_item,allData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

        // 为rv中的每个item条目设置点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {

                Toast.makeText(getContext(),"你点击了第"+(position+1)+"条求助帖",Toast.LENGTH_SHORT).show();
            }
        });
        // 为rv中每个item条目下面的子控件设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view,final int position) {
                switch (view.getId()){
                    case R.id.message_helps_rv_item_userImg:
                        Snackbar.make(view,"你点击了第"+(position+1)+"位用户头像",Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.message_helps_rv_item_cancelBtn:
                        Helper helper2 = new Helper();
                        helper2.setHelpState(-1);
                        helper2.update(allData.get(position).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    allData.get(position).setHelpState(-1);
                                    adapter.remove(position);
                                    Toast.makeText(getContext(),"取消该帮助成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    e.printStackTrace();
                                }
                            }
                        });
//                        showDialog();
                        break;
                } 
            }
        });

        // 为recycleView设置下拉加载，上拉刷新功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {

                List<BmobQuery<Helper>> andQuerys = new ArrayList<BmobQuery<Helper>>();
                BmobQuery<Helper> query1 = new BmobQuery<>();
                query1.addWhereEqualTo("user", BmobUser.getCurrentUser().getObjectId());
                BmobQuery<Helper> query2 = new BmobQuery<>();
                query2.addWhereEqualTo("helpState",1);
                andQuerys.add(query1);
                andQuerys.add(query2);
                BmobQuery<Helper> query = new BmobQuery<>();
                query.and(andQuerys);
                query.include("post,postUser");
                query.order("endTime");
                query.findObjects(new FindListener<Helper>() {
                    @Override
                    public void done(List<Helper> list, BmobException e) {
                        if (e==null){
                            Log.i(TAG, "onRefresh:查询数据成功");
                            if (list.size()==adapter.getItemCount()){
                                Toast.makeText(getContext(),"暂无更新的数据",Toast.LENGTH_SHORT).show();
                            }
                            allData = list;
                            adapter.setNewData(list);

                        }else{
                            e.printStackTrace();
                        }
                    }
                });

                refreshLayout.finishRefresh(1000); // 第一个参数表示延时时间，第二个参数若传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishLoadMore(1000);
            }
        });

    }

    public static HelpsFragment newInstance() {
        // 工厂模式,创建实例若想传参则需要利用Bundle
        HelpsFragment fragment = new HelpsFragment();
//        Bundle args = new Bundle();
//        args.putString("data", param);
//        fragment.setArguments(args);
        return fragment;
    }

    private void showDialog(){
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.help_detail_dialog,null);
        dialog.setContentView(dialogView);
        dialog.show();
        dialog.setCancelable(false);
        initDialogView(dialogView);
        initDialogListener();

    }

    private void initDialogView(View view){
        dialogSubTitle = view.findViewById(R.id.help_detail_subTitle);
        dialogSubTitle.setText("请输入取消原因");
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

}
