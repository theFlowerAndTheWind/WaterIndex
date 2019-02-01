package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.Money;
import com.jieshuizhibiao.waterindex.beans.SysConfigResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserMoney;
import com.jieshuizhibiao.waterindex.beans.request.AddTradeReqParams;
import com.jieshuizhibiao.waterindex.contract.presenter.AddTradePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.CommonSysConfigPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.UserMoneryPresenter;
import com.jieshuizhibiao.waterindex.contract.view.AddTradeViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.SecondRequstViewImpl;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.ui.fragment.TranscationAllFragment;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import java.util.Map;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by songxiaotao on 2019/1/18.
 * Class Note:发布购买需求
 */

public class TranscationReleaseBuyOrSellActivity extends BaseActivity
        implements AddTradeViewImpl, CommonViewImpl, SecondRequstViewImpl {

    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.ll_pay_cate)
    LinearLayout llPayCate;
    @BindView(R.id.iv_bank_state)
    ImageView bankSelect;
    @BindView(R.id.iv_alipay_state)
    ImageView alipaySelect;
    @BindView(R.id.iv_wxpay_state)
    ImageView wxpaySelect;

    @BindView(R.id.edt_transfer_amonut_pwd)
    EditText catitalPass;
    @BindView(R.id.edt_transfer_min)
    EditText transferMin;
    @BindView(R.id.edt_transfer_max)
    EditText transferMax;
    @BindDrawable(R.drawable.gray_border_bg_shape)
    Drawable edt_border;
    @BindDrawable(R.drawable.red_border_illegal_bg_shape)
    Drawable edt_border_illegal;
    @BindString(R.string.key_edt_release_capital_pwd)
    String keySafePwd;
    @BindString(R.string.key_edt_release_time_limit)
    String keyTimeLimit;
    @BindString(R.string.key_edt_release_transaction_max)
    String keyTransactionMax;
    @BindString(R.string.key_edt_release_transaction_min)
    String keyTransactionMin;
    @BindView(R.id.transaction_price)
    TextView transPrice;
    @BindView(R.id.bank_ll)
    LinearLayout bankLl;
    @BindView(R.id.wx_ll)
    LinearLayout wechatLl;
    @BindView(R.id.zfb_ll)
    LinearLayout alipayLl;

    private String max = "";
    private float sellMaxNum = 0;
    private CommonSysConfigPresenter sysConfigPresenter;
    private AddTradePresenter addTradePresenter;
    private UserMoneryPresenter userMoneryPresenter;
    private String action_type;
    private int[] pay_info_list;
    private String edtMaxContent;//交易数量edt内容
    private float edtMaxFloat;//交易数量edt内容  float类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//软键盘不遮挡
        StatusBarUtil.setImmersionStatus(this, titleBar);
        addTradePresenter = new AddTradePresenter();
        addTradePresenter.attachView(this);
        sysConfigPresenter = new CommonSysConfigPresenter();
        sysConfigPresenter.attachView(this);
        userMoneryPresenter = new UserMoneryPresenter();
        userMoneryPresenter.attachView(this);
        getIntentExtra();
        initView();
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        if (intent != null) {
            action_type = intent.getStringExtra(TranscationAllFragment.TYPE);
            pay_info_list = intent.getIntArrayExtra(TranscationAllFragment.EXTRA_PAY_INFO_LIST);
        }
    }

    private void initView() {

        if (action_type.equals(TranscationAllFragment.EXTRA_BUY)) {
            tvTitleCenter.setText("发布购买需求");
            llPayCate.setVisibility(View.GONE);
//            timeLimitFl.setVisibility(View.GONE);
        } else if (action_type.equals(TranscationAllFragment.EXTRA_SELL)) {
            tvTitleCenter.setText("发布出售需求");
//            timeLimitFl.setVisibility(View.GONE);//产品在禅道中要求取消
            for (Integer pay_info : pay_info_list) {
                if (pay_info == 1) {
                    bankLl.setVisibility(View.VISIBLE);
                } else if (pay_info == 2) {
                    alipayLl.setVisibility(View.VISIBLE);
                } else if (pay_info == 3) {
                    wechatLl.setVisibility(View.VISIBLE);
                }
            }
        }

        transferMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {


                    transferMax.setBackground(edt_border);
                    if (action_type.equals(TranscationAllFragment.EXTRA_SELL)) {
                        edtMaxContent = transferMax.getText().toString();
                        if (!TextUtils.isEmpty(edtMaxContent)) {
                            if (edtMaxContent.endsWith(".")) {
                                edtMaxContent = edtMaxContent + "0";
                            }
                            edtMaxFloat = Float.valueOf(edtMaxContent);
                            if (edtMaxFloat > sellMaxNum) {
                                transferMax.setBackground(edt_border_illegal);
                                transferMax.setText("");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    transferMax.setText("");
                }
            }
        });
        transferMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {


                    transferMin.setBackground(edt_border);
                    String edtMinContent = transferMin.getText().toString();
                    if (!TextUtils.isEmpty(edtMinContent)) {
                        if (edtMinContent.endsWith(".")) {
                            edtMinContent = edtMinContent + "0";
                        }
                        float edtMinFloat = Float.valueOf(edtMinContent);
                        if (!TextUtils.isEmpty(edtMaxContent) && edtMaxFloat > 0 && edtMinFloat > edtMaxFloat) {
                            transferMin.setText("");
                            transferMin.setBackground(edt_border_illegal);
                            ToastUtils.showCustomToastMsg("最小交易量不可超过交易数量", 150);
                        } else if (edtMinFloat > sellMaxNum) {
                            transferMin.setText("");
                            transferMin.setBackground(edt_border_illegal);
                            ToastUtils.showCustomToastMsg("最小交易量不可超过个人资产总量", 150);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    transferMin.setText("");
                }
            }
        });
        catitalPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    catitalPass.setBackground(edt_border);
                    if (TextUtils.isEmpty(catitalPass.getText().toString())) {
                        catitalPass.setBackground(edt_border_illegal);
                        ToastUtils.showCustomToastMsg("请输入资金密码", 150);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    catitalPass.setText("");
                }
            }
        });

    }

    @OnClick({R.id.left_ll, R.id.btn_transaction_buy})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.left_ll:
                goBack(view);
                break;
            case R.id.btn_transaction_buy:
                AddTradeReqParams params = new AddTradeReqParams();
                if (action_type.equals(TranscationAllFragment.EXTRA_BUY)) {
                    params.setType(HttpConfig.TRANSCATION_RELEASE_BUY);
                    params.setSafe_pw(catitalPass.getText().toString());
                    params.setPay_min(transferMin.getText().toString());
                    params.setTotal(transferMax.getText().toString());
                } else if (action_type.equals(TranscationAllFragment.EXTRA_SELL)) {
                    params.setType(HttpConfig.TRANSCATION_RELEASE_SELL);
                    params.setSafe_pw(catitalPass.getText().toString());
                    params.setPay_min(transferMin.getText().toString());
                    params.setTotal(transferMax.getText().toString());
                }
//                if (alipaySelect.getVisibility() != View.VISIBLE && wxpaySelect.getVisibility() != View.VISIBLE && bankSelect.getVisibility() != View.VISIBLE) {
//                    ToastUtils.showCustomToast("抱歉发布需求前，请先添加至少一种收款方式！", 0);
//                    return;
//                }
                addTradePresenter.vertify(params, action_type);
                addTradePresenter.addTrade(TranscationReleaseBuyOrSellActivity.this, params, action_type);
                break;
            default:
                break;
        }
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_transaction_release_buy);
    }

    public void doReuqestPrice() {
        sysConfigPresenter.getSysConfig(this);
    }

    public void getUserMoney() {
        if (userMoneryPresenter == null) {
            userMoneryPresenter = new UserMoneryPresenter();
            userMoneryPresenter.attachView(this);
        }
        userMoneryPresenter.userMonery(this);
    }


    @Override
    public void onReNetRefreshData(int viewId) {
        doReuqestPrice();
        getUserMoney();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doReuqestPrice();
        getUserMoney();
    }

    @Override
    public void onEdtContentsLegal() {
        catitalPass.setBackground(edt_border);
//        timeLimit.setBackground(edt_border);
        transferMin.setBackground(edt_border);
        transferMax.setBackground(edt_border);
        showLoadingDialog();
    }

    @Override
    public void onEdtContentsIllegalSell(Map<String, Boolean> verify) {
        if (!Util.isEmpty(verify.get(keySafePwd)) && !verify.get(keySafePwd)) {
            catitalPass.setBackground(edt_border_illegal);
            catitalPass.setText("");
        } else {
            catitalPass.setBackground(edt_border);
        }
//        if (!Util.isEmpty(verify.get(keyTimeLimit)) && !verify.get(keyTimeLimit)){
//            timeLimit.setBackground(edt_border_illegal);
//            timeLimit.setText("");
//        } else {
//            timeLimit.setBackground(edt_border);
//        }
        if (!Util.isEmpty(verify.get(keyTransactionMin)) && !verify.get(keyTransactionMin)) {
            transferMin.setBackground(edt_border_illegal);
            transferMin.setText("");
        } else {
            transferMin.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyTransactionMax)) && !verify.get(keyTransactionMax)) {
            transferMax.setBackground(edt_border_illegal);
            transferMax.setText("");
        } else {
            transferMax.setBackground(edt_border);
        }
    }

    @Override
    public void onEdtContentsIllegalBuy(Map<String, Boolean> verify) {
        if (!Util.isEmpty(verify.get(keySafePwd)) && !verify.get(keySafePwd)) {
            catitalPass.setBackground(edt_border_illegal);
            catitalPass.setText("");
        } else {
            catitalPass.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyTransactionMin)) && !verify.get(keyTransactionMin)) {
            transferMin.setBackground(edt_border_illegal);
            transferMin.setText("");
        } else {
            transferMin.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyTransactionMax)) && !verify.get(keyTransactionMax)) {
            transferMax.setBackground(edt_border_illegal);
            transferMax.setText("");
        } else {
            transferMax.setBackground(edt_border);
        }
    }

    @Override
    public void onAddTradeSuccess() {
        dismissLoadingDialog();
        ToastUtils.showCustomToast("发布成功", 1);
        finish();
    }

    @Override
    public void onAddTradeFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg, 0);
    }

    @Override
    public void onRequestSuccess(Object bean) {
        if (bean instanceof SysConfigResponseBean) {
            String price = ((SysConfigResponseBean) bean).getPrice();
            price = String.format("%.2f元/T", Float.valueOf(price));
            transPrice.setText(price);
        }
    }

    @Override
    public void onRequestFailed(String msg) {
        ToastUtils.showCustomToast(msg, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addTradePresenter != null) {
            addTradePresenter.detachView();
        }
        if (sysConfigPresenter != null) {
            sysConfigPresenter.detachView();
        }
    }

    //个人资产
    @Override
    public void onSecondRequstSuccess(Object bean) {
        if (bean != null) {
            String txt = "交易数量 最大交易数量   ";
            try {
                UserMoney userMoney = (UserMoney) bean;
                Money c2c = userMoney.getC2c();
                max = c2c.getDs();
                sellMaxNum = Float.valueOf(max.replace("T", "").trim());
                if (action_type.equals(TranscationAllFragment.EXTRA_SELL)) {
                    txt = new StringBuilder(txt).append(sellMaxNum).toString();
                }
                transferMax.setHint(txt);//最大交易量即个人资产可用数量
                transferMin.setHint("最小交易量");
            } catch (Exception e) {
                e.printStackTrace();
                transferMax.setHint(txt);
                transferMin.setHint("最小交易量");
            }

        }
    }

    @Override
    public void onSecondRequstFailed(String msg) {

    }
}
