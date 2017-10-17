package cn.edu.zstu.sunshine.utils;

import cn.edu.zstu.sunshine.entity.Exam;
import cn.edu.zstu.sunshine.entity.Network;

/**
 * 实体类属性复制
 * Created by CooLoongWu on 2017-9-22 14:01.
 */

public class EntityCopyUtil {

    public static Network copyNetwork(Network networkTo, Network networkFrom) {
        networkTo.setPort(networkFrom.getPort());
        networkTo.setIp(networkFrom.getIp());
        networkTo.setType(networkFrom.getType());
        networkTo.setName(networkFrom.getName());
        networkTo.setBalance(networkFrom.getBalance());
        return networkTo;
    }

    public static Exam copyExam(Exam examTo, Exam examFrom) {
        examTo.setCourse(examFrom.getCourse());
        examTo.setAddress(examFrom.getAddress());
        examTo.setSeatNumber(examFrom.getSeatNumber());
        examTo.setTime(examFrom.getTime());
        return examTo;
    }
}
