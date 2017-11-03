package cn.edu.zstu.sunshine.tools.library;

import android.content.Context;
import android.databinding.ViewDataBinding;

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

    private BookBorrowDao dao;

    LibraryViewModel(Context context, ViewDataBinding binding) {
        super(context, binding);
        this.dao = DaoUtil.getInstance().getSession().getBookBorrowDao();

        init();
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
        ActivityLibraryBinding libraryBinding = (ActivityLibraryBinding) binding;
        if (libraryBinding.include.recyclerView.getAdapter() != null) {
            libraryBinding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
