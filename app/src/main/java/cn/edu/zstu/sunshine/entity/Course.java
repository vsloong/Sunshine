package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 课程的实体类
 * Created by CooLoongWu on 2017-8-16 14:21.
 */
@Entity
public class Course {

    @Id
    private Long id;

    private String courseName;
    private String teacherName;
    private String address;
    private String time;

    public Course(String courseName, String teacherName, String address, String time) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.address = address;
        this.time = time;
    }

    @Generated(hash = 1367591428)
    public Course(Long id, String courseName, String teacherName, String address,
                  String time) {
        this.id = id;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.address = address;
        this.time = time;
    }

    @Generated(hash = 1355838961)
    public Course() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
