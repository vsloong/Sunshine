package cn.edu.zstu.sunshine.timetable;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.edu.zstu.sunshine.entity.Course;

/**
 * Created by CooLoongWu on 2017-8-21 17:09.
 */

public class TimetableListBindings {

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<Course> items) {

    }
}
