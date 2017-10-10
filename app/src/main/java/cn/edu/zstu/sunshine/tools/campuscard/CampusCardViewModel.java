package cn.edu.zstu.sunshine.tools.campuscard;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityCampusCardBinding;
import cn.edu.zstu.sunshine.databinding.DialogMonthBinding;
import cn.edu.zstu.sunshine.databinding.ItemMonthBinding;
import cn.edu.zstu.sunshine.entity.CampusCard;
import cn.edu.zstu.sunshine.greendao.CampusCardDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.DataUtil;
import cn.edu.zstu.sunshine.utils.DialogUtil;

/**
 * 校园卡消费记录的VM
 * Created by CooLoongWu on 2017-8-29 11:27.
 */

public class CampusCardViewModel {
    //属性必须为public
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    public ObservableField<String> expenses = new ObservableField<>();  //本月累计消费金额
    public ObservableField<String> balance = new ObservableField<>();   //余额
    public ObservableField<Integer> month = new ObservableField<>();   //月份
    public ObservableField<String> monthInfo = new ObservableField<>();   //月份信息

    private Context context;
    private ActivityCampusCardBinding binding;
    private List<CampusCard> data = new ArrayList<>();

    private CampusCardDao campusCardDao;

    CampusCardViewModel(Context context, ActivityCampusCardBinding binding) {
        this.context = context;
        this.binding = binding;

        month.set(0);
        monthInfo.set("本月消费");
        campusCardDao = DaoUtil.getInstance().getSession().getCampusCardDao();

        loadDataFromLocal();
    }

    /**
     * 加载本地当月消费数据【按照日期降序排列】
     */
    void loadDataFromLocal() {
        List<CampusCard> cards = campusCardDao.queryBuilder()
                .where(
                        CampusCardDao.Properties.UserId.eq(AppConfig.getDefaultUserId()),
                        CampusCardDao.Properties.Year.eq(DataUtil.getCurrentYear()),
                        CampusCardDao.Properties.Month.eq(DataUtil.getCurrentMonth())
                )
                .orderDesc(CampusCardDao.Properties.Time)
                .build().list();
        initData(cards);
    }

    private void initData(List<CampusCard> info) {
        data.clear();
        data.addAll(info);
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.include.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_campus_card, BR.campusCard, data));

        showEmptyView.set(binding.include.recyclerView.getAdapter().getItemCount() <= 0);

        double temp = 0;
        for (CampusCard ca : info) {
            temp += ca.getConsumption();
        }
        expenses.set(String.valueOf(temp));
        balance.set("45");
    }

    public void onBtnSelectMonthClick(View view) {
        final List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            months.add(String.valueOf(i + 1));
        }

        new DialogUtil(context)
                .setLayout(R.layout.dialog_month)
                .setTitle("请选择相应的月份")
                .onSetViewListener(new DialogUtil.IonSetViewListener() {
                    @Override
                    public void setView(final ViewDataBinding binding) {
                        Logger.e("执行了");
                        ((DialogMonthBinding) binding).include.recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                        ((DialogMonthBinding) binding).include.recyclerView.setAdapter(
                                new BaseAdapter<>(R.layout.item_month, BR.month, months)
                                        .setOnItemHandler(new BaseAdapter.OnItemHandler() {
                                            @Override
                                            public void onItemHandler(final ViewDataBinding viewDataBinding, final int position) {
                                                ((ItemMonthBinding) viewDataBinding).layoutItem.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
//                                                        if (month != 0) {
//                                                            ((DialogMonthBinding) binding).include.recyclerView.getAdapter().notifyItemChanged(month - 1);
//                                                        }
                                                        Logger.e("点击了" + (position + 1) + "月");
                                                        ((ItemMonthBinding) viewDataBinding).layoutItem.setBackgroundResource(R.drawable.shape_round_selected);
                                                        month.set(position + 1);
                                                    }
                                                });
                                            }
                                        })
                        );
                    }
                })
                .onConfirmClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        monthInfo.set(month.get() + "月消费");
                    }
                })
                .build()
                .show();
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
                //通知页面数据刷新
                EventBus.getDefault().post(new CampusCard());
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
