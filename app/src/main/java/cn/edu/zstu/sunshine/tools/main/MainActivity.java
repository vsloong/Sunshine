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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.meiqia.core.MQManager;
import com.meiqia.core.MQMessageManager;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import cn.edu.zstu.sunshine.utils.DateUtil;
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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityViewModel(this, binding);
        binding.setViewModel(viewModel);

        EventBus.getDefault().register(this);
        registerMQMessageReceiver();

        initViews();
        checkUpdateAndSkin();
    }

    private void initViews() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerView.setAdapter(new BaseAdapter<>(R.layout.item_main_tool, BR.tool, viewModel.getData())
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

    private void checkUpdateAndSkin() {
        Api.getUpdateInfo(this, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("检查更新失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.e("检查更新成功");
                //JsonParse<List<Update>> s = parseStrToJson(response.toString());
                Update update = JSON.parseObject(
                        response.body().string(),
                        new TypeReference<JsonParse<Update>>() {
                        })
                        .getData();


                long currentTime = System.currentTimeMillis();
                long effectiveTime = DateUtil.getMillis(update.getSkinEffectiveTime());
                long expiryTime = DateUtil.getMillis(update.getSkinExpiryTime());
                Logger.e("更新信息：" + update.isSkinChange()
                        + "；有效时间：" + effectiveTime + "-" + currentTime + "-" + expiryTime);

                if (update.isSkinChange()) {
                    //如果当前时间还未到皮肤生效的时间，那么去下载皮肤
                    if (currentTime < effectiveTime) {
                        downloadSkin(update.getSkinName(), update.getSkinCode(), update.getSkinDownloadUrl(), false);
                    }
                    //如果当前时间超过了皮肤失效时间，那么去还原默认皮肤
                    else if (currentTime > expiryTime) {
                        resumeDefaultSkin();
                    }
                    //当前时间在皮肤生效时间内，那么去下载皮肤并改变皮肤
                    else {
                        downloadSkin(update.getSkinName(), update.getSkinCode(), update.getSkinDownloadUrl(), true);
                    }
                } else {
                    resumeDefaultSkin();
                }
            }
        });
    }

    private void downloadSkin(final String fileName, final long fileCode, String url, final boolean isApply) {

        final String name = fileName + "_" + fileCode + ".skin";
        if (AppConfig.isFileExists(name)) {
            if (isApply) {
                changeSkin(name);
            }
            return;
        }

        Api.download(this, url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e(fileName + "下载失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                File fileDir = new File(AppConfig.FILE_PATH);
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    final long total = response.body().contentLength();

                    long sum = 0;

                    File file = new File(fileDir, name);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        sum += len;
                        fos.write(buf, 0, len);
                        final long finalSum = sum;

                        Logger.e("下载进度：" + (finalSum * 1.0f / total));
                    }
                    fos.flush();

                    Logger.e("下载完成");
                    if (isApply) {
                        changeSkin(name);
                    }

                } finally {
                    try {
                        response.body().close();
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }

                }
            }
        });
    }

    private void changeSkin(String fileName) {
        SkinManager.getInstance().changeSkin(
                AppConfig.FILE_PATH + File.separator + fileName,
                "cn.edu.zstu.sunshineskin",
                new ISkinChangingCallback() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onError(Exception e) {
                        //Toast.makeText(MainActivity.this, "换肤失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        //Toast.makeText(MainActivity.this, "换肤成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resumeDefaultSkin() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SkinManager.getInstance().removeAnySkin();
            }
        });

    }
}
