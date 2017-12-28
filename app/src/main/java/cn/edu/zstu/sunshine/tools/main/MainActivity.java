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
import android.view.ViewTreeObserver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.meiqia.core.MQManager;
import com.meiqia.core.MQMessageManager;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import cn.edu.zstu.skin.OnSkinChangedListener;
import cn.edu.zstu.skin.SkinManager;
import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityMainBinding;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.entity.Update;
import cn.edu.zstu.sunshine.event.UnRead;
import cn.edu.zstu.sunshine.interfaces.OnDownloadCallback;
import cn.edu.zstu.sunshine.service.MQMessageReceiver;
import cn.edu.zstu.sunshine.tools.TestActivity;
import cn.edu.zstu.sunshine.tools.campuscard.CampusCardActivity;
import cn.edu.zstu.sunshine.tools.exam.ExamActivity;
import cn.edu.zstu.sunshine.tools.exercise.ExerciseActivity;
import cn.edu.zstu.sunshine.tools.library.LibraryActivity;
import cn.edu.zstu.sunshine.tools.network.NetworkActivity;
import cn.edu.zstu.sunshine.tools.score.ScoreActivity;
import cn.edu.zstu.sunshine.tools.timetable.TimetableActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    public MainActivityViewModel viewModel;
    public ActivityMainBinding binding;
    private MQMessageReceiver messageReceiver = new MQMessageReceiver();

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

    private static final String toolsResName[] = {
            "ic_main_timetable",
            "ic_main_card",
            "ic_main_exam",
            "ic_main_score",
            "ic_main_network",
            "ic_main_exercise",
            "ic_main_library",
            "ic_main_test"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityViewModel(this, binding);
        binding.setViewModel(viewModel);

        EventBus.getDefault().register(this);
        registerMQMessageReceiver();

        initViews();

//        final String FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + "Sunshine";
//        SkinManager.getInstance().setSkinConfig(this,
//                FILE_PATH + File.separator + "sunshine_christmas_1712121455.skin",
//                "2017-11-28 00:00:00",
//                "2017-12-25 23:59:59"
//        );

//        downloadSkin("sunshine_christmas_1712121455.skin",
//                "https://raw.githubusercontent.com/CooLoongWu/Sunshine/master/skin/sunshine_christmas_1712121455.skin");
    }

    private void initViews() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_main_tool, BR.tool, viewModel.getData())
                .setOnItemHandler(new BaseAdapter.OnItemHandler() {
                    @Override
                    public void onItemHandler(ViewDataBinding viewDataBinding, final int position) {
                        String tag = "skin:src:mipmap:" + toolsResName[position];
//                        Logger.e("设置首页图标" + position + "的tag：" + tag);
                        viewDataBinding.getRoot().findViewById(R.id.img_tool_icon).setTag(tag);
                        viewDataBinding.getRoot().findViewById(R.id.text_tool_name).setTag("skin:textColor:color:text_tool");
//                      第二种方法
//                        ImageView img = viewDataBinding.getRoot().findViewById(R.id.img_tool_icon);
//                        img.setImageDrawable(SkinManager.getInstance().getResourcesManager().getMipmapByName(toolsResName[position]));
                        viewDataBinding.getRoot().findViewById(R.id.layout_item).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(cla[position]);
                            }
                        });
                    }
                }));

        //第一种方法（）【注册视图树观察者，当recyclerView加载完后会触发改事件，从而可以遍历视图进行换肤】
        binding.recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                SkinManager.getInstance().apply(MainActivity.this,
                        OnSkinChangedListener.DEFAULT_ON_SKIN_CHANGED_LISTENER);
                //使用完一次后必须撤销监听，否则会不停的不定时测量，消耗性能
                binding.recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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
    }

    private void checkUpdateAndSkin() {
        Api.getUpdateInfo(this, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("检查更新失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Update update = JSON.parseObject(
                        response.body().string(),
                        new TypeReference<JsonParse<Update>>() {
                        })
                        .getData();
                Logger.e("更新信息：" + update.isSkinChange()
                        + "；有效时间：" + update.getSkinEffectiveTime() + "-" + update.getSkinExpiryTime());

            }
        });
    }

    private void downloadSkin(String skinName, String url) {
        skinName += "56";

        Api.download(this, url, new OnDownloadCallback(AppConfig.FILE_PATH, skinName) {
            @Override
            public void onCompleted() {
                Logger.e("下载完成");
            }
        });
    }
}
