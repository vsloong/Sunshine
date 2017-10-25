package cn.edu.zstu.sunshine.tools.timetable;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableAddBinding;

public class TimetableAddActivity extends BaseActivity {

    private ActivityTimetableAddBinding binding;
    private TimetableAddViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable_add);
        viewModel = new TimetableAddViewModel(this, binding);
        binding.setViewModel(viewModel);
    }
}
