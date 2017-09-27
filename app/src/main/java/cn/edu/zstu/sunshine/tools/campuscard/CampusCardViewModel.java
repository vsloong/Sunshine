package cn.edu.zstu.sunshine.tools.campuscard;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityCampusCardBinding;
import cn.edu.zstu.sunshine.entity.CampusCard;
import cn.edu.zstu.sunshine.greendao.CampusCardDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.DataUtil;

/**
 * 校园卡消费记录的VM
 * Created by CooLoongWu on 2017-8-29 11:27.
 */

public class CampusCardViewModel {
    //属性必须为public
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    public ObservableField<String> expenses = new ObservableField<>();  //本月累计消费金额
    public ObservableField<String> balance = new ObservableField<>();   //余额

    private Context context;
    private ActivityCampusCardBinding binding;
    private List<CampusCard> data = new ArrayList<>();

    private CampusCardDao campusCardDao;

    CampusCardViewModel(Context context, ActivityCampusCardBinding binding) {
        this.context = context;
        this.binding = binding;

        campusCardDao = DaoUtil.getInstance().getSession().getCampusCardDao();

        expenses.set("235.34");
        balance.set("45");
        loadDataFromLocal();

    }

    private void loadDataFromLocal() {


        List<CampusCard> cards = campusCardDao.queryBuilder()
                .where(
                        CampusCardDao.Properties.UserId.eq(AppConfig.getDefaultUserId()),
                        CampusCardDao.Properties.Year.eq(DataUtil.getCurrentYear()),
                        CampusCardDao.Properties.Month.eq(DataUtil.getCurrentMonth())
                )
                .build().list();

        for (CampusCard card :
                cards) {
            Logger.e("UID" + card.getUserId());
            Logger.e("YEAR" + card.getYear());
            Logger.e("MONTH" + card.getMonth());
        }
        initData(cards);
    }

    void initData(List<CampusCard> info) {
        data.clear();
        data.addAll(info);
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.include.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_campus_card, BR.campusCard, data));

        showEmptyView.set(binding.include.recyclerView.getAdapter().getItemCount() <= 0);
    }

    /**
     * 存储饭卡数据
     *
     * @param data 饭卡消费列表数据
     */
    void insert(final List<CampusCard> data) {
        campusCardDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (CampusCard card : data) {
                    insert(card);
                }
            }
        });
    }

    /**
     * 判断是否存储了相同的数据
     *
     * @param card 饭卡数据
     */
    private void insert(CampusCard card) {
        CampusCard campusCard = campusCardDao.queryBuilder().where(
                CampusCardDao.Properties.UserId.eq(AppConfig.getDefaultUserId()),
                CampusCardDao.Properties.Time.eq(card.getTime())
        ).unique();
        if (campusCard == null) {
            card.complete();
            campusCardDao.insert(card);
            Logger.e("插入新的饭卡消费数据");
        } else {
            Logger.e("饭卡消费数据已存在");
        }
    }

    public void onBtnBackClick(View view) {
        ((CampusCardActivity) context).finish();
    }
}
