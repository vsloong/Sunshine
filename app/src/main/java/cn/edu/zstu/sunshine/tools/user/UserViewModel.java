package cn.edu.zstu.sunshine.tools.user;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityUserBinding;
import cn.edu.zstu.sunshine.entity.User;
import cn.edu.zstu.sunshine.greendao.UserDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 用户中心的ViewModel
 * Created by CooLoongWu on 2017-8-31 17:06.
 */

public class UserViewModel {

    public ObservableField<String> userId = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();

    private Context context;
    private ActivityUserBinding binding;

    UserViewModel(Context context, ActivityUserBinding binding) {
        this.context = context;
        this.binding = binding;


        UserDao userDao = DaoUtil.getInstance().getSession().getUserDao();
        User user = userDao.queryBuilder().where(UserDao.Properties.StudentId.eq(AppConfig.getDefaultStudentId())).build().unique();
        userId.set(user.getStudentId());
        userName.set(user.getStudentName());

    }

    public void onBtnBackClick(View view) {
        ((UserActivity) context).finish();
    }
}
