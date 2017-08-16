package cn.edu.zstu.sunshine.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity的基类
 * Created by CooLoongWu on 2017-8-16 10:56.
 */

public class BaseActivity extends AppCompatActivity {

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
