package cn.edu.zstu.sunshine.timetable;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableBinding;
import cn.edu.zstu.sunshine.databinding.ItemTabBinding;
import cn.edu.zstu.sunshine.entity.User;
import cn.edu.zstu.sunshine.greendao.UserDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.DataUtil;

public class TimetableActivity extends BaseActivity {

    private ActivityTimetableBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable);
        binding.setViewModel(new TimetableViewModel(this, binding));

        initTabLayout();
        initViews();
    }

    private void initTabLayout() {
        int dayOfWeek = DataUtil.getDayOfWeek() - 1;
        int datesOfWeek[] = DataUtil.getDatesOfWeek();

        for (int i = 0; i < 7; i++) {
            ItemTabBinding itemTabBinding = ItemTabBinding.inflate(LayoutInflater.from(this), binding.tabLayout, false);
            itemTabBinding.setItemWeek(DataUtil.week[i]);
            itemTabBinding.setItemDate(String.valueOf(datesOfWeek[i]));

            //暂不清楚为什么布局宽度会出现问题，这里需要重新设置下布局参数（100只是随便写的一个值，后需修改）
            itemTabBinding.getRoot().setLayoutParams(new LinearLayout.LayoutParams(
                    100,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TabLayout.Tab tab = binding.tabLayout.newTab().setCustomView(itemTabBinding.getRoot());
            binding.tabLayout.addTab(tab);
            if (dayOfWeek == i) {
                tab.select();
            }
        }
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initViews() {
        //new DialogUtil(this, R.layout.dialog_base).show();

        UserDao userDao = DaoUtil.getInstance().getSession().getUserDao();
        User user = userDao.queryBuilder().where(UserDao.Properties.StudentId.eq("2012329700031")).build().unique();
        if (user == null) {
            Logger.e("没有该用户");
//            userDao.insert(new User("2012329700030", "是我啊"));
        } else {
            Logger.e("已经有该用户了");
        }

        Logger.e("用户数量" + userDao.queryBuilder().count());
    }
}
