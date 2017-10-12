package cn.edu.zstu.sunshine.tools.network;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityNetworkBinding;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.entity.Network;
import cn.edu.zstu.sunshine.utils.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NetworkActivity extends BaseActivity {

    private ActivityNetworkBinding binding;
    private NetworkViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_network);
        viewModel = new NetworkViewModel(this, binding);
        binding.setViewModel(viewModel);

        initToolBar();
        loadDataFromNetwork();
    }

    private void initToolBar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_network);
        setSupportActionBar(binding.includeTitle.toolbar);
        binding.includeTitle.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(Network network) {
        viewModel.loadDataFromLocal();
        ToastUtil.showShortToast(R.string.toast_data_refresh_success);
    }

    private void loadDataFromNetwork() {
        Api.getNetworkInfo(this, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("失败" + e.toString());
                //如果是自己取消的，那么结果为：java.io.IOException: Canceled
                //如果是没有网络，那么结果为：java.net.UnknownHostException:
                //超时错误：java.net.SocketTimeoutException:
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String json = response.body().string();
                Logger.e("成功：" + json);

                JsonParse<Network> jsonParse = JSON.parseObject(
                        json,
                        new TypeReference<JsonParse<Network>>() {
                        });

                final Network network = jsonParse.getData();
                Logger.e("网费数据");
                Logger.e("IP：" + network.getIp());
                Logger.e("用户名：" + network.getName());
                Logger.e("余额：" + network.getBalance());
                Logger.e("类型：" + network.getType());
                Logger.e("端口号：" + network.getPort());
                network.complete();
                viewModel.insertOrUpdateDB(network);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Api.cancel(this);
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loadDataFromNetwork();
        return super.onOptionsItemSelected(item);
    }
}
