package cn.edu.zstu.sunshine.utils;

import cn.edu.zstu.sunshine.entity.Network;

/**
 * 实体类属性复制
 * Created by CooLoongWu on 2017-9-22 14:01.
 */

public class EntityCopyUtil {

    public static Network CopyNetwork(Network networkTo, Network networkFrom) {
        networkTo.setPort(networkFrom.getPort());
        networkTo.setIp(networkFrom.getIp());
        networkTo.setType(networkFrom.getType());
        networkTo.setName(networkFrom.getName());
        networkTo.setBalance(networkFrom.getBalance());
        networkTo.setUserId(networkFrom.getUserId());
        return networkTo;
    }
}
