/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: RegisterActivity
 * Author: sxt
 * Date: 2018/12/8 1:30 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.RegisterResponseBean;
import com.jieshuizhibiao.waterindex.contract.model.RegisterModel;
import com.jieshuizhibiao.waterindex.contract.presenter.RegisterPresenter;
import com.jieshuizhibiao.waterindex.contract.view.RegisterViewImpl;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import java.util.Map;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: RegisterActivity
 * @Description: java类作用描述
 * @Author: sxt
 * @Date: 2018/12/8 1:30 PM
 */
public class RegisterActivity extends BaseActivity implements RegisterViewImpl {

    @BindView(R.id.title_bar)
    View title_bar;
    @BindView(R.id.left_ll)
    LinearLayout left_ll;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.edt_mobile)
    EditText edt_mobile;
    @BindView(R.id.edt_sms)
    EditText edt_sms;
    @BindView(R.id.tv_get_sms)
    TextView tv_get_sms;
    @BindView(R.id.edt_pwd)
    EditText edt_pwd;
    @BindView(R.id.edt_confirm)
    EditText edt_confirm;
    @BindView(R.id.edt_invitation)
    EditText edt_invitation;
    @BindView(R.id.cb_agreement)
    CheckBox cb_agreement;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.tv_existing)
    TextView tv_existing;

    @BindDrawable(R.drawable.gray_border_bg_shape)
    Drawable edt_border;
    @BindDrawable(R.drawable.red_border_illegal_bg_shape)
    Drawable edt_border_illegal;


    @BindString(R.string.key_edt_name_mobile)
    String keyMobile;
    @BindString(R.string.key_edt_name_pwd)
    String keyPwd;
    @BindString(R.string.key_edt_name_sms)
    String keySms;
    @BindString(R.string.key_checkbox_agreement)
    String keyAgreement;

    private CountDownTimer TimeCount;
    private String mobile;
    private String sms;
    private String pwd;
    private String confirm;
    private String invitation;
    private boolean isChecked = true,runningCode = false;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPresenter = new RegisterPresenter(new RegisterModel());
        registerPresenter.attachView(this);

        StatusBarUtil.setImmersionStatus(this, title_bar);
        initView();
    }

    private void initView() {
        tv_title_center.setText("注册");
        cb_agreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isChecked = isChecked;
            }
        });
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.tv_get_sms, R.id.tv_agreement, R.id.btn_register, R.id.tv_existing, R.id.left_ll})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_get_sms:

                mobile = edt_mobile.getText().toString();
                registerPresenter.verify(mobile);
                if(!Util.isFastDoubleClick()){
                    registerPresenter.getSms(this, mobile);
                }

                break;
//            case R.id.cb_agreement:
//                break;
            case R.id.tv_agreement:
                //todo startAct 2 agreement webView
                break;
            case R.id.btn_register:
                if (isChecked) {
                    mobile = edt_mobile.getText().toString();
                    sms = edt_sms.getText().toString();
                    pwd = edt_pwd.getText().toString();
                    confirm = edt_confirm.getText().toString();
                    invitation = edt_invitation.getText().toString();
                    registerPresenter.verify(mobile, pwd, confirm, sms, invitation, isChecked);
                    registerPresenter.register(this, mobile, pwd, confirm, sms, invitation, isChecked);
                } else {
                    showToast("请阅读并同意《节水指标平台用书协议》");
                }
                break;
            case R.id.tv_existing:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            case R.id.left_ll:
                goBack(view);
                break;
            default:
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEdtContentsLegal() {
        edt_mobile.setBackground(edt_border);
        edt_sms.setBackground(edt_border);
        edt_pwd.setBackground(edt_border);
        edt_confirm.setBackground(edt_border);
        edt_invitation.setBackground(edt_border);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEdtContentsIllegal(Map<String, Boolean> verify) {
        if (verify.get(keyMobile) != null && !verify.get(keyMobile)) {
            edt_mobile.setBackground(edt_border_illegal);
            edt_mobile.setText("");
        } else {
            edt_mobile.setBackground(edt_border);
        }
        if (verify.get(keySms) != null && !verify.get(keySms)) {
            edt_sms.setBackground(edt_border_illegal);
            edt_sms.setText("");
        } else {
            edt_sms.setBackground(edt_border);
        }
        if (verify.get(keyPwd) != null && !verify.get(keyPwd)) {
            edt_pwd.setBackground(edt_border_illegal);
            edt_confirm.setBackground(edt_border_illegal);
            edt_pwd.setText("");
            edt_confirm.setText("");
        } else {
            edt_pwd.setBackground(edt_border);
            edt_confirm.setBackground(edt_border);
        }
        edt_invitation.setBackground(edt_border);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onGetSmsSuccess() {
        ToastUtils.showCustomToast("验证码已发送至您手机，请注意查收",1);

        TimeCount = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                runningCode = true;

                tv_get_sms.setBackground(getDrawable(R.drawable.blue_bg_shape));
                tv_get_sms.setTextColor(getResources().getColor(R.color.white));
                tv_get_sms.setText(String.valueOf(millisUntilFinished / 1000));
                tv_get_sms.setEnabled(false);
            }

            @Override
            public void onFinish() {
                runningCode = false;
                tv_get_sms.setText("发送验证码");
                tv_get_sms.setBackground(getDrawable(R.drawable.blue_border_bg_shape));
                tv_get_sms.setTextColor(getResources().getColor(R.color.primary_yellow));
                tv_get_sms.setEnabled(true);
            }
        };
        if (runningCode) {
            return;
        } else {
            TimeCount.start();
        }


    }

    @Override
    public void onGetSmsFailed(String msg) {
        ToastUtils.showCustomToast(msg,0);
    }

    @Override
    public void onRegisterSuccess(RegisterResponseBean registerResponseBean) {
        //一起操作
        SPUtil.insert(this,SPUtil.IS_LOGIN,true);
        SPUtil.insert(this,SPUtil.TOKEN,registerResponseBean.getToken());
        startActivity(new Intent(RegisterActivity.this, AuthActivity.class));
        finish();
    }

    @Override
    public void onRegisterFaild(String msg) {
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerPresenter != null) {
            registerPresenter.detachView();
        }
    }
}
