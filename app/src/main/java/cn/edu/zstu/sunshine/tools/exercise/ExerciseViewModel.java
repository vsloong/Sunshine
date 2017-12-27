package cn.edu.zstu.sunshine.tools.exercise;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseViewModel;
import cn.edu.zstu.sunshine.databinding.ActivityExerciseBinding;
import cn.edu.zstu.sunshine.entity.Exercise;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.greendao.ExerciseDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 锻炼的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:18.
 */

public class ExerciseViewModel extends BaseViewModel<Exercise> {

    ExerciseViewModel(Context context, ViewDataBinding binding) {
        super(context, binding);
    }

    @Override
    protected JsonParse<List<Exercise>> parseStrToJson(String data) {
        return JSON.parseObject(
                data,
                new TypeReference<JsonParse<List<Exercise>>>() {
                }
        );
    }

    @Override
    protected String loadUrl() {
        return Api.URL_EXERCISE;
    }

    @Override
    protected List<Exercise> loadDataFromLocal() {
        return DaoUtil.getInstance().getSession().getExerciseDao()
                .queryBuilder()
                .where(
                        ExerciseDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                )
                .list();
    }

    @Override
    protected void loadDataIntoView() {
        ActivityExerciseBinding exerciseBinding = (ActivityExerciseBinding) binding;
        if (exerciseBinding.includeRecycler.recyclerView.getAdapter() != null) {
            exerciseBinding.includeRecycler.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
