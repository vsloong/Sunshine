package cn.edu.zstu.sunshine.tools.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityMainBinding;
import cn.edu.zstu.sunshine.tools.campuscard.CampusCardActivity;
import cn.edu.zstu.sunshine.tools.timetable.TimetableActivity;

/**
 * Created by CooLoongWu on 2017-8-30 16:35.
 */

public class MainActivityViewModel {
    private Context context;
    private ActivityMainBinding binding;

    MainActivityViewModel(Context context, ActivityMainBinding binding) {
        this.context = context;
        this.binding = binding;

        binding.textTest.setText("默认的用户ID：" + AppConfig.getDefaultStudentId());
    }

    public void onTimeTableClick(View view) {
        context.startActivity(new Intent(context, TimetableActivity.class));
    }

    public void onCampusCardClick(View view) {
        context.startActivity(new Intent(context, CampusCardActivity.class));
    }
}
