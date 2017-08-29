package cn.edu.zstu.sunshine.tools.campuscard;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityCampusCardBinding;

public class CampusCardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCampusCardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_campus_card);
        CampusCardViewModel viewModel = new CampusCardViewModel(this, binding);
        binding.setViewModel(viewModel);

    }
}
