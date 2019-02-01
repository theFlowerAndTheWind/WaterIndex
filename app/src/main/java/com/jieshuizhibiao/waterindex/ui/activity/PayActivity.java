package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.unpay.BaseUnpayOrderInfo;
import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;
import com.jieshuizhibiao.waterindex.contract.model.CancelOrderModel;
import com.jieshuizhibiao.waterindex.contract.presenter.CancelOrderPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CancelOrderViewImpl;
import com.jieshuizhibiao.waterindex.event.ChangeOrderStatusEvent;
import com.jieshuizhibiao.waterindex.event.UserDoPayEvent;
import com.jieshuizhibiao.waterindex.ui.fragment.OrderListsTabFragment;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.TimeUtils;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * 买家身份单独使用
 * 卖家身份无对应步骤
 */
public class PayActivity extends BaseActivity implements CancelOrderViewImpl {

    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.tv_big_rmb)
    TextView tvBigRmb;
    @BindView(R.id.tv_buyer_paid)
    TextView tvBuyerPaid;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_time_clock)
    TextView tvTimeClock;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_time_clock)
    LinearLayout llTimeClock;
    @BindView(R.id.img_pay_type_bank_card)
    ImageView imgPayTypeBankCard;
    @BindView(R.id.img_pay_type_alipay)
    ImageView imgPayTypeAlipay;
    @BindView(R.id.img_pay_type_wechat)
    ImageView imgPayTypeWechat;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_user_name_txt)
    TextView tvUserNameTxt;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_account_name_txt)
    TextView tvAccountNameTxt;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.ll_qrcode)
    LinearLayout llQrcode;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_detail_name)
    TextView tvBankDetailName;
    @BindView(R.id.ll_bank_info)
    LinearLayout llBankInfo;
    @BindView(R.id.tv_pay_code)
    TextView tvPayCode;
    @BindView(R.id.tv_createtime)
    TextView tvCreatetime;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;


    private BaseUnpayOrderInfo baseUnpayOrderInfo;
    private PayInfo payInfo;
    private String lastStep;
    private int pay_type;
    private String expire_time;
    private CountDownTimer TimeCount;
    private boolean isChecked = false;
    private long order_id;
    private String pay_code;
    private String createtime;
    private String qrcodeUrl;
    private String rmb;
    private String account_name;
    private CancelOrderPresenter cancelOrderPresenter;
    private long pi_id;
    private boolean isTimeout = false;//倒计时结束，订单取消，不能继续操作


    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder=ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        StatusBarUtil.setImmersionStatus(this, titleBar);
        getIntntExtra();
        initView();

        cancelOrderPresenter = new CancelOrderPresenter(new CancelOrderModel());
        cancelOrderPresenter.attachView(this);
    }


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_pay);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @Override
    public void onCancelSucc(Object bean) {
        ToastUtils.showSuccessToast("取消成功");
        EventBus.getDefault().post(new ChangeOrderStatusEvent("PayActivity", "buyer_cancel"));
    }

    @Override
    public void onCancelFail(String msg) {
        ToastUtils.showCustomToast(msg,0);
    }

    private void getIntntExtra() {
        Intent intent = getIntent();
        if (intent != null) {
            baseUnpayOrderInfo = intent.getParcelableExtra(TraderUnpayActivity.ORDERINFO);
            payInfo = intent.getParcelableExtra(TraderUnpayActivity.PAYINFO);
            lastStep = intent.getStringExtra(TraderUnpayActivity.CURRENT_STEP);
            pay_type = intent.getIntExtra(TraderUnpayActivity.PAY_TYPE, 0);
            expire_time = intent.getStringExtra(TraderUnpayActivity.MILLIS);

            if (baseUnpayOrderInfo != null) {
                order_id = Long.valueOf(baseUnpayOrderInfo.getOrder_id());
                pay_code = baseUnpayOrderInfo.getPay_code();
                createtime = baseUnpayOrderInfo.getCreatetime();
                rmb = baseUnpayOrderInfo.getRmb();
            }
            if (payInfo != null) {
                qrcodeUrl = payInfo.getQrcode();
                account_name = payInfo.getAccount_name();
                pi_id = payInfo.getId();
            }
        }
    }

    private void initView() {
        if (lastStep.equals(OrderListsTabFragment.BUYER_UNPAY)) {
            tvTitleCenter.setText("支付");
        }/*else if(lastStep.equals(TraderUnpayActivity.SELLER_UNPAY)){
            tvTitleCenter.setText("收款");
        }else {
            tvTitleCenter.setText("支付及收款情况");
        }*/
        rmb=TraderUnpayActivity.formatRmb(rmb,"元");
        tvBigRmb.setText(rmb);
        if (TraderUnpayActivity.isNumeric(expire_time)) {
            long ms = Long.valueOf(expire_time);
            if (ms > 1000) {
                countDownPayTime(ms);
            }
        } else if ("不限制，请尽快付款".contains(expire_time)) {
            tvLeft.setVisibility(View.VISIBLE);
            tvLeft.setText("付款时限：");
            tvRight.setVisibility(View.GONE);
            tvTimeClock.setText(expire_time);
            tvTimeClock.setTextColor(getResources().getColor(R.color.text_black));
        }
        tvTimeClock.setVisibility(View.VISIBLE);
        if (lastStep.equals(OrderListsTabFragment.BUYER_UNPAY)) {
            tvBuyerPaid.setVisibility(View.GONE);
            llTimeClock.setVisibility(View.VISIBLE);
            tvUserNameTxt.setText("收款人");
        } /*else if (lastStep.equals(TraderUnpayActivity.SELLER_UNPAY)) {
            tvBuyerPaid.setVisibility(View.VISIBLE);
            llTimeClock.setVisibility(View.GONE);
            tvUserNameTxt.setText("收款人");//???
        }*/
        if (pay_type == 1) {
            imgPayTypeBankCard.setVisibility(View.VISIBLE);
            imgPayTypeAlipay.setVisibility(View.GONE);
            imgPayTypeWechat.setVisibility(View.GONE);
            tvPayType.setText("银行卡");
            tvAccountNameTxt.setText("银行卡号");
            tvAccountName.setText(account_name);
            llQrcode.setVisibility(View.GONE);
            llBankInfo.setVisibility(View.VISIBLE);
            if (payInfo != null) {
                tvBankDetailName.setText(new StringBuilder()
                        .append(payInfo.getBank_name())
                        .append("  ")
                        .append(payInfo.getBank_detail_name())
                        .toString());
            }
        } else if (pay_type == 2 || pay_type == 3) {
            if (pay_type == 2) {
                imgPayTypeBankCard.setVisibility(View.GONE);
                imgPayTypeAlipay.setVisibility(View.VISIBLE);
                imgPayTypeWechat.setVisibility(View.GONE);
                tvPayType.setText("支付宝");
            } else if (pay_type == 3) {
                imgPayTypeBankCard.setVisibility(View.GONE);
                imgPayTypeAlipay.setVisibility(View.GONE);
                imgPayTypeWechat.setVisibility(View.VISIBLE);
                tvPayType.setText("微信");
            }
            tvAccountNameTxt.setText("收款账号");
            tvAccountName.setText(account_name);
            llQrcode.setVisibility(View.VISIBLE);
            if (payInfo != null) qrcodeUrl = payInfo.getQrcode();
            llBankInfo.setVisibility(View.GONE);
        }
        if (payInfo != null) {
            tvUserName.setText(payInfo.getUser_name());
            tvPayCode.setText(pay_code);
            tvCreatetime.setText(createtime);
        }
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PayActivity.this.isChecked = isChecked;
            }
        });
    }

    @OnClick({R.id.left_ll, R.id.ll_qrcode, R.id.btn_finish_pay})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.left_ll:
                goBack(v);
                break;

            case R.id.ll_qrcode:
                Intent intent = new Intent(PayActivity.this, ImageBrowseActivity.class);
                intent.putExtra(ImageBrowseActivity.IMG_URL, qrcodeUrl);
                startActivity(intent);
                overridePendingTransition(R.anim.actionsheet_dialog_in, 0);
                break;
            case R.id.btn_finish_pay:
                if (!isTimeout) {
                    if (isChecked) {
                        Intent i = new Intent(PayActivity.this, PayFloatActivity.class);
                        i.putExtra("order_id", order_id);
                        i.putExtra("pi_id", pi_id);
                        startActivity(i);
                        overridePendingTransition(R.anim.actionsheet_dialog_in, 0);

                    } else {
                        ToastUtils.showCustomToastMsg("请同意",150);
                    }
                } else {
                    ToastUtils.showCustomToastMsg("订单付款已超时，请重新下单",150);
                }
                break;
        }
    }

    private void countDownPayTime(long expire_time) {
        TimeCount = new CountDownTimer(expire_time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (!PayActivity.this.isFinishing()) {
                    String countDown = TimeUtils.getToMSTime((int) (millisUntilFinished / 1000));
                    tvTimeClock.setText(countDown);
                }
            }

            @Override
            public void onFinish() {
                if (!PayActivity.this.isFinishing()) {
//                    cancelOrder(lastStep, order_id);
                    tvTimeClock.setText("00分00秒");
                    isTimeout = true;
                }
            }

        };
        TimeCount.start();
    }

    private void cancelOrder(String lastStep, long order_id) {
        showLoadingDialog();
        if (cancelOrderPresenter == null) {
            cancelOrderPresenter = new CancelOrderPresenter(new CancelOrderModel());
        }
        if (OrderListsTabFragment.BUYER_UNPAY.equals(lastStep)) {
            cancelOrderPresenter.cancelOrder(this, order_id);
        } /*else if (TraderUnpayActivity.SELLER_UNPAY.equals(lastStep)) {
//            traderUnpayPresenter.sellerUnpay(this, orderId);
        }*/
    }

    public void onEventMainThread(UserDoPayEvent event) {
        if (event != null) {
            if (event.getAction().equals(PayFloatActivity.ACTION_FORM_PAYFLOATACTIVITY)) {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (cancelOrderPresenter != null) {
            cancelOrderPresenter.detachView();
        }
        if (TimeCount != null) TimeCount.cancel();
        super.onDestroy();
    }
}
