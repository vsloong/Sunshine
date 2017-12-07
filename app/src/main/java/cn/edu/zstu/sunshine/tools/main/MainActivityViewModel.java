package cn.edu.zstu.sunshine.tools.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseViewModel;
import cn.edu.zstu.sunshine.databinding.ActivityMainBinding;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.entity.Tool;
import cn.edu.zstu.sunshine.tools.campuscard.CampusCardActivity;
import cn.edu.zstu.sunshine.tools.timetable.TimetableActivity;
import cn.edu.zstu.sunshine.tools.user.UserActivity;

/**
 * 主页面的ViewModel
 * Created by CooLoongWu on 2017-8-30 16:35.
 */

public class MainActivityViewModel extends BaseViewModel<Tool> {

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
            R.mipmap.ic_main_timetable,
            R.mipmap.ic_main_card,
            R.mipmap.ic_main_exam,
            R.mipmap.ic_main_score,
            R.mipmap.ic_main_networke,
            R.mipmap.ic_main_exercise,
            R.mipmap.ic_main_library,
            R.mipmap.ic_main_test
    };

    public ObservableBoolean haveUnRead = new ObservableBoolean();

    MainActivityViewModel(Context context, ViewDataBinding binding) {
        super(context, binding);

        //进入主页面再注册美洽客服服务，这里也是为了不占用app的启动速度
        MQConfig.init(context, AppConfig.KEY_MEIQIA, new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
                Logger.e("美洽客服初始化成功");
            }

            @Override
            public void onFailure(int code, String message) {
                Logger.e("美洽客服初始化失败，code：" + code + "；错误信息：" + message);
            }
        });
    }

    @Override
    protected String loadUrl() {
        return null;
    }

    @Override
    protected JsonParse<List<Tool>> parseStrToJson(String data) {
        return null;
    }

    @Override
    protected List<Tool> loadDataFromLocal() {
        //加载首页工具资源
        List<Tool> tools = new ArrayList<>();
        for (int i = 0; i < toolsName.length; i++) {
            tools.add(new Tool(toolsName[i], toolsIcon[i]));
        }
        return tools;
    }

    @Override
    protected void loadDataIntoView() {
        ActivityMainBinding mainBinding = (ActivityMainBinding) binding;
        if (mainBinding.recyclerView.getAdapter() != null) {
            mainBinding.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void onTimeTableClick(View view) {
        startNewActivity(TimetableActivity.class);
    }

    public void onCampusCardClick(View view) {
        startNewActivity(CampusCardActivity.class);
    }

    public void onUserClick(View view) {
        startNewActivity(UserActivity.class);
    }

    public void onCustomerServiceClick(View view) {

        MQConfig.ui.titleGravity = MQConfig.ui.MQTitleGravity.LEFT;//标题位置
        MQConfig.isVoiceSwitchOpen = false;//关闭语音功能，因为录音时有不显示录音按钮的Bug

//                //自定义UI，不建议这种方式
//                MQConfig.ui.backArrowIconResId = R.drawable.ic_back_24dp;//返回按钮
//                MQConfig.ui.titleBackgroundResId = R.color.colorPrimary;//标题栏背景色
//                MQConfig.ui.titleTextColorResId = R.color.colorAccent;//标题颜色
//                MQConfig.ui.leftChatBubbleColorResId = R.color.colorAccent;//左侧聊天气泡背景
//                MQConfig.ui.rightChatBubbleColorResId = R.color.blue_light;//右侧聊天气泡背景
//                MQConfig.ui.robotEvaluateTextColorResId = android.R.color.transparent;
//                MQConfig.ui.robotMenuItemTextColorResId = android.R.color.transparent;
//                MQConfig.ui.robotMenuTipTextColorResId = android.R.color.transparent;

        HashMap<String, String> clientInfo = new HashMap<>();
        clientInfo.put("学号", AppConfig.getDefaultUserId());
        //1、启动完全对话模式
        Intent intent = new MQIntentBuilder(context).setClientInfo(clientInfo).build();

        //2、启动单一表单模式
//                Intent intent = new Intent(context, MQMessageFormActivity.class);
        context.startActivity(intent);

        //将未读消息数清空
        haveUnRead.set(false);
    }

    private void startNewActivity(Class cla) {
        context.startActivity(new Intent(context, cla));
    }
}
