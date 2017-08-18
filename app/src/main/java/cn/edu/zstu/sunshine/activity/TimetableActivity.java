package cn.edu.zstu.sunshine.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableBinding;
import cn.edu.zstu.sunshine.entity.Course;
import cn.edu.zstu.sunshine.utils.DataUtil;

public class TimetableActivity extends BaseActivity {

    private ActivityTimetableBinding binding;

    private List<Course> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable);

        initTabLayout();
        initViews();
    }

    private void initTabLayout() {
        for (int i = 0; i < 7; i++) {
            TabLayout.Tab tab = binding.tabLayout.newTab().setCustomView(R.layout.item_tab);
            binding.tabLayout.addTab(tab);
            if (tab.getCustomView() != null) {
                TextView text_week = tab.getCustomView().findViewById(R.id.text_week);
                TextView text_day = tab.getCustomView().findViewById(R.id.text_day);

                text_week.setText(DataUtil.week[i]);
                text_day.setText(String.valueOf(DataUtil.getDates()[i]));

                if ((DataUtil.getDayOfWeek() - 1) == i) {
                    tab.select();
                }
            }

        }
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initViews() {
        binding.btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabLayout.Tab tab = binding.tabLayout.getTabAt(DataUtil.getDayOfWeek() - 1);
                if (tab != null) {
                    tab.select();
                }

                data.add(new Course("计算机原理与应用", "武帅龙", "2S-503", "07:00-09:00"));
                data.add(new Course("生物科学制药", "哇哈哈", "2N-324", "10:00-12:00"));
                data.add(new Course("生物科学制药", "哇哈哈", "2S-324", "10:00-12:00"));
                data.add(new Course("生物科学制药", "哇哈哈", "2N-324", "10:00-12:00"));

                binding.recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_course, BR.course, data));
    }


}
