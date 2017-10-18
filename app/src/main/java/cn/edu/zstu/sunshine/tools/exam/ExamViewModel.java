package cn.edu.zstu.sunshine.tools.exam;

import android.content.Context;
import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityExamBinding;
import cn.edu.zstu.sunshine.entity.Exam;
import cn.edu.zstu.sunshine.greendao.ExamDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 考试类的ViewModel
 * Created by CooLoongWu on 2017-9-20 15:18.
 */

public class ExamViewModel {
    private Context context;
    private ActivityExamBinding binding;
    private List<Exam> data = new ArrayList<>();

    private ExamDao examDao;

    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    public ExamViewModel(Context context, ActivityExamBinding binding) {
        this.context = context;
        this.binding = binding;

        examDao = DaoUtil.getInstance().getSession().getExamDao();
        init();
    }

    public List<Exam> getData() {
        return data;
    }

    public void setData(List<Exam> exams) {
        data.clear();
        data.addAll(exams);
    }

    void init() {
        loadDataFromLocal();
        loadDataIntoView();
    }

    private void loadDataFromLocal() {
        List<Exam> exams = examDao
                .queryBuilder()
                .where(
                        ExamDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                )
                .list();

        setData(exams);
    }

    private void loadDataIntoView() {
        showEmptyView.set(data.size() <= 0);

        if (binding.include.recyclerView.getAdapter() != null) {
            binding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
