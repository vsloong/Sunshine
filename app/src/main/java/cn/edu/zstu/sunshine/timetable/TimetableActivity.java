package cn.edu.zstu.sunshine.timetable;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableBinding;
import cn.edu.zstu.sunshine.utils.DataUtil;

public class TimetableActivity extends BaseActivity {

    private ActivityTimetableBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable);
        binding.setViewModel(new TimetableViewModel(this, binding));

        initTabLayout();
        initViews();
    }

    private void initTabLayout() {
        int dayOfWeek = DataUtil.getDayOfWeek() - 1;
        int datesOfWeek[] = DataUtil.getDatesOfWeek();
        for (int i = 0; i < 7; i++) {
            TabLayout.Tab tab = binding.tabLayout.newTab().setCustomView(R.layout.item_tab);
            binding.tabLayout.addTab(tab);
            if (tab.getCustomView() != null) {
                TextView text_week = tab.getCustomView().findViewById(R.id.text_week);
                TextView text_day = tab.getCustomView().findViewById(R.id.text_day);

                text_week.setText(DataUtil.week[i]);
                text_day.setText(String.valueOf(datesOfWeek[i]));

                if (dayOfWeek == i) {
                    tab.select();
                }
            }

        }
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initViews() {


    }
}
