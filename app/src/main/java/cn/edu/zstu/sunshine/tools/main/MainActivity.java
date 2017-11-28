package cn.edu.zstu.sunshine.tools.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.meiqia.core.MQManager;
import com.meiqia.core.MQMessageManager;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityMainBinding;
import cn.edu.zstu.sunshine.entity.Tool;
import cn.edu.zstu.sunshine.event.UnRead;
import cn.edu.zstu.sunshine.service.MQMessageReceiver;
import cn.edu.zstu.sunshine.skin.ISkinChangingCallback;
import cn.edu.zstu.sunshine.skin.SkinManager;
import cn.edu.zstu.sunshine.tools.TestActivity;
import cn.edu.zstu.sunshine.tools.campuscard.CampusCardActivity;
import cn.edu.zstu.sunshine.tools.exam.ExamActivity;
import cn.edu.zstu.sunshine.tools.exercise.ExerciseActivity;
import cn.edu.zstu.sunshine.tools.library.LibraryActivity;
import cn.edu.zstu.sunshine.tools.network.NetworkActivity;
import cn.edu.zstu.sunshine.tools.score.ScoreActivity;
import cn.edu.zstu.sunshine.tools.timetable.TimetableActivity;

public class MainActivity extends BaseActivity {

    public MainActivityViewModel viewModel;
    private MQMessageReceiver messageReceiver = new MQMessageReceiver();

    private static final String toolsName[] = {
            "课表",
            "饭卡",
            "考试",
            "成绩",
            "网费",
            "锻炼",
            "图书馆",
            "测试"
    };
    private static final int toolsIcon[] = {
            R.mipmap.ic_main_1,
            R.mipmap.ic_main_2,
            R.mipmap.ic_main_3,
            R.mipmap.ic_main_4,
            R.mipmap.ic_main_5,
            R.mipmap.ic_main_6,
            R.mipmap.ic_main_7,
            R.mipmap.ic_main_8
    };

    private static final Class cla[] = {
            TimetableActivity.class,
            CampusCardActivity.class,
            ExamActivity.class,
            ScoreActivity.class,
            NetworkActivity.class,
            ExerciseActivity.class,
            LibraryActivity.class,
            TestActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);

        //设置透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityViewModel(this, binding);
        binding.setViewModel(viewModel);

        EventBus.getDefault().register(this);
        registerMQMessageReceiver();

        //加载首页工具资源
        List<Tool> tools = new ArrayList<>();
        for (int i = 0; i < toolsName.length; i++) {
            tools.add(new Tool(toolsName[i], toolsIcon[i]));
        }
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_main_tool, BR.tool, tools)
                .setOnItemHandler(new BaseAdapter.OnItemHandler() {
                    @Override
                    public void onItemHandler(ViewDataBinding viewDataBinding, final int position) {
                        viewDataBinding.getRoot().findViewById(R.id.layout_item).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(cla[position]);
                            }
                        });
                    }
                }));

        //new DialogUtil(this, R.layout.dialog_base).show();

        String mSkinPkgPath = Environment.getExternalStorageDirectory() + File.separator + "sunshine_skin.apk";


        SkinManager.getInstance().changeSkin(
                mSkinPkgPath,
                "cn.edu.zstu.sunshineskin",
                new ISkinChangingCallback() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, "换肤失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "换肤成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Subscribe
    public void onMQMessageEvent(UnRead unRead) {
        viewModel.haveUnRead.set(true);
    }

    /**
     * 监听客服消息
     */
    private void registerMQMessageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MQMessageManager.ACTION_NEW_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                intentFilter);

        //判断是否有未读消息
        MQManager.getInstance(this).getUnreadMessages(new OnGetMessageListCallback() {
            @Override
            public void onFailure(int i, String s) {
            }

            @Override
            public void onSuccess(List<MQMessage> list) {
                Logger.e("未读消息数：" + list.size());
                viewModel.haveUnRead.set(!list.isEmpty());
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
        EventBus.getDefault().unregister(this);
        SkinManager.getInstance().unregister(this);
    }
}
