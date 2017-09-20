package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 网络信息的实体类
 * Created by CooLoongWu on 2017-9-20 10:25.
 */

@Entity
public class Network {

    @Id
    private Long id;

    private String userId;          //学生的学号或者是教师的学号
    private String name;    //用户名
    private String ip;      //IP地址
    private String balance; //余额
    private String type;    //网络类型（移动网通电信）
    private String port;    //端口号

    @Generated(hash = 1785083936)
    public Network(Long id, String userId, String name, String ip, String balance,
                   String type, String port) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.ip = ip;
        this.balance = balance;
        this.type = type;
        this.port = port;
    }

    @Generated(hash = 1981325040)
    public Network() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
