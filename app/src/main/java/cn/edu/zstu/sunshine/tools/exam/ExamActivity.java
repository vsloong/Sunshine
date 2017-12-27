package cn.edu.zstu.sunshine.tools.exam;

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
import cn.edu.zstu.sunshine.databinding.ActivityExamBinding;
import cn.edu.zstu.sunshine.entity.Exam;
import cn.edu.zstu.sunshine.utils.ToastUtil;

public class ExamActivity extends BaseActivity {

    private ActivityExamBinding binding;
    private ExamViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam);
        viewModel = new ExamViewModel(this, binding);
        binding.setViewModel(viewModel);

        initToolbar();
        initViews();
        viewModel.loadDataFromNetWork();
    }

    private void initToolbar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_exam);
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
        binding.include.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_exam, BR.exam, viewModel.getData()));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(Exam exam) {
        viewModel.init();
        ToastUtil.showShortToast(R.string.toast_data_refresh_success);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Api.cancel(this);
    }

}
