package cn.edu.zstu.sunshine.tools.score;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityScoreBinding;

public class ScoreActivity extends BaseActivity {
    private ActivityScoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_score);
        binding.setViewModel(new ScoreViewModel(this, binding));
        initToolbar();
    }

    private void initToolbar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_score);
        binding.includeTitle.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
