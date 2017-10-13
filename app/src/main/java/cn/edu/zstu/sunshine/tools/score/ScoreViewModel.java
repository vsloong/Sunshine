package cn.edu.zstu.sunshine.tools.score;

import android.content.Context;
import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.databinding.ActivityScoreBinding;
import cn.edu.zstu.sunshine.entity.Score;

/**
 * 成绩的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:08.
 */

public class ScoreViewModel {
    private Context context;
    private ActivityScoreBinding binding;

    private List<Score> data = new ArrayList<>();
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    public ScoreViewModel(Context context, ActivityScoreBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public List<Score> getData() {
        return data;
    }

    public void setData(List<Score> scores) {
        data.clear();
        data.addAll(scores);
    }
}
