package cn.edu.zstu.sunshine.tools.timetable;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableBinding;
import cn.edu.zstu.sunshine.databinding.ItemCourseBinding;
import cn.edu.zstu.sunshine.databinding.ItemTabBinding;
import cn.edu.zstu.sunshine.entity.Course;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.DateUtil;
import cn.edu.zstu.sunshine.utils.IntentUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TimetableActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    private ActivityTimetableBinding binding;
    private TimetableViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable);
        viewModel = new TimetableViewModel(this, binding);
        binding.setViewModel(viewModel);

        initToolBar();
        initTabLayout();
        initViews();
        loadDataFromNetWork();
    }

    private void initToolBar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_timetable);
        setSupportActionBar(binding.includeTitle.toolbar);
        binding.includeTitle.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews() {
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.include.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_course, BR.course, viewModel.getData()).setOnItemHandler(
                new BaseAdapter.OnItemHandler() {
                    @Override
                    public void onItemHandler(final ViewDataBinding viewDataBinding, final int position) {
                        ItemCourseBinding binding = (ItemCourseBinding) viewDataBinding;
                        binding.btnModify.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //ToastUtil.showShortToast(R.string.toast_tip_enrollment_time);
                                        Logger.e(viewModel.getData().get(position).getCourseName());
                                        IntentUtil.startTimetableAddActivity(TimetableActivity.this, viewModel.getData().get(position).getCourseId());
                                    }
                                }
                        );
                    }
                }
        ));
    }

    private void initTabLayout() {
        int dayOfWeek = DateUtil.getDayOfWeek() - 1;
        int datesOfWeek[] = DateUtil.getDatesOfWeek();

        for (int i = 0; i < 7; i++) {
            ItemTabBinding itemTabBinding = ItemTabBinding.inflate(LayoutInflater.from(this), binding.tabLayout, false);
            itemTabBinding.setItemWeek(DateUtil.week[i]);
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
        binding.tabLayout.addOnTabSelectedListener(this);
    }

    private void loadDataFromNetWork() {
        Api.getTimetableInfo(this, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("获取信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Logger.e("获取信息成功：" + data);
                final JsonParse<List<Course>> jsonParse = JSON.parseObject(data,
                        new TypeReference<JsonParse<List<Course>>>() {
                        }
                );

                DaoUtil.insertOrUpdate(jsonParse.getData());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(Course course) {
        viewModel.backToToday();
        ToastUtil.showShortToast(R.string.toast_data_refresh_success);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_timetable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                IntentUtil.startTimetableAddActivity(TimetableActivity.this, "");
                break;
            case R.id.menu_refresh:
                loadDataFromNetWork();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Api.cancel(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Logger.e("点击了周" + (tab.getPosition() + 1));
        viewModel.setDay(tab.getPosition() + 1);
        viewModel.init();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
