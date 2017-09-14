package cn.edu.zstu.sunshine.tools.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.meiqia.core.MQMessageManager;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityMainBinding;
import cn.edu.zstu.sunshine.entity.Tool;
import cn.edu.zstu.sunshine.tools.campuscard.CampusCardActivity;
import cn.edu.zstu.sunshine.tools.service.MQMessageReceiver;
import cn.edu.zstu.sunshine.tools.timetable.TimetableActivity;
import cn.edu.zstu.sunshine.utils.DialogUtil;
import cn.edu.zstu.sunshine.utils.OkHttpUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private MQMessageReceiver messageReceiver = new MQMessageReceiver();

    private static final String toolsName[] = {
            "课表", "饭卡", "考试", "成绩", "网费", "锻炼", "图书馆"};
    private static final int toolsIcon[] = {
            R.mipmap.ic_main_timetable,
            R.mipmap.ic_main_card,
            R.mipmap.ic_main_exam,
            R.mipmap.ic_main_score,
            R.mipmap.ic_main_networke,
            R.mipmap.ic_main_exercise,
            R.mipmap.ic_main_library
    };

    private static final Class cla[] = {
            TimetableActivity.class,
            CampusCardActivity.class,
            TimetableActivity.class,
            TimetableActivity.class,
            TimetableActivity.class,
            TimetableActivity.class,
            TimetableActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel viewModel = new MainActivityViewModel(this, binding);
        binding.setViewModel(viewModel);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MQMessageManager.ACTION_NEW_MESSAGE_RECEIVED);

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                intentFilter);

        //设置透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        List<Tool> tools = new ArrayList<>();
        for (int i = 0; i < toolsName.length; i++) {
            tools.add(new Tool(toolsName[i], toolsIcon[i]));
        }
        //加载首页工具资源
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_main_tool, BR.tool, tools)
                .setOnItemHandler(new BaseAdapter.OnItemHandler() {
                    @Override
                    public void onItemHandler(ViewDataBinding viewDataBinding, final int position) {
                        viewDataBinding.getRoot().findViewById(R.id.layout_item).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (position > 1) {
                                    ToastUtil.showShortToast(R.string.toast_notice_develop);
                                } else {
                                    startActivity(cla[position]);
                                }
                            }
                        });
                    }
                }));

        new DialogUtil(this, R.layout.dialog_base).show();


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }
}
