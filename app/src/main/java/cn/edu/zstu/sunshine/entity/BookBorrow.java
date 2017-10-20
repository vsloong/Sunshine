package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.edu.zstu.sunshine.base.AppConfig;

/**
 * 个人借书实体类
 * Created by CooLoongWu on 2017-10-20 14:37.
 */

@Entity
public class BookBorrow {

    @Id
    private Long id;

    private String userId;      //学生的学号或者是教师的学号

    private String bookName;    //书名
    private String author;      //作者名
    private String borrowDate;  //借书日期
    private String returnDate;  //归还日期


    @Generated(hash = 1406867693)
    public BookBorrow(Long id, String userId, String bookName, String author,
                      String borrowDate, String returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookName = bookName;
        this.author = author;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }


    @Generated(hash = 1207451502)
    public BookBorrow() {
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


    public String getBookName() {
        return this.bookName;
    }


    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


    public String getAuthor() {
        return this.author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }


    public String getBorrowDate() {
        return this.borrowDate;
    }


    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }


    public String getReturnDate() {
        return this.returnDate;
    }


    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }


}
