package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 校园卡消费记录的实体类
 * Created by CooLoongWu on 2017-8-29 09:44.
 */
@Entity
public class CampusCard {

    @Id
    private Long id;

    private String time;        //消费时间
    private String location;    //大致消费地址，包括餐厅、图书馆、宿舍水电、充值POS机
    private String address;     //具体消费位置
    private String consumption; //消费金额
    private String purpose;     //消费用途

    @Generated(hash = 2060271943)
    public CampusCard(Long id, String time, String location, String address,
                      String consumption, String purpose) {
        this.id = id;
        this.time = time;
        this.location = location;
        this.address = address;
        this.consumption = consumption;
        this.purpose = purpose;
    }

    @Generated(hash = 782523111)
    public CampusCard() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsumption() {
        return this.consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}
