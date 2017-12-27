package cn.edu.zstu.sunshine.tools.campuscard;

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
import cn.edu.zstu.sunshine.databinding.ActivityCampusCardBinding;
import cn.edu.zstu.sunshine.entity.CampusCard;
import cn.edu.zstu.sunshine.utils.DateUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;

public class CampusCardActivity extends BaseActivity {

    private ActivityCampusCardBinding binding;
    private CampusCardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_campus_card);
        viewModel = new CampusCardViewModel(this, binding);
        binding.setViewModel(viewModel);

        initToolBar();
        initViews();

        viewModel.loadDataFromNetWork();
    }

    private void initToolBar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_campus_card);
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
        binding.include.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_campus_card, BR.campusCard, viewModel.getData()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(CampusCard campusCard) {
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
        viewModel.month.set(DateUtil.getCurrentMonth());
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
