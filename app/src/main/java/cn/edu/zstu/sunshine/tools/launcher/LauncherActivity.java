package cn.edu.zstu.sunshine.tools.launcher;

import android.os.Bundle;
import android.os.Handler;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.greendao.UserDao;
import cn.edu.zstu.sunshine.tools.main.MainActivity;
import cn.edu.zstu.sunshine.tools.user.UserAddActivity;
import cn.edu.zstu.sunshine.utils.DaoUtil;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //一行代码实现换肤
//        SkinManager.getInstance().declare(this);

        //配置换肤资源信息
//        final String FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + "Sunshine";
//        SkinManager.getInstance().setSkinConfig(
//                FILE_PATH + File.separator + "sunshine_christmas_1711291037.skin",
//                "2017-12-05 00:00:00",
//                "2017-12-12 23:59:59"
//        );

        UserDao userDao = DaoUtil.getInstance().getSession().getUserDao();
        if (userDao.queryBuilder().build().list().isEmpty()) {
            goNext(UserAddActivity.class);
        } else {
            goNext(MainActivity.class);
        }
    }


    private void goNext(final Class cla) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(cla, true);
            }
        }, 1000);
    }
}
