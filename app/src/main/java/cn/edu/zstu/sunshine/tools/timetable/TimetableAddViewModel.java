package cn.edu.zstu.sunshine.tools.timetable;

import android.content.Context;
import android.view.View;

import cn.edu.zstu.sunshine.databinding.ActivityTimetableAddBinding;

/**
 * 添加课表课程的ViewModel
 * Created by CooLoongWu on 2017-10-25 14:08.
 */

public class TimetableAddViewModel {

    private Context context;
    private ActivityTimetableAddBinding binding;

    public TimetableAddViewModel(Context context, ActivityTimetableAddBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    /**
     * 返回上一页
     *
     * @param view 按钮
     */
    public void onBtnBackClick(View view) {
        ((TimetableAddActivity) context).finish();
    }
}
