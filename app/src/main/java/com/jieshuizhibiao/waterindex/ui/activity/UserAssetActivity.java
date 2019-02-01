package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.Money;
import com.jieshuizhibiao.waterindex.beans.UserMoney;
import com.jieshuizhibiao.waterindex.contract.presenter.UserMoneryPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.SecondRequstViewImpl;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: fushizhe
 */
public class UserAssetActivity extends BaseActivity implements SecondRequstViewImpl {

    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.title_bar)
    View title_bar;
    @BindView(R.id.tv_jsl)
    TextView tvJsl;
    @BindView(R.id.tv_jsl_freeze)
    TextView tvJslFreeze;
    @BindView(R.id.tv_ds)
    TextView tvDs;
    @BindView(R.id.tv_ds_freeze)
    TextView tvDsFreeze;
    @BindView(R.id.total_all_monery_bb)
    TextView tvDsTransferTotalBb;
    @BindView(R.id.total_all_monery_c2c)
    TextView tvDsTransferTotalC2c;

    private UserMoneryPresenter userMoneryPresenter;
    private Money moneryC2c = new Money();
    private Money moneryBb = new Money();

    //by sxt
    private String c2cDs;//节水指标账户总资产
    private String bbDs;//数字节水账户总资产
    public static final String TRANS_TYPE="trans_type";
    public static final String C2C_DS="C2C_ds";
    public static final String BB_DS="bb_ds";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userMoneryPresenter = new UserMoneryPresenter();
        userMoneryPresenter.attachView(this);

        StatusBarUtil.setImmersionStatus(this, title_bar);
        initView();
    }

    private void initView() {
        tv_title_center.setText("我的资产");
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_asset);
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doRequest();
    }

    public void doRequest(){
        if (userMoneryPresenter != null) {
            userMoneryPresenter.userMonery(this);
            showLoadingDialog();
        }
    }
    @OnClick({R.id.left_ll, R.id.img_title_left, R.id.btn_jsl_transfer, R.id.btn_c2c_transfer})
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.img_title_left:
                goBack(view);

                break;
            case R.id.left_ll:
                goBack(view);
                break;
            case R.id.btn_c2c_transfer:
                bundle.putString(C2C_DS,c2cDs);
                bundle.putString(BB_DS,bbDs);
                bundle.putString(TRANS_TYPE,"2");
                jumpActivity(bundle,intent);
                break;
            case R.id.btn_jsl_transfer:
                bundle.putString(C2C_DS,c2cDs);
                bundle.putString(BB_DS,bbDs);
                bundle.putString(TRANS_TYPE,"1");
                jumpActivity(bundle,intent);
                break;
            default:
                break;
        }

    }

    public void jumpActivity(Bundle bundle,Intent intent){
        intent.putExtras(bundle);
        intent.setClass(UserAssetActivity.this,TransferActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSecondRequstSuccess(Object bean) {
        dismissLoadingDialog();
        if(bean instanceof UserMoney){
            moneryBb = ((UserMoney) bean).getBb();
            moneryC2c = ((UserMoney) bean).getC2c();
        }
        String c2cRmbStr=TraderUnpayActivity.formatRmb(moneryC2c.getRmb(),"元");
        c2cDs =moneryC2c.getDs();
        tvDsTransferTotalC2c.setText(new StringBuilder("总资产折合:").append(c2cDs).append(" ≈ ").append(c2cRmbStr).toString());
        tvJsl.setText(new StringBuilder("可用：").append(moneryC2c.getDs()).toString());
        tvJslFreeze.setText(new StringBuilder("冻结：").append(moneryC2c.getDs_freeze()).toString());

        String bbRmbStr = TraderUnpayActivity.formatRmb(moneryBb.getRmb(),"元");
        bbDs=moneryBb.getDs();
        tvDsTransferTotalBb.setText(new StringBuilder("总资产折合:").append(bbDs).append(" ≈ ").append(bbRmbStr).toString());
        tvDs.setText(new StringBuilder("可用：").append(moneryBb.getDs()).toString());
        tvDsFreeze.setText(new StringBuilder("冻结：").append(moneryBb.getDs_freeze()).toString());
    }

    @Override
    public void onSecondRequstFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);
    }
}
