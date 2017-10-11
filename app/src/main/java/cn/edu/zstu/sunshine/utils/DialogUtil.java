package cn.edu.zstu.sunshine.utils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.zstu.sunshine.R;

/**
 * 创建Dialog的工具类
 * Created by CooLoongWu on 2017-8-24 13:40.
 */

public class DialogUtil {

    private Context context;
    private boolean cancelable = false;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private int layoutId;
    private ViewDataBinding binding;

    private View.OnClickListener confirmClickListener;
    private View.OnClickListener cancelClickListener;
    private IonSetViewListener onSetViewListener;

    private View dialogView;


    public DialogUtil(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context, R.style.DialogTheme);
    }

    public DialogUtil setLayout(int layoutId) {
        this.layoutId = layoutId;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false);
        dialogView = binding.getRoot();

        builder.setView(dialogView);
        dialog = builder.create();
        return this;
    }

    public DialogUtil setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public DialogUtil onConfirmClickListener(View.OnClickListener listener) {
        this.confirmClickListener = listener;
        return this;
    }

    public DialogUtil onCancelClickListener(View.OnClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public DialogUtil onSetViewListener(IonSetViewListener listener) {

        this.onSetViewListener = listener;
        listener.setView(binding, dialog);
        return this;
    }

    public AlertDialog build() {

        dialog.setCancelable(cancelable);

        dialogView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmClickListener != null) {
                    confirmClickListener.onClick(view);
                }
                dialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelClickListener != null) {
                    cancelClickListener.onClick(view);
                }
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public DialogUtil setContent(String str) {
        TextView content = dialogView.findViewById(R.id.text_content);
        content.setVisibility(View.VISIBLE);
        content.setText(str);
        return this;
    }

    public DialogUtil setTitle(String str) {
        TextView title = dialogView.findViewById(R.id.text_title);
        title.setVisibility(View.VISIBLE);
        title.setText(str);
        return this;
    }

    public DialogUtil setButtonText(String str) {
        Button btn = dialogView.findViewById(R.id.btn_confirm);
        btn.setText(str);
        return this;
    }

    public interface IonSetViewListener {
        void setView(ViewDataBinding binding, AlertDialog dialog);
    }

}
