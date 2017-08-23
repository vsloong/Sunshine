package cn.edu.zstu.sunshine.timetable;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableBinding;
import cn.edu.zstu.sunshine.entity.Course;
import cn.edu.zstu.sunshine.utils.DataUtil;

/**
 * 课表的ViewModel类
 * Created by CooLoongWu on 2017-8-21 14:59.
 */

public class TimetableViewModel {
    //属性必须为public
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    private Context context;
    private ActivityTimetableBinding binding;
    private List<Course> data = new ArrayList<>();

    TimetableViewModel(Context context, ActivityTimetableBinding binding) {
        this.context = context;
        this.binding = binding;

        initData();
    }

    private void initData() {
        data.add(new Course("计算机原理与应用", "CooLoongWu", "2S-503", "07:00-09:00"));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_course, BR.course, data));

        showEmptyView.set(binding.recyclerView.getAdapter().getItemCount() <= 0);
    }

    /**
     * 返回当日数据
     *
     * @param view 按钮
     */
    public void onBtnTodayClickListener(View view) {
        TabLayout.Tab tab = binding.tabLayout.getTabAt(DataUtil.getDayOfWeek() - 1);
        if (tab != null) {
            tab.select();
        }

        Logger.e("点击了按钮");

        data.add(new Course("生物科学制药", "伍德", "2N-324", "10:00-12:00"));
        data.add(new Course("操作系统原理", "詹妮", "2N-324", "15:00-17:00"));
        binding.recyclerView.getAdapter().notifyDataSetChanged();
        showEmptyView.set(binding.recyclerView.getAdapter().getItemCount() <= 0);
    }
}
