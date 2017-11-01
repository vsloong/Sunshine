package cn.edu.zstu.sunshine.tools.library;

import android.content.Context;
import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.databinding.ActivityLibraryBinding;
import cn.edu.zstu.sunshine.entity.BookBorrow;
import cn.edu.zstu.sunshine.greendao.BookBorrowDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 图书馆的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:22.
 */

public class LibraryViewModel {

    private Context context;
    private ActivityLibraryBinding binding;

    private List<BookBorrow> data = new ArrayList<>();
    private BookBorrowDao dao;
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    public List<BookBorrow> getData() {
        return data;
    }

    public void setData(List<BookBorrow> books) {
        data.clear();
        data.addAll(books);
    }

    public LibraryViewModel(Context context, ActivityLibraryBinding binding) {
        this.context = context;
        this.binding = binding;

        dao = DaoUtil.getInstance().getSession().getBookBorrowDao();
        init();
    }

    public void init() {
        loadDataFromLocal();
        loadDataIntoView();
    }

    private void loadDataFromLocal() {
        List<BookBorrow> books = dao
                .queryBuilder()
                .where(
                        BookBorrowDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                )
                .list();

        setData(books);
    }

    private void loadDataIntoView() {
        showEmptyView.set(data.size() <= 0);

        if (binding.include.recyclerView.getAdapter() != null) {
            binding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
