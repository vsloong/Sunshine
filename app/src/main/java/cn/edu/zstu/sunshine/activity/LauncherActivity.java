package cn.edu.zstu.sunshine.activity;

import android.os.Bundle;
import android.os.Handler;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(TimetableActivity.class, true);
            }
        }, 1000);
    }
}
