package cn.edu.zstu.sunshine.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.meiqia.core.MQManager;
import com.meiqia.core.MQMessageManager;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.zstu.sunshine.event.UnRead;

/**
 * 美洽客服消息广播监听
 * Created by CooLoongWu on 2017-9-14 16:59.
 */

public class MQMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        MQManager.getInstance(context).getUnreadMessages(new OnGetMessageListCallback() {
            @Override
            public void onFailure(int i, String s) {
                Logger.e("接受新消息失败：" + s);
            }

            @Override
            public void onSuccess(List<MQMessage> list) {
                Logger.e("收到未读消息：" + list.size());
                if (!list.isEmpty()) {
                    EventBus.getDefault().post(new UnRead());
                }

            }
        });

        // 获取 ACTION
        final String action = intent.getAction();
        // 接收新消息
        if (MQMessageManager.ACTION_NEW_MESSAGE_RECEIVED.equals(action)) {
            // 从 intent 获取消息 id
            String msgId = intent.getStringExtra("msgId");
            // 从 MCMessageManager 获取消息对象
            MQMessageManager messageManager = MQMessageManager.getInstance(context);
            MQMessage message = messageManager.getMQMessage(msgId);
        }
    }
}
