package cn.edu.zstu.sunshine.timetable;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.entity.Course;

/**
 * 课表的ViewModel类
 * Created by CooLoongWu on 2017-8-21 14:59.
 */

public class TimetableViewModel extends BaseObservable {
    //属性必须为public
    public ObservableBoolean showEmptyView = new ObservableBoolean(false);

    public ObservableList<Course> courses = new ObservableArrayList<>();

    private Context context;

    TimetableViewModel(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        List<Course> data = new ArrayList<>();
        data.add(new Course("计算机原理与应用", "武帅龙", "2S-503", "07:00-09:00"));
        data.add(new Course("生物科学制药", "占波", "2N-324", "10:00-12:00"));
        courses.addAll(data);
        notifyPropertyChanged(BR.course);
    }
}
