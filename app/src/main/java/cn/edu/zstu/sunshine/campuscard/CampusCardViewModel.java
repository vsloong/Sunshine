package cn.edu.zstu.sunshine.campuscard;

import android.content.Context;

import cn.edu.zstu.sunshine.databinding.ActivityCampusCardBinding;

/**
 * 校园卡消费记录的VM
 * Created by CooLoongWu on 2017-8-29 11:27.
 */

public class CampusCardViewModel {
    private Context context;
    private ActivityCampusCardBinding binding;

    CampusCardViewModel(Context context, ActivityCampusCardBinding binding) {
        this.context = context;
        this.binding = binding;
    }


}
