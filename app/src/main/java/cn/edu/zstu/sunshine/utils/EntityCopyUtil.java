package cn.edu.zstu.sunshine.utils;

import cn.edu.zstu.sunshine.entity.BookBorrow;
import cn.edu.zstu.sunshine.entity.Course;
import cn.edu.zstu.sunshine.entity.Exam;
import cn.edu.zstu.sunshine.entity.Exercise;
import cn.edu.zstu.sunshine.entity.Network;
import cn.edu.zstu.sunshine.entity.Score;

/**
 * 实体类属性复制
 * Created by CooLoongWu on 2017-9-22 14:01.
 */

public class EntityCopyUtil {

    public static Network copyNetwork(Network to, Network from) {
        to.setPort(from.getPort());
        to.setIp(from.getIp());
        to.setType(from.getType());
        to.setName(from.getName());
        to.setBalance(from.getBalance());
        return to;
    }

    public static Exam copyExam(Exam to, Exam from) {
        to.setCourse(from.getCourse());
        to.setAddress(from.getAddress());
        to.setSeatNumber(from.getSeatNumber());
        to.setTime(from.getTime());
        return to;
    }

    public static Exercise copyExercise(Exercise to, Exercise from) {
        to.setDate(from.getDate());
        to.setDuration(from.getDuration());
        to.setFirst(from.getFirst());
        to.setSecond(from.getSecond());
        return to;
    }

    public static Score copyScore(Score to, Score from) {
        to.setCourse(from.getCourse());
        to.setInstitute(from.getInstitute());
        to.setRebuildScore(from.getRebuildScore());
        to.setScore(from.getScore());
        to.setResitScore(from.getResitScore());
        to.setYear(from.getYear());
        to.setTerm(from.getTerm());
        return to;
    }

    public static Course copyCourse(Course to, Course from) {
        to.setTime(from.getTime());
        to.setAddress(from.getAddress());
        to.setCourseName(from.getCourseName());
        to.setTeacherName(from.getTeacherName());
        return to;
    }

    public static BookBorrow copyBookBorrow(BookBorrow to, BookBorrow from) {
        to.setAuthor(from.getAuthor());
        to.setBookName(from.getBookName());
        to.setBorrowDate(from.getBorrowDate());
        to.setReturnDate(from.getReturnDate());
        return to;
    }
}
