package cn.edu.zstu.sunshine.utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * 莫尔斯码工具类
 * Created by CooLoongWu on 2017-12-28 11:13.
 */

public class MorseUtil {

    private static Map<String, String> encodeMap = new HashMap<>();
    private static Map<String, String> decodeMap = new HashMap<>();

    private static FlashUtil flashUtil;
    private static int duration = 100;

    public static void init() {
        flashUtil = FlashUtil.getInstance();
        encodeMap.put("a", ".-");
        encodeMap.put("b", "-...");
        encodeMap.put("c", "-.-.");
        encodeMap.put("d", "-..");
        encodeMap.put("e", ".");
        encodeMap.put("f", "..-.");
        encodeMap.put("g", "--.");
        encodeMap.put("h", "....");
        encodeMap.put("i", "..");
        encodeMap.put("j", ".---");
        encodeMap.put("k", "-.-");
        encodeMap.put("l", ".-..");
        encodeMap.put("m", "--");
        encodeMap.put("n", "-.");
        encodeMap.put("o", "---");
        encodeMap.put("p", ".--.");
        encodeMap.put("q", "--.-");
        encodeMap.put("r", ".-.");
        encodeMap.put("s", "...");
        encodeMap.put("t", "-");
        encodeMap.put("u", "..-");
        encodeMap.put("v", "...-");
        encodeMap.put("w", ".--");
        encodeMap.put("x", "-..-");
        encodeMap.put("y", "-.--");
        encodeMap.put("z", "--..");
        encodeMap.put("1", ".----");
        encodeMap.put("2", "..---");
        encodeMap.put("3", "...--");
        encodeMap.put("4", "....-");
        encodeMap.put("5", ".....");
        encodeMap.put("6", "-....");
        encodeMap.put("7", "--...");
        encodeMap.put("8", "---..");
        encodeMap.put("9", "----.");
        encodeMap.put("0", "-----");

        Log.e("encodeMap长度", "" + encodeMap.size());

        for (Map.Entry<String, String> temp : encodeMap.entrySet()) {
            Log.e("encodeMap", "key：" + temp.getKey() + "；value：" + temp.getValue());
            decodeMap.put(temp.getValue(), temp.getKey());
        }

        Log.e("decodeMap长度", "" + decodeMap.size());

    }

    /**
     * 将输入的字符串编码
     *
     * @param str 待编码字符串
     * @return 编码后得到的StringBuilder
     */
    public static StringBuilder encode(String str) {
        String lowerStr = str.toLowerCase();
        int length = lowerStr.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            //Log.e("当前位置字母", String.valueOf(lowerStr.charAt(i)));
            //原字符串字母间的间隔在莫尔斯码中用两个空格表示，因为每个字母后面都会跟一个空格，所以这里一个空格就好
            if (Character.isSpaceChar(lowerStr.charAt(i))) {
                builder.append(" ");
                continue;
            }
            String key = String.valueOf(lowerStr.charAt(i));
            String value = encodeMap.get(key);
            //有未识别的符号用？代替
            if (null == value) {
                builder.append("?");
            } else {
                builder.append(value);
                //每个莫尔斯码之间用一个空格分隔
                builder.append(" ");
            }
        }
        Log.e("编码结果：", builder.toString());
        return builder;
    }

    public static void show(String str) {
        StringBuilder builder = encode(str);
        int length = builder.length();
        for (int i = 0; i < length; i++) {
            for (MorseUtil.Action temp : MorseUtil.Action.values()) {
                if (builder.charAt(i) == temp.getChar()) {
                    temp.doIt();
                }
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        flashUtil.release();
    }

    public enum Action {
        Dit('.') {
            @Override
            public void doIt() {
                Log.e("莫尔斯码", ".");
                flashUtil.turnOn();
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flashUtil.turnOff();
            }
        },
        Dah('-') {
            @Override
            public void doIt() {
                Log.e("莫尔斯码", "-");
                flashUtil.turnOn();
                try {
                    Thread.sleep(duration * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flashUtil.turnOff();
            }
        },
        Space(' ') {
            @Override
            public void doIt() {
                Log.e("莫尔斯码", " ");
            }
        };

        char str;

        public char getChar() {
            return str;
        }

        Action(char str) {
            this.str = str;
        }

        public abstract void doIt();
    }

}
