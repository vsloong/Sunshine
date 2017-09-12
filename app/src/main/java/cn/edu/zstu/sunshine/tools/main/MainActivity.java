package cn.edu.zstu.sunshine.tools.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

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

        //设置透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

//        new DialogUtil(this, R.layout.dialog_base).show();

//        try {
//            new OkHttpUtil().getTest("https://www.baidu.com", new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Logger.e("失败");
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    Logger.e(response.toString());
//                    Logger.e(response.body().toString());
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        OkHttpUtil.getInstance()
                .post("http://www.easy-mock.com/mock/59acaa94e0dc6633419a3afe/sunshine/test")
                .build()
                .execute(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Logger.e("查询失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Logger.json(response.body().string());
                    }
                });
    }


    /**
     * 监听返回按键【当点击返回键时执行Home键效果】
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
