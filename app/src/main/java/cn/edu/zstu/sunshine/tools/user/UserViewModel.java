package cn.edu.zstu.sunshine.tools.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityUserBinding;
import cn.edu.zstu.sunshine.entity.User;
import cn.edu.zstu.sunshine.greendao.UserDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;

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

        List<User> users = userDao.queryBuilder().build().list();

        for (User user : users) {
            if (user.getStudentId().equals(AppConfig.getDefaultStudentId())) {
                userId.set(user.getStudentId());
                userName.set(user.getStudentName());
            }

            Logger.e("用户:" + user.getStudentId() + "；姓名：" + user.getStudentName());
        }

    }

    public void onBtnBackClick(View view) {
        ((UserActivity) context).finish();
    }

    public void onBtnAddUserClick(View view) {
        UserDao userDao = DaoUtil.getInstance().getSession().getUserDao();

        //如果用户列表为空，那么说明这是用户第一次添加用户，把该用户的ID设为默认ID
        if (userDao.queryBuilder().build().list().size() > 2) {
            ToastUtil.showShortToast(R.string.toast_user_full);
        } else {
            startNewActivity(AddUserActivity.class);
        }

    }

    private void startNewActivity(Class cla) {
        Intent intent = new Intent(context, cla);
        intent.putExtra(AddUserViewModel.INTENT_ADD_USER_TYPE, true);
        context.startActivity(intent);
    }
}
