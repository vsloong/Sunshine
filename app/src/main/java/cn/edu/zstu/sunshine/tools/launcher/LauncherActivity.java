package cn.edu.zstu.sunshine.tools.launcher;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.io.File;

import cn.edu.zstu.skin.SkinManager;
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

        //TODO 测试测试
        SkinManager.getInstance().declare(this);

        final String FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + "Sunshine";
        final String SKIN_PKG = "cn.edu.zstu.sunshineskin";

        SkinManager.getInstance().setSkinConfig(
                FILE_PATH + File.separator + "sunshine_christmas_1711291037.skin",
                SKIN_PKG,
                "2017-12-05",
                "2017-12-12"

        );
        ImageView img = findViewById(R.id.img_launcher);
        Drawable mipmap = SkinManager.getInstance().getResources().getMipmapByName("banner");
        if (mipmap != null) {
            img.setImageDrawable(mipmap);
        } else {
            Logger.e("获取资源失败");
        }
        //TODO 测试结束


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
