package cn.edu.zstu.sunshine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.edu.zstu.sunshine.base.AppConfig;

/**
 * 搜索图书的实体类
 * Created by CooLoongWu on 2017-10-20 14:46.
 */
@Entity
public class BookSearch {
    @Id
    private Long id;

    private String userId;      //学生的学号或者是教师的学号

    private String bookId;     //索书号
    private String bookName;    //书名
    private String author;      //作者名
    private String editor;      //主编
    private String publishing;  //出版社
    private String collectionNum;  //馆藏数量
    private String borrowNum;      //可借数量


    @Generated(hash = 1469766481)
    public BookSearch(Long id, String userId, String bookId, String bookName,
                      String author, String editor, String publishing, String collectionNum,
                      String borrowNum) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.editor = editor;
        this.publishing = publishing;
        this.collectionNum = collectionNum;
        this.borrowNum = borrowNum;
    }


    @Generated(hash = 1656979850)
    public BookSearch() {
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


    public String getBookId() {
        return this.bookId;
    }


    public void setBookId(String bookId) {
        this.bookId = bookId;
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


    public String getEditor() {
        return this.editor;
    }


    public void setEditor(String editor) {
        this.editor = editor;
    }


    public String getPublishing() {
        return this.publishing;
    }


    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }


    public String getCollectionNum() {
        return this.collectionNum;
    }


    public void setCollectionNum(String collectionNum) {
        this.collectionNum = collectionNum;
    }


    public String getBorrowNum() {
        return this.borrowNum;
    }


    public void setBorrowNum(String borrowNum) {
        this.borrowNum = borrowNum;
    }

}
