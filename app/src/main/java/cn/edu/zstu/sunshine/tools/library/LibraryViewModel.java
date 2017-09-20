package cn.edu.zstu.sunshine.tools.library;

import android.content.Context;

import cn.edu.zstu.sunshine.databinding.ActivityLibraryBinding;

/**
 * 图书馆的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:22.
 */

public class LibraryViewModel {

    private Context context;
    private ActivityLibraryBinding binding;

    public LibraryViewModel(Context context, ActivityLibraryBinding binding) {
        this.context = context;
        this.binding = binding;
    }
}
