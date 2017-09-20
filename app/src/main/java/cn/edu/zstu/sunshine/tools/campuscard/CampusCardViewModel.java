package cn.edu.zstu.sunshine.tools.campuscard;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityCampusCardBinding;
import cn.edu.zstu.sunshine.entity.CampusCard;

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

    CampusCardViewModel(Context context, ActivityCampusCardBinding binding) {
        this.context = context;
        this.binding = binding;

        expenses.set("235.34");
        balance.set("45");
        initData();

    }

    private void initData() {
        data.add(new CampusCard("2017-08-29 15:23:45", "紫薇阁餐费支出", "zwg24", 12.34, "餐费支出"));
        data.add(new CampusCard("2017-08-29 15:23:45", "桂花园餐费支出", "zwg24", 8, "餐费支出"));
        data.add(new CampusCard("2017-08-29 15:23:45", "玫瑰园餐费支出", "zwg24", 17, "餐费支出"));

        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.include.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_campus_card, BR.campusCard, data));

        showEmptyView.set(binding.include.recyclerView.getAdapter().getItemCount() <= 0);
    }

    public void onBtnBackClick(View view) {
        ((CampusCardActivity) context).finish();
    }
}
