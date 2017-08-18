package cn.edu.zstu.sunshine.entity;

import android.util.Log;
import android.view.View;

/**
 * 课程的实体类
 * Created by CooLoongWu on 2017-8-16 14:21.
 */

public class Course {

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

    //必须要加上View参数，否则报错
    public void modify(View view) {
        Log.e("按钮", "修改按钮");
    }
}
