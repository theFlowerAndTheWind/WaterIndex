package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.PayMentResponseBean;
import com.jieshuizhibiao.waterindex.beans.request.PayMentTypeSwitchReqParams;
import com.jieshuizhibiao.waterindex.contract.presenter.PayMentTypePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.PayMentTypeSwitchPresenter;
import com.jieshuizhibiao.waterindex.contract.view.PayMentTypeSwitchViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.PayMentTypeViewImpl;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:收款方式
 */

public class PaymentTypeActivity extends BaseActivity implements PayMentTypeSwitchViewImpl,PayMentTypeViewImpl {

    @BindView(R.id.title_bar)
    View title_bar;
    @BindView(R.id.left_ll)
    LinearLayout left_ll;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.payment_switch_zfb_img)
    ImageView zfbSwitchImg;
    @BindView(R.id.payment_switch_wx_img)
    ImageView wxSwitchImg;
    @BindView(R.id.payment_switch_bank_img)
    ImageView bankSwitchImg;
    @BindView(R.id.tv_alipay_state)
    TextView tvAliPayState;
    @BindView(R.id.tv_wxpay_state)
    TextView tvWxpayState;
    @BindView(R.id.tv_bank_state)
    TextView tvBankState;

    @BindView(R.id.zfh_account)
    TextView tvZfhAccount;
    @BindView(R.id.wx_account)
    TextView tvWxAccount;
    @BindView(R.id.bank_account)
    TextView tvBankAccount;

    @BindView(R.id.paytype_zfb_text)
    TextView tvZfbTitle;
    @BindView(R.id.paytype_wx_text)
    TextView tvWxTitle;
    @BindView(R.id.paytype_bank_text)
    TextView tvBankTitle;
    private PayMentTypeSwitchPresenter payMentTypeSwitchPresenter;
    private PayMentTypePresenter payMentTypePresenter;
    private PayMentResponseBean.TypeList bankCardLists;
    private PayMentResponseBean.TypeList aliPayLists;
    private PayMentResponseBean.TypeList wxPayLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, title_bar);
        payMentTypeSwitchPresenter = new PayMentTypeSwitchPresenter();
        payMentTypeSwitchPresenter.attachView(this);
        payMentTypePresenter = new PayMentTypePresenter();
        payMentTypePresenter.attachView(this);
        initView();
    }

    private void initView() {
        tv_title_center.setText("收款方式");
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_payment_type);
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doRequestPayType();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doRequestPayType();
    }

    @OnClick({R.id.left_ll,R.id.zfb_ll,R.id.wx_ll,R.id.bank_ll,R.id.payment_switch_zfb_img,
            R.id.payment_switch_wx_img,R.id.payment_switch_bank_img})
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        int status = -1;
        switch (v.getId()) {
            case R.id.left_ll:
                goBack(v);
                finish();
                break;
            case R.id.zfb_ll:
                if (!Util.isFastDoubleClick()) {
                    bundle.putString("payType", HttpConfig.ZFB_TYPE);
                    putBundleState(bundle,tvAliPayState,aliPayLists);
                    jumpActivity(bundle,WxOrZfbPaymetActivity.class);
                }
                break;
            case R.id.wx_ll:
                if (!Util.isFastDoubleClick()) {
                    bundle.putString("payType", HttpConfig.WX_TYPE);
                    putBundleState(bundle,tvWxpayState,wxPayLists);
                    jumpActivity(bundle,WxOrZfbPaymetActivity.class);
                }
                break;
            case R.id.bank_ll:
                if (!Util.isFastDoubleClick()) {
                    putBundleState(bundle,tvBankState,bankCardLists);
                    jumpActivity(bundle,BankPaymetActivity.class);
                }
                break;
            case R.id.payment_switch_zfb_img:
                if (!Util.isFastDoubleClick()) {
                    if ((Boolean) SPUtil.get(this,"isOpenZFB",false)) {
                        zfbSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_off));
                        SPUtil.insert(this,"isOpenZFB",false);
                        status = 0;
                    }else {
                        zfbSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_on));
                        SPUtil.insert(this,"isOpenZFB",true);
                        status = 1;
                    }
                    doRequestSwitch(String.valueOf(aliPayLists.getId()),status);
                }
                break;
            case R.id.payment_switch_wx_img:
                if (!Util.isFastDoubleClick()) {
                    if ((Boolean) SPUtil.get(this,"isOpenWX",false)) {
                        wxSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_off));
                        SPUtil.insert(this,"isOpenWX",false);
                        status = 0;
                    }else {
                        wxSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_on));
                        SPUtil.insert(this,"isOpenWX",true);
                        status = 1;
                    }
                    doRequestSwitch(String.valueOf(wxPayLists.getId()),status);
                }
                break;
            case R.id.payment_switch_bank_img:
                if (!Util.isFastDoubleClick()) {
                    if ((Boolean) SPUtil.get(this,"isOpenBANK",false)) {
                        bankSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_off));
                        SPUtil.insert(this,"isOpenBANK",false);
                        status = 0;
                    }else {
                        bankSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_on));
                        SPUtil.insert(this,"isOpenBANK",true);
                        status = 1;
                    }
                    doRequestSwitch(String.valueOf(bankCardLists.getId()),status);
                }
                break;
            default:break;
        }
    }

    public void doRequestPayType(){
        payMentTypePresenter.payMentType(PaymentTypeActivity.this);
        showLoadingDialog();
    }

    public void doRequestSwitch(String id,int status){
        PayMentTypeSwitchReqParams params = new PayMentTypeSwitchReqParams();
        params.setId(id);
        params.setStatus(status);
        payMentTypeSwitchPresenter.setPayMentTypeSwitch(PaymentTypeActivity.this,params);
        showLoadingDialog();
    }

    public void jumpActivity(Class<?> cla){
        startActivity(new Intent(PaymentTypeActivity.this,cla));
    }

    public void jumpActivity(Bundle bundle,Class<?> cla){
        Intent intent = new Intent();
        intent.setClass(PaymentTypeActivity.this,cla);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 判断此支付方式是否已添加过
     */
    public void putBundleState(Bundle bundle,TextView tvState,PayMentResponseBean.TypeList typeList){
        if (!TextUtils.isEmpty(tvState.getText().toString()) && tvState.getText().equals("添加")){
            bundle.putBoolean("isHasAdd",false);
        }else {
            bundle.putBoolean("isHasAdd",true);
        }
        bundle.putParcelable("TypeList",typeList);
    }
    @Override
    public void onPaymentTypeSwitchSuccess() {
        dismissLoadingDialog();
        ToastUtils.showCustomToast("修改成功",1);
    }

    @Override
    public void onPaymentTypeSwitcFailed(String msg) {
        //失败时 状态要回归原来的状态
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);
    }

    @Override
    public void onPayMentTypeSuccess(PayMentResponseBean payMentResponseBean) {
        dismissLoadingDialog();
        bankCardLists = payMentResponseBean.bank;//此方式使用了注解直接获取的解析后的对象
        aliPayLists = payMentResponseBean.getAlipay();//此方式为传统get set方式
        wxPayLists = payMentResponseBean.getWechat();

        tvAliPayState.setText(aliPayLists.getLink_text());
        tvWxpayState.setText(wxPayLists.getLink_text());
        tvBankState.setText(bankCardLists.getLink_text());

        tvZfhAccount.setText(TextUtils.isEmpty(aliPayLists.getAccount_name()) ? "支付宝": aliPayLists.getAccount_name());
        tvWxAccount.setText(TextUtils.isEmpty(wxPayLists.getAccount_name()) ? "微信" :wxPayLists.getAccount_name());
        tvBankAccount.setText(TextUtils.isEmpty(bankCardLists.getAccount_name()) ? "银行" :bankCardLists.getAccount_name());

        tvZfbTitle.setText(TextUtils.isEmpty(aliPayLists.getType_text()) ? "未知" :aliPayLists.getType_text());
        tvWxTitle.setText(TextUtils.isEmpty(wxPayLists.getType_text()) ? "未知" :wxPayLists.getType_text());
        tvBankTitle.setText(TextUtils.isEmpty(bankCardLists.getType_text()) ? "未知":bankCardLists.getType_text());

        if (aliPayLists.getIsopen() == 1) {
            zfbSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_on));
        }else {
            zfbSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_off));
        }
        if (wxPayLists.getIsopen() == 1) {
            wxSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_on));
        }else {
            wxSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_off));
        }
        if (bankCardLists.getIsopen() == 1) {
            bankSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_on));
        }else {
            bankSwitchImg.setBackground(getResources().getDrawable(R.drawable.switch_off));
        }

    }

    @Override
    public void onPayMentTypeFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (payMentTypeSwitchPresenter!=null){
            payMentTypeSwitchPresenter.detachView();
        }
        if (payMentTypePresenter !=null){
            payMentTypePresenter.detachView();
        }
    }
}
