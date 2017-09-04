package cn.edu.zstu.sunshine.tools.user;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityUserBinding;
import cn.edu.zstu.sunshine.entity.User;

public class UserActivity extends BaseActivity {

    private ActivityUserBinding binding;
    private UserViewModel viewModel;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        viewModel = new UserViewModel(this, binding);
        binding.setViewModel(viewModel);

        initViews();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        users.clear();
//        users.addAll(viewModel.getUsers());
//        binding.include.recyclerView.getAdapter().notifyDataSetChanged();
//    }

    private void initViews() {
//        users.addAll(viewModel.getUsers());
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.include.recyclerView.setAdapter(
                new BaseAdapter<>(R.layout.item_user, BR.user, viewModel.getUsers()).setOnItemHandler(
                        new BaseAdapter.OnItemHandler() {
                            @Override
                            public void onItemHandler(ViewDataBinding viewDataBinding, final int position) {
                                if (viewModel.getUsers().get(position).getUserId().equals(AppConfig.getDefaultUserId())) {
                                    viewDataBinding.getRoot().findViewById(R.id.view).setBackground(ContextCompat.getDrawable(UserActivity.this, R.drawable.shape_ring_color));
                                }

                                viewDataBinding.getRoot().findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        viewModel.deleteUser(position);
                                    }
                                });
                            }
                        }));
    }
}
