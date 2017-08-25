package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户账号的实体类
 * Created by CooLoongWu on 2017-8-25 13:31.
 */

@Entity
public class User {

    @Id
    private Long id;

    private String studentId;       //学号
    private String studentName;     //姓名

    private String officePassword;      //教务密码，用于课表、考试、成绩查询
    private String campusCardPassword;  //校园一卡通密码，用于饭卡消费查询
    private String libraryPassword;     //图书馆密码，用于图书馆个人信息、借书信息查询
    private String networkPassword;     //网费密码，用于网费信息查询
    private String sportsPassword;      //体育密码，用于“阳光长跑”锻炼打卡信息查询

    public User(String studentId, String studentName) {
        this.id = null;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    @Generated(hash = 1930408373)
    public User(Long id, String studentId, String studentName,
                String officePassword, String campusCardPassword,
                String libraryPassword, String networkPassword, String sportsPassword) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.officePassword = officePassword;
        this.campusCardPassword = campusCardPassword;
        this.libraryPassword = libraryPassword;
        this.networkPassword = networkPassword;
        this.sportsPassword = sportsPassword;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getOfficePassword() {
        return this.officePassword;
    }

    public void setOfficePassword(String officePassword) {
        this.officePassword = officePassword;
    }

    public String getCampusCardPassword() {
        return this.campusCardPassword;
    }

    public void setCampusCardPassword(String campusCardPassword) {
        this.campusCardPassword = campusCardPassword;
    }

    public String getLibraryPassword() {
        return this.libraryPassword;
    }

    public void setLibraryPassword(String libraryPassword) {
        this.libraryPassword = libraryPassword;
    }

    public String getNetworkPassword() {
        return this.networkPassword;
    }

    public void setNetworkPassword(String networkPassword) {
        this.networkPassword = networkPassword;
    }

    public String getSportsPassword() {
        return this.sportsPassword;
    }

    public void setSportsPassword(String sportsPassword) {
        this.sportsPassword = sportsPassword;
    }
}
