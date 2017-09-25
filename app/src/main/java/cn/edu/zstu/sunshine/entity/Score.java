package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 考试成绩的实体类
 * Created by CooLoongWu on 2017-9-25 15:12.
 */

@Entity
public class Score {

    @Id
    private Long id;

    private String userId;

    private String year;//学年
    private String term;//学期
    private String course;          //课程名
    private String score;           //成绩
    private String resitScore;     //补考成绩
    private String rebuildScore;    //重修成绩
    private String institute;       //开课学院

    @Generated(hash = 1668242109)
    public Score(Long id, String userId, String year, String term, String course,
                 String score, String resitScore, String rebuildScore,
                 String institute) {
        this.id = id;
        this.userId = userId;
        this.year = year;
        this.term = term;
        this.course = course;
        this.score = score;
        this.resitScore = resitScore;
        this.rebuildScore = rebuildScore;
        this.institute = institute;
    }

    @Generated(hash = 226049941)
    public Score() {
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

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCourse() {
        return this.course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getResitScore() {
        return this.resitScore;
    }

    public void setResitScore(String resitScore) {
        this.resitScore = resitScore;
    }

    public String getRebuildScore() {
        return this.rebuildScore;
    }

    public void setRebuildScore(String rebuildScore) {
        this.rebuildScore = rebuildScore;
    }

    public String getInstitute() {
        return this.institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

}
