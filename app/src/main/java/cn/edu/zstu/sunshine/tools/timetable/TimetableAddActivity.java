package cn.edu.zstu.sunshine.tools.timetable;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.orhanobut.logger.Logger;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableAddBinding;
import cn.edu.zstu.sunshine.databinding.ItemOrdinalBinding;

public class TimetableAddActivity extends BaseActivity {

    private ActivityTimetableAddBinding binding;
    private TimetableAddViewModel viewModel;

    private int weekPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String courseId = getIntent().getStringExtra("courseId");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable_add);
        viewModel = new TimetableAddViewModel(this, binding, courseId);
        binding.setViewModel(viewModel);

        initViews();
    }


    private void initViews() {
        initWeekView();
        initTimeView();
    }

    private void initWeekView() {
        LinearLayoutManager weekLayoutManager = new LinearLayoutManager(this);
        weekLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        binding.includeWeek.recyclerView.setLayoutManager(weekLayoutManager);
        final BaseAdapter weekAdapter = new BaseAdapter<>(R.layout.item_ordinal, BR.ordinal, viewModel.getWeeks());
        binding.includeWeek.recyclerView.setAdapter(weekAdapter);
        weekAdapter.setOnItemHandler(
                new BaseAdapter.OnItemHandler() {
                    @Override
                    public void onItemHandler(ViewDataBinding viewDataBinding, final int position) {
                        final ItemOrdinalBinding itemBinding = (ItemOrdinalBinding) viewDataBinding;
                        if (viewModel.getWeeks().get(position).isSelected()) {
                            Logger.e("被选中的周" + viewModel.getWeeks().get(position).getId());
                            itemBinding.getRoot().setSelected(true);
                        } else {
                            //由于RecyclerView的布局重用机制，这里必须将其他的布局设置为未选中状态
                            itemBinding.getRoot().setSelected(false);
                        }

                        itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.getWeeks().get(weekPosition).setSelected(false);
                                viewModel.getWeeks().get(position).setSelected(true);
                                weekPosition = position;
                                weekAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
        );
    }

    private void initTimeView() {
        LinearLayoutManager timeLayoutManager = new LinearLayoutManager(this);
        timeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.includeTime.recyclerView.setLayoutManager(timeLayoutManager);
        final BaseAdapter timeAdapter = new BaseAdapter<>(R.layout.item_ordinal, BR.ordinal, viewModel.getTime());
        binding.includeTime.recyclerView.setAdapter(timeAdapter);
        timeAdapter.setOnItemHandler(
                new BaseAdapter.OnItemHandler() {
                    @Override
                    public void onItemHandler(ViewDataBinding viewDataBinding, final int position) {
                        final ItemOrdinalBinding itemBinding = (ItemOrdinalBinding) viewDataBinding;
                        if (viewModel.getTime().get(position).isSelected()) {
                            Logger.e("被选中的课程" + position);
                            itemBinding.getRoot().setSelected(true);
                        } else {
                            //由于RecyclerView的布局重用机制，这里必须将其他的布局设置为未选中状态
                            itemBinding.getRoot().setSelected(false);
                        }

                        itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.getTime().get(position).setSelected(!viewModel.getTime().get(position).isSelected());
                                timeAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
        );
    }
}
