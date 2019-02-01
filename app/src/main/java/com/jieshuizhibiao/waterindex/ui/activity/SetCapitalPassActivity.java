package com.jieshuizhibiao.waterindex.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.ChangeCapitalPassReqParams;
import com.jieshuizhibiao.waterindex.beans.request.PersonalAuthReqParams;
import com.jieshuizhibiao.waterindex.beans.request.SetCaptialPassReqParams;
import com.jieshuizhibiao.waterindex.contract.presenter.ChangeCapitalPassPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.SetCapitalPassPresenter;
import com.jieshuizhibiao.waterindex.contract.view.ChangeCapitalPassViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.SetCapitalPassViewImpl;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import java.util.Map;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置资金密码(修改/设置)
 */
public class SetCapitalPassActivity extends BaseActivity implements SetCapitalPassViewImpl,ChangeCapitalPassViewImpl {

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
    EditText edtConfirmPass;
    @BindView(R.id.edt_pay_nike)
    EditText edtPayNike;
    @BindView(R.id.btn_find)
    Button btnAuth;
    @BindDrawable(R.drawable.gray_border_bg_shape)
    Drawable edt_border;
    @BindDrawable(R.drawable.red_border_illegal_bg_shape)
    Drawable edt_border_illegal;
    @BindString(R.string.key_edt_name_pwd)
    String keySafePass;
    @BindString(R.string.key_edt_name_nike_name)
    String keyNickName;
    @BindString(R.string.key_edt_capital_pwd)
    String keyPass;
    @BindString(R.string.key_edt_capital_old_pwd)
    String keyOldPass;

    private SetCaptialPassReqParams setCaptialPassReqParams;
    private ChangeCapitalPassReqParams changeCapitalPassReqParams;
    private SetCapitalPassPresenter setCapitalPassPresenter;
    private ChangeCapitalPassPresenter changeCapitalPassPresenter;
    @OnClick({R.id.left_ll,R.id.btn_find})
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.left_ll:
                goBack(v);
                finish();
                break;

            case R.id.btn_find:
                if(getIntent().getStringExtra("action").equals("SafeCenterActivity")){
                    changeCapitalPassReqParams.setOld_safe_pw(edtOldPass.getText().toString());
                    changeCapitalPassReqParams.setSafe_pw(edtNewPass.getText().toString());
                    changeCapitalPassReqParams.setSafe_pw_re(edtConfirmPass.getText().toString());
                    changeCapitalPassPresenter.verfity(changeCapitalPassReqParams);
                    changeCapitalPassPresenter.changeCapitalPass(this,changeCapitalPassReqParams);

                }else if(getIntent().getStringExtra("action").equals("AuthActivity")){
                    setCaptialPassReqParams.setNick_name(edtPayNike.getText().toString());
                    setCaptialPassReqParams.setSafe_pw(edtNewPass.getText().toString());
                    setCaptialPassReqParams.setSafe_pw_re(edtConfirmPass.getText().toString());
                    setCapitalPassPresenter.verfity(setCaptialPassReqParams);
                    setCapitalPassPresenter.SetCapitalPass(this,setCaptialPassReqParams);
                }


                break;
            default:break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, titleBar);
        setCapitalPassPresenter=new SetCapitalPassPresenter();
        setCapitalPassPresenter.attachView(this);
        changeCapitalPassPresenter = new ChangeCapitalPassPresenter();
        changeCapitalPassPresenter.attachView(this);
        initData();
    }


    /**
     * 修改密码-设置资金密码-修改资金密码 使用的同一xml
     */
    private void initData() {
        if (getIntent()!=null && !TextUtils.isEmpty(getIntent().getStringExtra("action"))){
            if(getIntent().getStringExtra("action").equals("SafeCenterActivity")){
                tvTitleCenter.setText("修改资金密码");
                edtOldPass.setVisibility(View.VISIBLE);
                edtOldPass.setHint(R.string.hint_input_capital_old_pwd);
                edtNewPass.setHint(R.string.hint_input_capital_new_pwd);
                edtConfirmPass.setHint(R.string.hint_input_capital_confirm_pwd);
                btnAuth.setText("完成");
            }else if(getIntent().getStringExtra("action").equals("AuthActivity")){
                tvTitleCenter.setText("设置资金密码");
                edtNewPass.setHint(R.string.hint_input_capital_pwd);
                edtConfirmPass.setHint(R.string.hint_input_capital_confirm_pwd);

                edtOldPass.setVisibility(View.GONE);
                edtPayNike.setVisibility(View.VISIBLE);
                btnAuth.setText("提交认证");
            }
            setCaptialPassReqParams = new SetCaptialPassReqParams();
            changeCapitalPassReqParams = new ChangeCapitalPassReqParams();
            if (!Util.isEmpty(getIntent().getParcelableExtra("PersonalAuthReqParams"))){
                PersonalAuthReqParams params = getIntent().getParcelableExtra("PersonalAuthReqParams");
                setCaptialPassReqParams.setCity(params.getCity());
                setCaptialPassReqParams.setProvince(params.getProvince());
                setCaptialPassReqParams.setUser_name(params.getUser_name());
                setCaptialPassReqParams.setId_no(params.getId_no());
                setCaptialPassReqParams.setId_img_a(params.getId_img_a());
                setCaptialPassReqParams.setId_img_b(params.getId_img_b());
            }

        }


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
        edtPayNike.setBackground(edt_border);
        edtNewPass.setBackground(edt_border);
        edtConfirmPass.setBackground(edt_border);
        showLoadingDialog();
    }

    @Override
    public void onEdtContentsIllegal(Map<String, Boolean> verify) {
        if (!Util.isEmpty(verify.get(keySafePass)) && !verify.get(keySafePass)) {
            edtNewPass.setBackground(edt_border_illegal);
            edtConfirmPass.setBackground(edt_border_illegal);
            edtNewPass.setText("");
            edtConfirmPass.setText("");
        } else {
            edtNewPass.setBackground(edt_border);
            edtConfirmPass.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyNickName)) && !verify.get(keyNickName)){
            edtPayNike.setBackground(edt_border_illegal);
            edtPayNike.setText("");
        } else {
            edtPayNike.setBackground(edt_border);
        }
    }

    @Override
    public void onSetCapitalPassSuccess() {
        dismissLoadingDialog();
        ToastUtils.showCustomToast("设置成功",1);
        finish();
    }

    @Override
    public void onSetCapitalPassFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setCapitalPassPresenter.detachView();
    }


    @Override
    public void onEdtContentsLegalChange() {
        edtOldPass.setBackground(edt_border);
        edtNewPass.setBackground(edt_border);
        edtConfirmPass.setBackground(edt_border);
        showLoadingDialog();
    }

    @Override
    public void onEdtContentsIllegalChange(Map<String, Boolean> verify) {
        if (!Util.isEmpty(verify.get(keyPass)) && !verify.get(keyPass)) {
            edtNewPass.setBackground(edt_border_illegal);
            edtConfirmPass.setBackground(edt_border_illegal);
            edtNewPass.setText("");
            edtConfirmPass.setText("");
        }else {
            edtNewPass.setBackground(edt_border);
            edtConfirmPass.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyOldPass)) && !verify.get(keyOldPass)){
            edtOldPass.setBackground(edt_border_illegal);
            edtOldPass.setText("");
        } else {
            edtOldPass.setBackground(edt_border);
        }
    }

    @Override
    public void onChangeCapitalPassSuccess() {
        dismissLoadingDialog();
        ToastUtils.showCustomToast("修改成功",1);
        finish();
    }

    @Override
    public void onChangeCapitalPassFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);
    }
}
