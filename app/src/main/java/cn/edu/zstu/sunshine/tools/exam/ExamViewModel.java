package cn.edu.zstu.sunshine.tools.exam;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseViewModel;
import cn.edu.zstu.sunshine.databinding.ActivityExamBinding;
import cn.edu.zstu.sunshine.entity.Exam;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.greendao.ExamDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 考试类的ViewModel
 * Created by CooLoongWu on 2017-9-20 15:18.
 */

public class ExamViewModel extends BaseViewModel<Exam> {

    ExamViewModel(Context context, ActivityExamBinding binding) {
        super(context, binding);
    }

    @Override
    protected JsonParse<List<Exam>> parseStrToJson(String data) {
        return JSON.parseObject(data,
                new TypeReference<JsonParse<List<Exam>>>() {
                });
    }

    @Override
    protected String loadUrl() {
        return Api.URL_EXAM;
    }

    protected List<Exam> loadDataFromLocal() {
        return DaoUtil.getInstance().getSession().getExamDao()
                .queryBuilder()
                .where(
                        ExamDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                )
                .list();
    }

    protected void loadDataIntoView() {
        ActivityExamBinding examBinding = (ActivityExamBinding) binding;
        if (examBinding.include.recyclerView.getAdapter() != null) {
            examBinding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
