package cn.edu.zstu.sunshine.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.entity.Course;


/**
 * 课程类的适配器
 * Created by CooLoongWu on 2017-8-17 17:03.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Course> courses = new ArrayList<>();

    public CourseAdapter(List<Course> data) {
        courses = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_course, parent, false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().setVariable(cn.edu.zstu.sunshine.BR.course, courses.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
        }

        void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        ViewDataBinding getBinding() {
            return this.binding;
        }
    }

}
