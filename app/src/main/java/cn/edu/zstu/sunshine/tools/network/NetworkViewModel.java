package cn.edu.zstu.sunshine.tools.network;

import android.content.Context;
import android.databinding.ObservableField;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.databinding.ActivityNetworkBinding;
import cn.edu.zstu.sunshine.entity.Network;
import cn.edu.zstu.sunshine.utils.ToastUtil;

/**
 * 网费的ViewModel类
 * Created by CooLoongWu on 2017-9-19 17:12.
 */

public class NetworkViewModel {
    private Context context;
    private ActivityNetworkBinding binding;

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> ip = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> port = new ObservableField<>();
    public ObservableField<String> balance = new ObservableField<>();

    NetworkViewModel(Context context, ActivityNetworkBinding binding) {
        this.context = context;
        this.binding = binding;

        initData();
    }

    private void initData() {
        name.set("是我啊");
        ip.set("127.0.0.1");
        type.set("大网通");
        port.set("02" + "号床位");
        balance.set("20.34" + "元");
    }

    void refreshData(Network network) {
        name.set(network.getName());
        ip.set(network.getIp());
        type.set(network.getType());
        port.set(network.getPort() + "号床位");
        balance.set(network.getBalance() + "元");

        ToastUtil.showShortToast(R.string.toast_data_refresh_success);
    }
}
