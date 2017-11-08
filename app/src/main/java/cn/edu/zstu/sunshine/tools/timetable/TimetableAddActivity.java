package cn.edu.zstu.sunshine.tools.timetable;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.BR;
import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.base.BaseAdapter;
import cn.edu.zstu.sunshine.databinding.ActivityTimetableAddBinding;
import cn.edu.zstu.sunshine.databinding.ItemOrdinalBinding;
import cn.edu.zstu.sunshine.entity.Ordinal;

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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.includeWeeks.recyclerView.setLayoutManager(manager);

        final BaseAdapter adapter = new BaseAdapter<>(R.layout.item_ordinal, BR.ordinal, viewModel.getWeeks());
        binding.includeWeeks.recyclerView.setAdapter(adapter);
        adapter.setOnItemHandler(
                new BaseAdapter.OnItemHandler() {
                    @Override
                    public void onItemHandler(ViewDataBinding viewDataBinding, final int position) {
                        final ItemOrdinalBinding itemBinding = (ItemOrdinalBinding) viewDataBinding;
                        if (viewModel.getWeeks().get(position).isSelected()) {
                            itemBinding.getRoot().setSelected(true);
                        }

                        itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                viewModel.getWeeks().get(weekPosition).setSelected(false);
//                                adapter.notifyItemChanged(weekPosition, viewModel.getWeeks().get(weekPosition));
                                viewModel.getWeeks().get(position).setSelected(true);
//                                adapter.notifyItemChanged(position, viewModel.getWeeks().get(position));
                                weekPosition = position;

                                List<Ordinal> newWeeks = new ArrayList<>();
                                newWeeks.addAll(viewModel.getWeeks());
                                viewModel.setWeeks(newWeeks);
                                Logger.e("当前选中的position" + weekPosition);
                                for (cn.edu.zstu.sunshine.entity.Ordinal o : viewModel.getWeeks()) {
                                    Logger.e("星期" + o.getId() + "；选择状态：" + o.isSelected());
                                }
                                adapter.notifyDataSetChanged();


                            }
                        });
                    }
                }
        );
    }
}
