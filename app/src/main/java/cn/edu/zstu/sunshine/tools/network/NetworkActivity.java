package cn.edu.zstu.sunshine.tools.network;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityNetworkBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NetworkActivity extends BaseActivity {

    private ActivityNetworkBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_network);
        binding.setViewModel(new NetworkViewModel(this, binding));

        initToolBar();
        getData();
    }

    private void initToolBar() {
        binding.includeTitle.toolbar.setTitle(R.string.title_activity_network);
        binding.includeTitle.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getData() {
        Api.getNetworkInfo(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.e("成功" + response.body().string());
            }
        });
    }
}
