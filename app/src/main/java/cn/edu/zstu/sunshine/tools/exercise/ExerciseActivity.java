package cn.edu.zstu.sunshine.tools.exercise;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityExerciseBinding;

public class ExerciseActivity extends BaseActivity {

    private ActivityExerciseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);
        binding.setViewModel(new ExerciseViewModel(this, binding));

        initToolbar();
    }

    private void initToolbar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_exercise);
        binding.includeTitle.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
