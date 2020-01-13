package com.zk.zhukun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hik.zhukun.R;
import com.zk.common.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.Companion.getInstance().d("123456");
        LogUtil.Companion.getInstance().setLogSwitch(true);
    }
}
