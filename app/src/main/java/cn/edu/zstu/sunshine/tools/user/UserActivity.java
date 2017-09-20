package cn.edu.zstu.sunshine.tools.user;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityUserBinding;

public class UserActivity extends BaseActivity {

    private ActivityUserBinding binding;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        viewModel = new UserViewModel(this, binding);
        binding.setViewModel(viewModel);

        initToolBar();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initToolBar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_user);
        binding.includeTitle.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initViews() {
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.include.recyclerView.setAdapter(
                new BaseAdapter<>(R.layout.item_user, BR.user, viewModel.getUsers()).setOnItemHandler(
                        new BaseAdapter.OnItemHandler() {
                            @Override
                            public void onItemHandler(ViewDataBinding viewDataBinding, final int position) {
                                int color = viewModel.getUsers().get(position).getUserId().equals(AppConfig.getDefaultUserId()) ?
                                        R.drawable.shape_ring_color :
                                        R.drawable.shape_ring;
                                viewDataBinding.getRoot().findViewById(R.id.view).setBackground(
                                        ContextCompat.getDrawable(UserActivity.this, color));


                                viewDataBinding.getRoot().findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        viewModel.deleteUser(position);
                                    }
                                });

                                viewDataBinding.getRoot().findViewById(R.id.btn_switch).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        viewModel.switchUser(position);
                                    }
                                });
                            }
                        }));
    }

}