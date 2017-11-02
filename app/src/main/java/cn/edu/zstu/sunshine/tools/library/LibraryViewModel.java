package cn.edu.zstu.sunshine.tools.library;

import android.content.Context;

import java.util.List;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseViewModel;
import cn.edu.zstu.sunshine.databinding.ActivityLibraryBinding;
import cn.edu.zstu.sunshine.entity.BookBorrow;
import cn.edu.zstu.sunshine.greendao.BookBorrowDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 图书馆的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:22.
 */

public class LibraryViewModel extends BaseViewModel<BookBorrow> {

    private ActivityLibraryBinding binding;
    private BookBorrowDao dao;

    LibraryViewModel(Context context, ActivityLibraryBinding binding) {
        super(context);
        this.binding = binding;

        dao = DaoUtil.getInstance().getSession().getBookBorrowDao();
        init();
    }

    @Override
    public void init() {
        super.init();
        //showEmptyView必须在这里设置才可以生效，在父类中不可以
        showEmptyView.set(data.size() <= 0);
    }

    @Override
    protected void loadDataFromLocal() {
        List<BookBorrow> books = dao
                .queryBuilder()
                .where(
                        BookBorrowDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                )
                .list();
        setData(books);
    }

    @Override
    protected void loadDataIntoView() {
        if (binding.include.recyclerView.getAdapter() != null) {
            binding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
