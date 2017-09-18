package cn.edu.zstu.sunshine.tools.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.view.View;

import java.util.List;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityUserBinding;
import cn.edu.zstu.sunshine.entity.User;
import cn.edu.zstu.sunshine.greendao.UserDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.DialogUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;

/**
 * 用户中心的ViewModel
 * Created by CooLoongWu on 2017-8-31 17:06.
 */

public class UserViewModel {

    public ObservableField<String> userId = new ObservableField<>();
    public ObservableField<String> userNickname = new ObservableField<>();

    private Context context;
    private ActivityUserBinding binding;

    private ObservableArrayList<User> users = new ObservableArrayList<>();
    private UserDao userDao;
    private int position = 0;

    UserViewModel(Context context, ActivityUserBinding binding) {
        this.context = context;
        this.binding = binding;

        userDao = DaoUtil.getInstance().getSession().getUserDao();
        users.addAll(userDao.queryBuilder().build().list());
    }

    public void addUser(User user) {
        users.add(user);
        binding.include.recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void deleteUser() {
        userDao.delete(users.get(position));
        users.remove(position);
        binding.include.recyclerView.getAdapter().notifyDataSetChanged();
    }

    void deleteUser(int position) {
        this.position = position;

        if (users.get(position).getUserId().equals(AppConfig.getDefaultUserId())) {
            ToastUtil.showShortToast(R.string.toast_user_delete_err01);
        } else {
            showDeleteConfirmDialog();
        }

    }

    private void switchUser() {
        AppConfig.setDefaultUserId(users.get(position).getUserId());
        getUsers();
        binding.include.recyclerView.getAdapter().notifyDataSetChanged();

        ToastUtil.showShortToast(R.string.toast_user_switch);
    }

    void switchUser(int position) {
        this.position = position;
        if (users.get(position).getUserId().equals(AppConfig.getDefaultUserId())) {
            ToastUtil.showShortToast(R.string.toast_user_switch_err01);
        } else {
            showSwitchConfirmDialog();
        }
    }

    List<User> getUsers() {
        users.clear();
        users.addAll(userDao.queryBuilder().build().list());
        for (User user : users) {
            if (user.getUserId().equals(AppConfig.getDefaultUserId())) {
                userId.set(user.getUserId());
                userNickname.set(user.getUserNickname());
            }
        }
        return users;
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

    private void showDeleteConfirmDialog() {
        new DialogUtil(context)
                .setView(R.layout.dialog_base)
                .setTitle("确定删除该用户吗？")
                .setContent("删除后该用户的资料也会全部删除，请三思~")
                .setButtonText("删掉吧")
                .onConfirmClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteUser();
                    }
                })
                .build()
                .show();
    }

    private void showSwitchConfirmDialog() {
        new DialogUtil(context)
                .setView(R.layout.dialog_base)
                .setTitle("确定切换为该用户吗？")
                .setButtonText("立即切换")
                .onConfirmClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switchUser();
                    }
                })
                .build()
                .show();
    }
}
