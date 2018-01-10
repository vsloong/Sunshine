package cn.edu.zstu.sunshine.tools;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.io.OutputStream;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;

public class TestActivity extends BaseActivity {

    OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String defaultStr = "Hello You !";
        String str = new String(Base64.encode(defaultStr.getBytes(), Base64.DEFAULT));

        Log.e("编码：", str);
        str = new String(Base64.decode(str, Base64.DEFAULT));
        Log.e("解码：", str);
    }
}
