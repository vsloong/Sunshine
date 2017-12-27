package cn.edu.zstu.sunshine.tools.campuscard;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.base.BaseViewModel;
import cn.edu.zstu.sunshine.databinding.ActivityCampusCardBinding;
import cn.edu.zstu.sunshine.databinding.DialogMonthBinding;
import cn.edu.zstu.sunshine.databinding.ItemMonthBinding;
import cn.edu.zstu.sunshine.entity.CampusCard;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.greendao.CampusCardDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.DateUtil;
import cn.edu.zstu.sunshine.utils.DialogUtil;

/**
 * 校园卡消费记录的VM
 * Created by CooLoongWu on 2017-8-29 11:27.
 */

public class CampusCardViewModel extends BaseViewModel<CampusCard> {

    public ObservableField<String> expenses = new ObservableField<>();  //本月累计消费金额
    public ObservableField<String> balance = new ObservableField<>();   //余额
    public ObservableField<Integer> month = new ObservableField<>();   //月份

    protected CampusCardViewModel(Context context, ViewDataBinding binding) {
        super(context, binding);
    }

    @Override
    protected JsonParse<List<CampusCard>> parseStrToJson(String data) {
        return JSON.parseObject(
                data,
                new TypeReference<JsonParse<List<CampusCard>>>() {
                }
        );
    }

    @Override
    public void init() {
        //TODO 为什么一直为空指针异常
        month.set(DateUtil.getCurrentMonth());
        super.init();
    }

    @Override
    protected String loadUrl() {
        return Api.URL_CAMPUSCARD;
    }

    /**
     * 加载本地当月消费数据【按照日期降序排列】
     */
    protected List<CampusCard> loadDataFromLocal() {
        return DaoUtil.getInstance().getSession().getCampusCardDao()
                .queryBuilder()
                .where(
                        CampusCardDao.Properties.UserId.eq(AppConfig.getDefaultUserId()),
                        CampusCardDao.Properties.Year.eq(DateUtil.getCurrentYear()),
                        CampusCardDao.Properties.Month.eq(month.get())
                )
                .orderDesc(CampusCardDao.Properties.Time)
                .build()
                .list();
    }

    /**
     * 加载数据到视图
     */
    protected void loadDataIntoView() {
        showEmptyView.set(data.size() <= 0);
        ActivityCampusCardBinding campusCardBinding = (ActivityCampusCardBinding) binding;
        if (campusCardBinding.include.recyclerView.getAdapter() != null) {
            campusCardBinding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }

        BigDecimal bigDecimal = new BigDecimal("0");
        for (CampusCard ca : data) {
            bigDecimal = new BigDecimal(
                    bigDecimal.add(new BigDecimal(Double.toString(ca.getConsumption()))).toString()
            );
        }
        expenses.set(bigDecimal.toString());
        balance.set("45");
    }

    public void onBtnSelectMonthClick(View view) {
        final List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            months.add(String.valueOf(i + 1));
        }

        new DialogUtil(context)
                .setLayout(R.layout.dialog_month)
                .setTitle("请选择查询的月份")
                .onSetViewListener(new DialogUtil.IonSetViewListener() {
                    @Override
                    public void setView(ViewDataBinding binding, final AlertDialog dialog) {
                        Logger.e("执行了");
                        ((DialogMonthBinding) binding).include.recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                        ((DialogMonthBinding) binding).include.recyclerView.setVerticalScrollBarEnabled(false);
                        ((DialogMonthBinding) binding).include.recyclerView.setAdapter(
                                new BaseAdapter<>(R.layout.item_month, BR.month, months)
                                        .setOnItemHandler(new BaseAdapter.OnItemHandler() {
                                            @Override
                                            public void onItemHandler(final ViewDataBinding viewDataBinding, final int position) {
                                                ((ItemMonthBinding) viewDataBinding).layoutItem.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
//
                                                        Logger.e("点击了" + (position + 1) + "月");

                                                        month.set(position + 1);

                                                        init();

                                                        dialog.dismiss();
                                                    }
                                                });
                                            }
                                        })
                        );
                    }
                })
                .build()
                .show();
    }
}
