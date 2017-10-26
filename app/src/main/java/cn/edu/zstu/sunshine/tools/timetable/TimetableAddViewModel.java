package cn.edu.zstu.sunshine.tools.timetable;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableAddBinding;
import cn.edu.zstu.sunshine.entity.Course;
import cn.edu.zstu.sunshine.greendao.CourseDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import cn.edu.zstu.sunshine.utils.ToastUtil;

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

    public TimetableAddViewModel(Context context, ActivityTimetableAddBinding binding, String courseId) {
        this.context = context;
        this.binding = binding;
        this.courseId = courseId;

        dao = DaoUtil.getInstance().getSession().getCourseDao();

        init();
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
     * 确认修改
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

//        if (isUpdate) {
//            dao.update(course.get());
//        } else {
//            dao.insert(course.get());
//        }
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
