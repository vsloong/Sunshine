package cn.edu.zstu.sunshine.tools.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityUserBinding;

public class UserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        UserViewModel viewModel = new UserViewModel(this, binding);
        binding.setViewModel(viewModel);
    }
}
