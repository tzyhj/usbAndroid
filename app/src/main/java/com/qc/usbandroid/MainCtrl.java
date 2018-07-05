package com.qc.usbandroid;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;


import com.qc.usbandroid.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainCtrl {

    private static final String TAG = MainCtrl.class.getSimpleName();
    private Activity act;
    private ActivityMainBinding binding;

    public List<String> mList;
    public final MainAdapter mAdapter;


    public MainCtrl(Activity act, ActivityMainBinding binding) {
        this.act = act;
        this.binding = binding;

        mList = new ArrayList<>();
        mAdapter = new MainAdapter(act,mList);

        mList.add("123");
        initView();
        mList.add("456");
        mAdapter.notifyDataSetChanged();

    }

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(act, OrientationHelper.VERTICAL,false);
        binding.rvMsg.addItemDecoration(new SpaceItemDecoration(act,5));
        binding.rvMsg.setLayoutManager(layoutManager);
        binding.rvMsg.setAdapter(mAdapter);
    }


    /**
     *获取数据
     */
    public void onClickRead(View view){
        mList.add("ret"+ String.valueOf("onClickRead"));
        mAdapter.notifyDataSetChanged();
//        act.readFromUsb();
    }

    /**
     *发送数据
     */
    public void onClickWrite(View view){
        mList.add("ret"+ String.valueOf("onClickWrite"));
        mAdapter.notifyDataSetChanged();
//        act.sendToUsb("1234567890");

    }








}
