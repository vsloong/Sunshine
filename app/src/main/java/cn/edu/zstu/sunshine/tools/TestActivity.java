package cn.edu.zstu.sunshine.tools;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;

public class TestActivity extends BaseActivity {

    OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String defaultStr = "Hello You !";
        String str = new String(Base64.encode(defaultStr.getBytes(), Base64.DEFAULT));

        Log.e("编码：", str);
        str = new String(Base64.decode(str, Base64.DEFAULT));
        Log.e("解码：", str);

//        MorseUtil.init();
//        MorseUtil.show("SOS");

//        simulateKey(KeyEvent.KEYCODE_BACK);
    }

    /**
     * 执行shell命令
     *
     * @param cmd 命令
     */
    private void exec(String cmd) {
        if (os == null) {
            try {
                os = Runtime.getRuntime().exec("su").getOutputStream();
                os.write(cmd.getBytes());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void simulateKey(int keyCode) {
        exec("input keyevent " + keyCode + "\n");
    }

    /**
     * adb shell getevent -p | grep -e "0035" -e "0036" 获取屏幕宽高
     *  我的是魅蓝5，分辨率1280*720 像素密度282，比例正好是1:1的
     * 0035  : value 0, min 0, max 720, fuzz 0, flat 0, resolution 0
     * 0036  : value 0, min 0, max 1280, fuzz 0, flat 0, resolution 0
     *
     *
     * adb shell getevent | grep -e "0035" -e "0036" 获取屏幕点击位置坐标
     */
}
