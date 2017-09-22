package cn.edu.zstu.sunshine.tools.network;

import android.content.Context;
import android.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityNetworkBinding;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.entity.Network;
import cn.edu.zstu.sunshine.greendao.NetworkDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.EntityCopyUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 网费的ViewModel类
 * Created by CooLoongWu on 2017-9-19 17:12.
 */

public class NetworkViewModel {
    private Context context;
    private ActivityNetworkBinding binding;
    public ObservableField<Network> network = new ObservableField<>();

    private NetworkDao networkDao;
    private Network networkData;

    NetworkViewModel(Context context, ActivityNetworkBinding binding) {
        this.context = context;
        this.binding = binding;

        loadDataFromLocal();
    }

    /**
     * 加载本地数据
     */
    private void loadDataFromLocal() {
        networkDao = DaoUtil.getInstance().getSession().getNetworkDao();
        networkData = networkDao.queryBuilder()
                .where(NetworkDao.Properties.UserId.eq(AppConfig.getDefaultUserId()))
                .build()
                .unique();

        if (networkData != null) {
            network.set(networkData);
        } else {
            loadDataFromNetwork();
        }
    }

    /**
     * 加载网络数据
     */
    private void loadDataFromNetwork() {
        Api.getNetworkInfo(context, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("失败" + e.toString());
                //如果是自己取消的，那么结果为：java.io.IOException: Canceled
                //如果是没有网络，那么结果为：java.net.UnknownHostException:
                //超时错误：java.net.SocketTimeoutException:
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String json = response.body().string();
                Logger.e("成功：" + json);

                JsonParse<Network> jsonParse = JSON.parseObject(
                        json,
                        new TypeReference<JsonParse<Network>>() {
                        });

                Network network = jsonParse.getData();
                refreshData(network);
            }
        });
    }

    /**
     * 刷新数据【刷新页面上的数据以及数据库中的数据】
     *
     * @param network 网费信息实体
     */
    void refreshData(Network network) {
        this.network.set(network);
        this.insertOrUpdateDB(network);
        //ToastUtil.showShortToast(R.string.toast_data_refresh_success);
    }

    /**
     * 插入或者更新网费数据
     *
     * @param network 网费信息实体
     */
    private void insertOrUpdateDB(Network network) {
        networkData = networkDao.queryBuilder()
                .where(NetworkDao.Properties.UserId.eq(AppConfig.getDefaultUserId()))
                .build()
                .unique();
        if (networkData != null) {
            networkDao.update(
                    EntityCopyUtil.CopyNetwork(networkData, network)
            );
            Logger.e("网费数据更新");
        } else {
            networkDao.insert(network);
            Logger.e("网费数据插入");
        }


    }
}
