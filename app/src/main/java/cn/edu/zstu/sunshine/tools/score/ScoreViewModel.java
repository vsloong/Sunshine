package cn.edu.zstu.sunshine.tools.score;

import android.content.Context;
import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityScoreBinding;
import cn.edu.zstu.sunshine.entity.Score;
import cn.edu.zstu.sunshine.greendao.ScoreDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 成绩的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:08.
 */

public class ScoreViewModel {
    private Context context;
    private ActivityScoreBinding binding;

    private List<Score> data = new ArrayList<>();
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    private ScoreDao scoreDao;

    public ScoreViewModel(Context context, ActivityScoreBinding binding) {
        this.context = context;
        this.binding = binding;

        scoreDao = DaoUtil.getInstance().getSession().getScoreDao();
        init();
    }

    public List<Score> getData() {
        return data;
    }

    public void setData(List<Score> scores) {
        data.clear();
        data.addAll(scores);
    }

    void init() {
        loadDataFromLocal();
        loadDataIntoView();
    }

    private void loadDataFromLocal() {
        List<Score> scores = scoreDao
                .queryBuilder()
                .where(
                        ScoreDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                )
                .list();

        setData(scores);
    }

    private void loadDataIntoView() {
        showEmptyView.set(data.size() <= 0);

        if (binding.include.recyclerView.getAdapter() != null) {
            binding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
