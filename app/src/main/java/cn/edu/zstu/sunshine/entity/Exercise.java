package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.edu.zstu.sunshine.base.AppConfig;

/**
 * 锻炼的实体类
 * Created by CooLoongWu on 2017-10-20 14:55.
 */
@Entity
public class Exercise {

    @Id
    private Long id;

    private String userId;      //学生的学号或者是教师的学号
    private String date;        //打卡日期
    private String times;       //打卡次数
    private String duration;    //时长
    private String first;       //第一次打卡起始时间
    private String second;      //第二次打卡起始时间


    @Generated(hash = 1324365415)
    public Exercise(Long id, String userId, String date, String times,
                    String duration, String first, String second) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.times = times;
        this.duration = duration;
        this.first = first;
        this.second = second;
    }


    @Generated(hash = 1537691247)
    public Exercise() {
    }


    /**
     * 补全其他所需信息
     */
    public void complete() {
        this.userId = AppConfig.getDefaultUserId();
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


    public String getDate() {
        return this.date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public String getTimes() {
        return this.times;
    }


    public void setTimes(String times) {
        this.times = times;
    }


    public String getDuration() {
        return this.duration;
    }


    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getFirst() {
        return this.first;
    }


    public void setFirst(String first) {
        this.first = first;
    }


    public String getSecond() {
        return this.second;
    }


    public void setSecond(String second) {
        this.second = second;
    }
}
