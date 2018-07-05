package com.qc.usbandroid;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qc.usbandroid.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ActivityMainBinding binding;
    private MainCtrl ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        ctrl = new MainCtrl(this, binding);
        binding.setViewCtrl(ctrl);


    }
}
