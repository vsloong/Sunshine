package cn.edu.zstu.sunshine.tools.score;

import android.content.Context;

import cn.edu.zstu.sunshine.databinding.ActivityScoreBinding;

/**
 * 成绩的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:08.
 */

public class ScoreViewModel {
    private Context context;
    private ActivityScoreBinding binding;

    public ScoreViewModel(Context context, ActivityScoreBinding binding) {
        this.context = context;
        this.binding = binding;
    }
}
