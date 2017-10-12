package cn.edu.zstu.sunshine.tools.campuscard;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import cn.edu.zstu.sunshine.databinding.ActivityCampusCardBinding;
import cn.edu.zstu.sunshine.entity.CampusCard;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.utils.DataUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        initView();
        loadDataFromNetWork();
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

    private void initView() {
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.include.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_campus_card, BR.campusCard, viewModel.getData()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(CampusCard campusCard) {
        viewModel.init();
        ToastUtil.showShortToast(R.string.toast_data_refresh_success);
    }

    private void loadDataFromNetWork() {
        Api.getCampusCardInfo(this, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("获取信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Logger.e("获取信息成功：" + data);
                final JsonParse<List<CampusCard>> jsonParse = JSON.parseObject(data,
                        new TypeReference<JsonParse<List<CampusCard>>>() {
                        }
                );

                viewModel.insert(jsonParse.getData());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        viewModel.month.set(DataUtil.getCurrentMonth());
        loadDataFromNetWork();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Api.cancel(this);
    }


}
