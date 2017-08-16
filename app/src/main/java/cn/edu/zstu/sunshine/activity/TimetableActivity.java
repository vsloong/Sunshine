package cn.edu.zstu.sunshine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.utils.DataUtil;

public class TimetableActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.layout_tab);

        int dates[] = DataUtil.getDates();
        int today = DataUtil.getDayOfWeek();

        for (int i = 0; i < 7; i++) {
            TabLayout.Tab tab = tabLayout.newTab().setCustomView(R.layout.item_tab);
            tabLayout.addTab(tab);
            TextView week = tab.getCustomView().findViewById(R.id.text_week);
            TextView day = tab.getCustomView().findViewById(R.id.text_day);

            week.setText(DataUtil.week[i]);
            day.setText(String.valueOf(dates[i]));

            if ((today - 1) == i) {
                tab.select();
            }
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }
}
