package cn.edu.zstu.sunshine.tools.timetable;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableAddBinding;
import cn.edu.zstu.sunshine.entity.Course;
import cn.edu.zstu.sunshine.entity.Ordinal;
import cn.edu.zstu.sunshine.greendao.CourseDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.DialogUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;
import cn.edu.zstu.sunshine.views.RangeSeekBar;

/**
 * 添加课表课程的ViewModel
 * Created by CooLoongWu on 2017-10-25 14:08.
 */

public class TimetableAddViewModel {

    private Context context;
    private ActivityTimetableAddBinding binding;
    private String courseId;
    private CourseDao dao;
    private boolean isUpdate = false; //是更新数据还是插入数据

    public ObservableField<Course> course = new ObservableField<>();

    private List<Ordinal> weeks = new ArrayList<>();

    public TimetableAddViewModel(Context context, ActivityTimetableAddBinding binding, String courseId) {
        this.context = context;
        this.binding = binding;
        this.courseId = courseId;

        dao = DaoUtil.getInstance().getSession().getCourseDao();

        for (int i = 0; i < 7; i++) {
            weeks.add(new Ordinal(i, false));
        }

        init();
    }

    public void setWeeks(List<Ordinal> data) {
        this.weeks.clear();
        Logger.e("设置数据：" + data.size());
        this.weeks.addAll(data);
    }

    public List<Ordinal> getWeeks() {
        return weeks;
    }

    private void init() {
        if (courseId.isEmpty()) {
            //添加课程，默认设置课程ID为系统的时间戳
            Course newCourse = new Course();
            newCourse.setCourseId(String.valueOf(System.currentTimeMillis()));
            course.set(newCourse);

        } else {
            isUpdate = true;
            //修改课程
            Course oldCourse = dao.queryBuilder()
                    .where(
                            CourseDao.Properties.UserId.eq(AppConfig.getDefaultUserId()),
                            CourseDao.Properties.CourseId.eq(courseId)
                    )
                    .build()
                    .unique();
            course.set(oldCourse);
        }

    }

    /**
     * 确认修改【判断是否冲突，星期、上课节数、起始周】
     *
     * @param view 按钮
     */
    public void onBtnConfirmClick(View view) {
        Logger.e("修改后的数据："
                + course.get().getCourseId() + "；"
                + course.get().getCourseName() + "；"
                + course.get().getTeacherName() + "；"
                + course.get().getAddress() + "；"
                + course.get().getTime() + "；"
                + course.get().getDay() + "；"
                + course.get().getWeeks() + "；"
        );
        if (course.get().getCourseName() == null
                || course.get().getTeacherName() == null
                || course.get().getAddress() == null
                || course.get().getDay() == 0
                || course.get().getWeeks() == null
                ) {
            ToastUtil.showShortToast(R.string.toast_course_fill);
            return;
        }

        List<Course> conflictCourse = dao
                .queryBuilder()
                .where(
                        CourseDao.Properties.UserId.eq(AppConfig.getDefaultUserId()),
                        CourseDao.Properties.CourseId.notEq(course.get().getCourseId()),//查询非本课程的其他课程
                        CourseDao.Properties.Day.eq(course.get().getDay()),
                        CourseDao.Properties.Weeks.eq(course.get().getWeeks()),
                        CourseDao.Properties.Time.eq(course.get().getTime())
                )
                .build()
                .list();

        if (conflictCourse.isEmpty()) {
            Logger.e("没有冲突的课程");
            if (isUpdate) {
                Logger.e("更新课程");
                dao.update(course.get());
            } else {
                Logger.e("插入新课程");
                dao.insert(course.get());
            }

            //关闭该页面并通知刷新课表
            EventBus.getDefault().post(new Course());
            ((TimetableAddActivity) context).finish();
        } else {
            Logger.e("存在冲突的课程");
            ToastUtil.showShortToast(R.string.toast_course_conflict);
        }

    }

    /**
     * 点击设置课程时间
     *
     * @param view view
     */
    public void onCourseTimeClick(View view) {
        new DialogUtil(context)
                .setLayout(R.layout.dialog_range)
                .setTitle("设置上课时间")
                .setButtonText("确定")
                .onSetViewListener(new DialogUtil.IonSetViewListener() {
                    @Override
                    public void setView(ViewDataBinding binding, AlertDialog dialog) {
                        ((RangeSeekBar) binding.getRoot().findViewById(R.id.seek_bar_time)).setOnCursorChangeListener(
                                new RangeSeekBar.OnCursorChangeListener() {
                                    @Override
                                    public void onLeftCursorChanged(int location, String textMark) {
                                        Logger.e("左侧指针位置：" + textMark);
                                    }

                                    @Override
                                    public void onRightCursorChanged(int location, String textMark) {
                                        Logger.e("右侧指针位置：" + textMark);
                                    }
                                }
                        );
                    }
                })
                .build()
                .show();
    }

    /**
     * 点击设置课程持续周数
     *
     * @param view view
     */
    public void onCourseWeeksClick(View view) {

    }

    /**
     * 返回上一页
     *
     * @param view 按钮
     */
    public void onBtnBackClick(View view) {
        ((TimetableAddActivity) context).finish();
    }

    /**
     * 课程ID提示
     *
     * @param view 按钮
     */
    public void onBtnQuestionClick(View view) {
        ToastUtil.showShortToast(R.string.toast_course_id);
    }
}
