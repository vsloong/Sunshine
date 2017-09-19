package cn.edu.zstu.sunshine.tools.network;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.databinding.ActivityNetworkBinding;

public class NetworkActivity extends AppCompatActivity {

    private ActivityNetworkBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_network);
        binding.setViewModel(new NetworkViewModel(this, binding));
    }
}
