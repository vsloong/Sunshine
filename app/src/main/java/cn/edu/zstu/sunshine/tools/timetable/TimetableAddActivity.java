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
                        if (position == weekPosition) {
                            Logger.e("被选中的position" + position);
                            itemBinding.getRoot().setSelected(true);
                        } else {
                            //由于RecyclerView的布局重用机制，这里必须将其他的布局设置为未选中状态
                            itemBinding.getRoot().setSelected(false);
                        }

                        itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

//                                viewModel.getWeeks().get(weekPosition).setSelected(false);
//                                adapter.notifyItemChanged(weekPosition, viewModel.getWeeks().get(weekPosition));
//                                viewModel.getWeeks().get(position).setSelected(true);
//                                adapter.notifyItemChanged(position, viewModel.getWeeks().get(position));
                                weekPosition = position;

//                                List<Ordinal> newWeeks = new ArrayList<>();
//                                newWeeks.addAll(viewModel.getWeeks());
//                                viewModel.setWeeks(newWeeks);
//                                Logger.e("当前选中的position" + weekPosition);
//                                for (cn.edu.zstu.sunshine.entity.Ordinal o : viewModel.getWeeks()) {
//                                    Logger.e("星期" + o.getId() + "；选择状态：" + o.isSelected());
//                                }
                                adapter.notifyDataSetChanged();


                            }
                        });
                    }
                }
        );
    }
}
