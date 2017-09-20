package cn.edu.zstu.sunshine.tools.exam;

import android.content.Context;

import cn.edu.zstu.sunshine.databinding.ActivityExamBinding;

/**
 * 考试类的ViewModel
 * Created by CooLoongWu on 2017-9-20 15:18.
 */

public class ExamViewModel {
    private Context context;
    private ActivityExamBinding binding;

    public ExamViewModel(Context context, ActivityExamBinding binding) {
        this.context = context;
        this.binding = binding;
    }
}
