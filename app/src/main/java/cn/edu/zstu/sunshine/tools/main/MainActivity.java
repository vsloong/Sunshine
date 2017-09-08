package cn.edu.zstu.sunshine.tools.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityMainBinding;
import cn.edu.zstu.sunshine.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel viewModel = new MainActivityViewModel(this, binding);
        binding.setViewModel(viewModel);


//        new DialogUtil(this, R.layout.dialog_base).show();

        try {
            new OkHttpUtil().getTest("https://www.baidu.com", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Logger.e("失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Logger.e(response.toString());
                    Logger.e(response.body().toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
