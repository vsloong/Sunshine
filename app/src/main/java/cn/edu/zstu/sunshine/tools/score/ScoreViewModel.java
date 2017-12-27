package cn.edu.zstu.sunshine.tools.score;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseViewModel;
import cn.edu.zstu.sunshine.databinding.ActivityScoreBinding;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.entity.Score;
import cn.edu.zstu.sunshine.greendao.ScoreDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 成绩的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:08.
 */

public class ScoreViewModel extends BaseViewModel<Score> {

    public ScoreViewModel(Context context, ActivityScoreBinding binding) {
        super(context, binding);
    }

    @Override
    protected JsonParse<List<Score>> parseStrToJson(String data) {
        return JSON.parseObject(
                data,
                new TypeReference<JsonParse<List<Score>>>() {
                }
        );
    }

    @Override
    protected String loadUrl() {
        return Api.URL_SCORE;
    }

    protected List<Score> loadDataFromLocal() {
        return DaoUtil.getInstance().getSession().getScoreDao()
                .queryBuilder()
                .where(
                        ScoreDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                )
                .list();
    }

    protected void loadDataIntoView() {
        ActivityScoreBinding scoreBinding = (ActivityScoreBinding) binding;
        if (scoreBinding.include.recyclerView.getAdapter() != null) {
            scoreBinding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
