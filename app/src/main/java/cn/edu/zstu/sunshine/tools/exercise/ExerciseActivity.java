package cn.edu.zstu.sunshine.tools.exercise;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityExerciseBinding;
import cn.edu.zstu.sunshine.entity.Exercise;
import cn.edu.zstu.sunshine.utils.ToastUtil;

public class ExerciseActivity extends BaseActivity {

    private ActivityExerciseBinding binding;
    private ExerciseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);
        viewModel = new ExerciseViewModel(this, binding);
        binding.setViewModel(viewModel);

        initToolbar();
        initViews();

        viewModel.loadDataFromNetWork();
    }

    private void initToolbar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_exercise);
        setSupportActionBar(binding.includeTitle.toolbar);
        binding.includeTitle.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews() {
        binding.includeRecycler.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.includeRecycler.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_exercise, BR.exercise, viewModel.getData()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        viewModel.loadDataFromNetWork();
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(Exercise exercise) {
        viewModel.init();
        ToastUtil.showShortToast(R.string.toast_data_refresh_success);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Api.cancel(this);
    }
}
