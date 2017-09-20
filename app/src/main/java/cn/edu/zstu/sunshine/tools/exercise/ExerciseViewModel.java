package cn.edu.zstu.sunshine.tools.exercise;

import android.content.Context;

import cn.edu.zstu.sunshine.databinding.ActivityExerciseBinding;

/**
 * 锻炼的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:18.
 */

public class ExerciseViewModel {

    private Context context;
    private ActivityExerciseBinding binding;

    public ExerciseViewModel(Context context, ActivityExerciseBinding binding) {
        this.context = context;
        this.binding = binding;
    }
}
