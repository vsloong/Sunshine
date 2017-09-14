package cn.edu.zstu.sunshine.tools.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.meiqia.core.MQMessageManager;
import com.meiqia.core.bean.MQMessage;
import com.orhanobut.logger.Logger;

/**
 * 美洽客服消息广播监听
 * Created by CooLoongWu on 2017-9-14 16:59.
 */

public class MQMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取 ACTION
        final String action = intent.getAction();
        // 接收新消息
        if (MQMessageManager.ACTION_NEW_MESSAGE_RECEIVED.equals(action)) {
            // 从 intent 获取消息 id
            String msgId = intent.getStringExtra("msgId");
            // 从 MCMessageManager 获取消息对象
            MQMessageManager messageManager = MQMessageManager.getInstance(context);
            MQMessage message = messageManager.getMQMessage(msgId);
            // do something
            Logger.e("收到新消息啦：" + message.getContent());
        }
    }
}
