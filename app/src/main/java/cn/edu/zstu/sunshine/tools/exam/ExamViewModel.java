package cn.edu.zstu.sunshine.tools.exam;

import android.content.Context;
import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.databinding.ActivityExamBinding;
import cn.edu.zstu.sunshine.entity.Exam;

/**
 * 考试类的ViewModel
 * Created by CooLoongWu on 2017-9-20 15:18.
 */

public class ExamViewModel {
    private Context context;
    private ActivityExamBinding binding;
    private List<Exam> data = new ArrayList<>();

    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    public ExamViewModel(Context context, ActivityExamBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public List<Exam> getData() {
        return data;
    }

    public void setData(List<Exam> exams) {
        data.clear();
        data.addAll(exams);
    }
}
