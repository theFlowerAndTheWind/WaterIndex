package com.jieshuizhibiao.waterindex.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.model.ChangePassModel;
import com.jieshuizhibiao.waterindex.contract.presenter.ChangePassPresenter;
import com.jieshuizhibiao.waterindex.contract.view.ChangePassViewImpl;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import java.util.Map;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class ChangePassActivity extends BaseActivity implements ChangePassViewImpl {

    @BindView(R.id.title_bar)
    View titleBar;
    @BindView(R.id.left_ll)
    LinearLayout left_ll;
    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.edt_old_pass)
    EditText edtOldPass;
    @BindView(R.id.edt_new_pass)
    EditText edtNewPass;
    @BindView(R.id.edt_confirm_pass)
    EditText edtNewConfirmPass;
    @BindDrawable(R.drawable.gray_border_bg_shape)
    Drawable edt_border;
    @BindDrawable(R.drawable.red_border_illegal_bg_shape)
    Drawable edt_border_illegal;
    @BindString(R.string.key_edt_change_new_pwd)
    String keyNewPass;
    @BindString(R.string.key_edt_change_old_pwd)
    String keyNewConfirmPass;

    private ChangePassPresenter changePassPresenter;

    @OnClick({R.id.left_ll,R.id.btn_find})
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.left_ll:
                goBack(v);
                finish();
                break;

            case R.id.btn_find:
                changePassPresenter.vertify(edtOldPass.getText().toString(),edtNewPass.getText().toString(),edtNewConfirmPass.getText().toString());
                changePassPresenter.changePass(this,edtOldPass.getText().toString(), edtNewPass.getText().toString());
                break;
            default:break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changePassPresenter=new ChangePassPresenter(new ChangePassModel());
        changePassPresenter.attachView(this);

        StatusBarUtil.setImmersionStatus(this, titleBar);
        initView();
    }

    private void initView() {
        tvTitleCenter.setText("修改密码");
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_change_pass);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @Override
    public void onEdtContentsLegal() {
        edtOldPass.setBackground(edt_border);
        edtNewPass.setBackground(edt_border);
        edtNewConfirmPass.setBackground(edt_border);
        showLoadingDialog();
    }

    @Override
    public void onEdtContentsIllegal(Map<String, Boolean> verify) {
        if (!Util.isEmpty(verify.get(keyNewPass)) && !verify.get(keyNewPass)) {
            edtNewPass.setBackground(edt_border_illegal);
            edtNewConfirmPass.setBackground(edt_border_illegal);
            edtNewPass.setText("");
            edtNewConfirmPass.setText("");
        } else {
            edtNewPass.setBackground(edt_border);
            edtNewConfirmPass.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyNewConfirmPass)) && !verify.get(keyNewConfirmPass)){
            edtOldPass.setBackground(edt_border_illegal);
            edtOldPass.setText("");
        } else {
            edtOldPass.setBackground(edt_border);
        }
    }

    @Override
    public void changePassSuccess() {
        dismissLoadingDialog();
        ToastUtils.showCustomToast("修改成功！",1);
        finish();
    }

    @Override
    public void changePassFiled(String err) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(err,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        changePassPresenter.detachView();
    }
}
