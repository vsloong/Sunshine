package cn.edu.zstu.sunshine.tools.timetable;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableBinding;
import cn.edu.zstu.sunshine.databinding.ItemTabBinding;
import cn.edu.zstu.sunshine.utils.DataUtil;

public class TimetableActivity extends BaseActivity {

    private ActivityTimetableBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable);
        binding.setViewModel(new TimetableViewModel(this, binding));

        initTabLayout();
    }

    private void initTabLayout() {
        int dayOfWeek = DataUtil.getDayOfWeek() - 1;
        int datesOfWeek[] = DataUtil.getDatesOfWeek();

        for (int i = 0; i < 7; i++) {
            ItemTabBinding itemTabBinding = ItemTabBinding.inflate(LayoutInflater.from(this), binding.tabLayout, false);
            itemTabBinding.setItemWeek(DataUtil.week[i]);
            itemTabBinding.setItemDate(String.valueOf(datesOfWeek[i]));

            //暂不清楚为什么布局宽度会出现问题，这里需要重新设置下布局参数（100只是随便写的一个值，后需修改）
            itemTabBinding.getRoot().setLayoutParams(new LinearLayout.LayoutParams(
                    100,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TabLayout.Tab tab = binding.tabLayout.newTab().setCustomView(itemTabBinding.getRoot());
            binding.tabLayout.addTab(tab);
            if (dayOfWeek == i) {
                tab.select();
            }
        }
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
