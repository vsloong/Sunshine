package cn.edu.zstu.sunshine.tools.network;

import android.content.Context;
import android.databinding.ObservableField;

import com.orhanobut.logger.Logger;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityNetworkBinding;
import cn.edu.zstu.sunshine.entity.Network;
import cn.edu.zstu.sunshine.greendao.NetworkDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.EntityCopyUtil;

/**
 * 网费的ViewModel类
 * Created by CooLoongWu on 2017-9-19 17:12.
 */

public class NetworkViewModel {
    private Context context;
    private ActivityNetworkBinding binding;
    public ObservableField<Network> network = new ObservableField<>();

    private NetworkDao networkDao;

    NetworkViewModel(Context context, ActivityNetworkBinding binding) {
        this.context = context;
        this.binding = binding;

        networkDao = DaoUtil.getInstance().getSession().getNetworkDao();

        loadDataFromLocal();
    }

    /**
     * 加载本地数据
     */
    void loadDataFromLocal() {
        Network networkData = networkDao.queryBuilder()
                .where(NetworkDao.Properties.UserId.eq(AppConfig.getDefaultUserId()))
                .build()
                .unique();

        if (networkData != null) {
            Logger.e("网费余额：" + networkData.getBalance());
            Network temp = new Network();
            network.set(EntityCopyUtil.copyNetwork(temp, networkData));
        } else {
            Network temp = new Network();
            temp.setName("用户名");
            temp.setType("网络类型");
            temp.setPort("0");
            temp.setIp("ip地址");
            temp.setBalance("余额");
            network.set(temp);
        }
    }


}
