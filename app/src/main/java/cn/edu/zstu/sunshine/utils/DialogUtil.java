package cn.edu.zstu.sunshine.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import cn.edu.zstu.sunshine.R;

/**
 * 创建Dialog的工具类
 * Created by CooLoongWu on 2017-8-24 13:40.
 */

public class DialogUtil {

    private Context context;
    private int layoutId;

    public DialogUtil(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);

        View view = View.inflate(context, layoutId, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}
