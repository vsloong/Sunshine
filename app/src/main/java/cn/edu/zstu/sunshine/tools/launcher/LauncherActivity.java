package cn.edu.zstu.sunshine.tools.launcher;

import android.os.Bundle;
import android.os.Handler;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.greendao.UserDao;
import cn.edu.zstu.sunshine.tools.timetable.TimetableActivity;
import cn.edu.zstu.sunshine.tools.user.AddUserActivity;
import cn.edu.zstu.sunshine.utils.DaoUtil;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        UserDao userDao = DaoUtil.getInstance().getSession().getUserDao();
        if (userDao.queryBuilder().build().list().isEmpty()) {
            goNext(AddUserActivity.class);
        } else {
            goNext(TimetableActivity.class);
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
