package cn.edu.zstu.sunshine.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * Activity的基类
 * Created by CooLoongWu on 2017-8-16 10:56.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        //设置日间，夜间模式
        if (AppConfig.isNightMode()) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }

    public void startActivity(Class cla) {
        startActivity(cla, false);
    }

    public void startActivity(Class cla, boolean isFinish) {
        startActivity(new Intent(this, cla));
        if (isFinish) {
            finish();
        }
    }

}
