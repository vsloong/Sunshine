package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.utils.DataUtil;

/**
 * 校园卡消费记录的实体类
 * Created by CooLoongWu on 2017-8-29 09:44.
 */
@Entity
public class CampusCard {

    @Id
    private Long id;

    private String userId;      //学生的学号或者是教师的学号
    private String time;        //消费时间
    private String location;    //大致消费地址，包括餐厅、图书馆、宿舍水电、充值POS机
    private String address;     //具体消费位置
    private double consumption; //消费金额
    private String purpose;     //消费用途

    private int year;           //年份【从消费时间中获取，用来统计信息等】
    private int month;          //月份【从消费时间中获取，用来统计信息等】

    /**
     * 补全其他所需信息
     */
    public void complete() {
        this.userId = AppConfig.getDefaultUserId();
        setYear(DataUtil.getYearFromString(time));
        setMonth(DataUtil.getMonthFromString(time));
    }

    @Generated(hash = 1119695301)
    public CampusCard(Long id, String userId, String time, String location,
                      String address, double consumption, String purpose, int year,
                      int month) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.location = location;
        this.address = address;
        this.consumption = consumption;
        this.purpose = purpose;
        this.year = year;
        this.month = month;
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

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public double getConsumption() {
        return this.consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


}
