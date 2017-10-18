package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.edu.zstu.sunshine.base.AppConfig;

/**
 * 考试的实体类
 * 课程、考试、成绩三项添加courseId来作为唯一
 * Created by CooLoongWu on 2017-9-25 14:49.
 */
@Entity
public class Exam {

    @Id
    private Long id;

    private String userId;

    private String courseId;  //课程ID
    private String course;  //考试科目
    private String time;    //考试时间
    private String address; //考试地点
    private String seatNumber;//座位号

    /**
     * 补全其他所需信息
     */
    public void complete() {
        setUserId(AppConfig.getDefaultUserId());
    }

    @Generated(hash = 1546043229)
    public Exam(Long id, String userId, String courseId, String course, String time,
                String address, String seatNumber) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.course = course;
        this.time = time;
        this.address = address;
        this.seatNumber = seatNumber;
    }

    @Generated(hash = 945526930)
    public Exam() {
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

    public String getCourse() {
        return this.course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSeatNumber() {
        return this.seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
