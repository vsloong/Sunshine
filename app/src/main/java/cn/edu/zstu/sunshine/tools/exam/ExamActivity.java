package cn.edu.zstu.sunshine.tools.exam;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityExamBinding;

public class ExamActivity extends BaseActivity {

    private ActivityExamBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam);
        binding.setViewModel(new ExamViewModel(this, binding));

        initToolbar();
    }

    private void initToolbar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_exam);
        binding.includeTitle.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
