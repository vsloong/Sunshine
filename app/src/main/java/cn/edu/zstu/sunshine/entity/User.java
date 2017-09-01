package cn.edu.zstu.sunshine.entity;

import android.view.View;

import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import cn.edu.zstu.sunshine.greendao.UserDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 用户账号的实体类
 * Created by CooLoongWu on 2017-8-25 13:31.
 */

@Entity
public class User {

    @Id
    private Long id;

    private String userId;          //学生的学号或者是教师的学号
    private String userName;        //姓名
    private String userNickname;    //昵称
    private String userType;        //用户类型，学生、老师

    private String officePassword;      //教务密码，用于课表、考试、成绩查询
    private String campusCardPassword;  //校园一卡通密码，用于饭卡消费查询
    private String libraryPassword;     //图书馆密码，用于图书馆个人信息、借书信息查询
    private String networkPassword;     //网费密码，用于网费信息查询
    private String sportsPassword;      //体育密码，用于“阳光长跑”锻炼打卡信息查询

    public User(String userId, String userNickname) {
        this.id = null;
        this.userId = userId;
        this.userNickname = userNickname;
    }

    @Generated(hash = 522828270)
    public User(Long id, String userId, String userName, String userNickname,
                String userType, String officePassword, String campusCardPassword,
                String libraryPassword, String networkPassword, String sportsPassword) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userType = userType;
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

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return this.userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public void onBtnDeleteClick(View view) {
        Logger.e("删除按钮点击了");
        UserDao userDao = DaoUtil.getInstance().getSession().getUserDao();
        userDao.delete(this);
    }
}
