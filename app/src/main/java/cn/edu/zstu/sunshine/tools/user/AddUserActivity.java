package cn.edu.zstu.sunshine.tools.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseActivity;
import cn.edu.zstu.sunshine.databinding.ActivityAddStudentBinding;
import cn.edu.zstu.sunshine.tools.main.MainActivity;
import cn.edu.zstu.sunshine.utils.ToastUtil;

public class AddUserActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ActivityAddStudentBinding binding;
    private AddUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_student);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_student);
        viewModel = new AddUserViewModel(this, binding);
        binding.setViewModel(viewModel);

        onTouchCard();
    }

    private void onTouchCard() {
        binding.editCardId.setOnClickListener(this);
        binding.editCardHolder.setOnClickListener(this);
        binding.editCardId.addTextChangedListener(this);

        binding.imgHelp.setOnClickListener(this);
        binding.btnClose.setOnClickListener(this);
        binding.btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_card_holder:
            case R.id.edit_card_id:
                binding.layoutBgCard.setBackgroundResource(R.drawable.shape_gradient_blue);
                break;
            case R.id.img_help:
                ToastUtil.showShortToast(R.string.toast_tip_enrollment_time);
                break;
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_done:
                if (isInputDone()) {
                    if (viewModel.isAddUserDone()) {
                        startActivity(MainActivity.class, true);
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean isInputDone() {
        if (binding.editCardId.getText().toString().trim().length() < 1
                || binding.editCardHolder.getText().toString().trim().length() < 1) {
            ToastUtil.showShortToast(R.string.toast_input_not_done);
            return false;
        }
        return true;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() < 5) {
            binding.labelEnrollmentTime.setText(String.format(getResources().getString(R.string.enrollment_time), charSequence.toString()));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
