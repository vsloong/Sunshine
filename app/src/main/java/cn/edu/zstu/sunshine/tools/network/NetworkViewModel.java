package cn.edu.zstu.sunshine.tools.network;

import android.content.Context;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.databinding.ActivityNetworkBinding;

/**
 * 网费的ViewModel类
 * Created by CooLoongWu on 2017-9-19 17:12.
 */

public class NetworkViewModel {
    private Context context;
    private ActivityNetworkBinding binding;

    NetworkViewModel(Context context, ActivityNetworkBinding binding) {
        this.context = context;
        this.binding = binding;

        setToolbarName();
    }

    private void setToolbarName() {
        binding.include.toolbar.setTitle(R.string.title_activity_network);
    }
}
