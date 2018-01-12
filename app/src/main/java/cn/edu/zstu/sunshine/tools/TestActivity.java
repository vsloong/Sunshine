package cn.edu.zstu.sunshine.tools;

import android.os.Bundle;

import java.io.OutputStream;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;

public class TestActivity extends BaseActivity {

    OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


    }
}
