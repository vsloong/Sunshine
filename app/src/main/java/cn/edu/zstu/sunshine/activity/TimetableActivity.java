package cn.edu.zstu.sunshine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.entity.Course;
import cn.edu.zstu.sunshine.utils.DataUtil;

public class TimetableActivity extends BaseActivity {

    private TabLayout tabLayout;
    private RecyclerView recycler;
    private List<Course> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        initTabLayout();
        initViews();
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.layout_tab);
        for (int i = 0; i < 7; i++) {
            TabLayout.Tab tab = tabLayout.newTab().setCustomView(R.layout.item_tab);
            tabLayout.addTab(tab);
            TextView text_week = tab.getCustomView().findViewById(R.id.text_week);
            TextView text_day = tab.getCustomView().findViewById(R.id.text_day);

            text_week.setText(DataUtil.week[i]);
            text_day.setText(String.valueOf(DataUtil.getDates()[i]));

            if ((DataUtil.getDayOfWeek() - 1) == i) {
                tab.select();
            }
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initViews() {
        TextView text_month = (TextView) findViewById(R.id.text_month);
        TextView text_week = (TextView) findViewById(R.id.text_week);

        String month = String.format(getResources().getString(R.string.month), DataUtil.getMonth());
        text_month.setText(getColorText(month, R.color.text_yellow, 0, month.length() - 1));
        String week = String.format(getResources().getString(R.string.week), DataUtil.getWeek());
        text_week.setText(getColorText(week, R.color.text_yellow, 2, week.length() - 2));

        Button btn_today = (Button) findViewById(R.id.btn_today);
        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLayout.getTabAt(DataUtil.getDayOfWeek() - 1).select();

                data.add(new Course("计算机原理与应用", "武帅龙", "2S-503", "07:00-09:00"));
                data.add(new Course("生物科学制药", "哇哈哈", "2N-324", "10:00-12:00"));
                data.add(new Course("生物科学制药", "哇哈哈", "2S-324", "10:00-12:00"));
                data.add(new Course("生物科学制药", "哇哈哈", "2N-324", "10:00-12:00"));
                recycler.getAdapter().notifyDataSetChanged();
            }
        });
        recycler = (RecyclerView) findViewById(R.id.view_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));


        recycler.setAdapter(new CommonAdapter<Course>(TimetableActivity.this, R.layout.item_course, data) {
            @Override
            protected void convert(ViewHolder holder, Course course, int position) {
                holder.setText(R.id.text_class_address, String.format(getResources().getString(R.string.course_address), course.getAddress()));
                holder.setText(R.id.text_name_course, course.getCourseName());
                holder.setText(R.id.text_name_teacher, String.format(getResources().getString(R.string.course_teacher), course.getTeacherName()));
                holder.setText(R.id.text_time, course.getTime());
            }

            @Override
            public int getItemCount() {
                int itemCount = super.getItemCount();
                Log.i("课程", "课程数" + itemCount);
                return itemCount;
            }

        });


    }


    private SpannableString getColorText(String string, int color, int start, int end) {
        SpannableString sp = new SpannableString(string);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, color)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
