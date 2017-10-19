package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.edu.zstu.sunshine.base.AppConfig;

/**
 * 课程的实体类
 * 课程、考试、成绩三项添加courseId来作为唯一
 * Created by CooLoongWu on 2017-8-16 14:21.
 */
@Entity
public class Course {

    @Id
    private Long id;

    private String userId;          //学生的学号或者是教师的学号
    private String courseId;        //课程ID
    private String courseName;
    private String teacherName;
    private String address;
    private String time;
    private int day;//星期几，周一就是1

    /**
     * 补全其他所需信息
     */
    public void complete() {
        setUserId(AppConfig.getDefaultUserId());
    }

    public Course(String courseName, String teacherName, String address, String time) {
        this.userId = AppConfig.getDefaultUserId();
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.address = address;
        this.time = time;
    }

    @Generated(hash = 13235015)
    public Course(Long id, String userId, String courseId, String courseName,
                  String teacherName, String address, String time) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
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

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
