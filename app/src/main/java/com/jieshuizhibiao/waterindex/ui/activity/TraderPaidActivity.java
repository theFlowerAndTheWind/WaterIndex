package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.ListOrder;
import com.jieshuizhibiao.waterindex.beans.paid.BuyerPaidOrderInfo;
import com.jieshuizhibiao.waterindex.beans.paid.BuyerPaidResponse;
import com.jieshuizhibiao.waterindex.beans.paid.SellerPaidOrderInfo;
import com.jieshuizhibiao.waterindex.beans.paid.SellerPaidResponse;
import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;
import com.jieshuizhibiao.waterindex.contract.model.SellerCheckoutModel;
import com.jieshuizhibiao.waterindex.contract.model.TraderDoAppealModel;
import com.jieshuizhibiao.waterindex.contract.model.TraderModel;
import com.jieshuizhibiao.waterindex.contract.presenter.SellerCheckoutPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.TraderDoAppealPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.TraderPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.SecondRequstViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.UploadFileViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.ThirdRequestViewImpl;
import com.jieshuizhibiao.waterindex.event.ChangeOrderStatusEvent;
import com.jieshuizhibiao.waterindex.ui.fragment.OrderListsTabFragment;
import com.jieshuizhibiao.waterindex.ui.view.NewAlertDialog;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.image.GlidImageManager;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class TraderPaidActivity extends BaseActivity implements CommonViewImpl, SecondRequstViewImpl, ThirdRequestViewImpl {
    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.title_bar)
    View titleBar;
    @BindView(R.id.left_ll)
    LinearLayout leftLl;

    @BindView(R.id.tv_big_rmb)
    TextView tvBigRmb;
    @BindView(R.id.tv_buyer_paid)
    TextView tvBuyerPaid;
    @BindView(R.id.ll_time_clock)
    LinearLayout llTimeClock;
    @BindView(R.id.ll_header_rmb)
    LinearLayout llHeaderRmb;

    @BindView(R.id.ll_trade_info)
    LinearLayout llTradeInfo;
    @BindView(R.id.tv_rmb)
    TextView tvRmb;
    @BindView(R.id.ll_rmb)
    LinearLayout llRmb;
    @BindView(R.id.tv_trade_type)
    TextView tvTradeType;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.ll_layout_trade_infp)
    LinearLayout llLayoutTradeInfp;
    @BindView(R.id.ll_trade_info_container)
    LinearLayout llTradeInfoContainer;

    @BindView(R.id.ll_pay_info)
    LinearLayout llPayInfo;
    @BindView(R.id.img_pay_type_bank_card)
    ImageView imgPayTypeBankCard;
    @BindView(R.id.img_pay_type_alipay)
    ImageView imgPayTypeAlipay;
    @BindView(R.id.img_pay_type_wechat)
    ImageView imgPayTypeWechat;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.ll_pay_type)
    LinearLayout llPayType;
    @BindView(R.id.tv_user_name_txt)
    TextView tvUserNameTxt;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_user_name)
    LinearLayout llUserName;
    @BindView(R.id.tv_account_name_txt)
    TextView tvAccountNameTxt;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.ll_account_name)
    LinearLayout llAccountName;
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
    @BindView(R.id.ll_pay_code)
    LinearLayout llPayCode;
    @BindView(R.id.tv_createtime)
    TextView tvCreatetime;
    @BindView(R.id.ll_createtime)
    LinearLayout llCreatetime;
    @BindView(R.id.tv_paytime)
    TextView tvPaytime;
    @BindView(R.id.ll_paytime)
    LinearLayout llPaytime;
    @BindView(R.id.tv_trader)
    TextView tvTrader;
    @BindView(R.id.img_trader_avatar)
    ImageView imgTraderAvatar;
    @BindView(R.id.tv_trader_nickname)
    TextView tvTraderNickname;
    @BindView(R.id.ll_trader)
    LinearLayout llTrader;
    @BindView(R.id.img_pay_snapshot)
    ImageView imgPaySnapshot;
    @BindView(R.id.ll_pay_snapshot)
    LinearLayout llPaySnapshot;
    @BindView(R.id.ll_seller_part)
    LinearLayout llSellerPart;
    @BindView(R.id.btn_buyer_appeal)
    Button btnBuyerAppeal;
    @BindView(R.id.btn_seller_appeal)
    Button btnSellerAppeal;
    @BindView(R.id.btn_seller_checkout)
    Button btnSellerCheckout;
    @BindView(R.id.ll_btns_seller)
    LinearLayout llBtnsSeller;
    @BindString(R.string.appeal_warning)
    String appealWarning;

    private String current_step;
    private long order_id;
    private PayInfo payInfo;
    private String qrcodeUrl;//该页面要做放大吗？？
    private String paySnapshotUrl;
    private String pay_code;
    private String createtime;
    private NewAlertDialog dialog;
    private String appealConten;
    private String safePw;

    private TraderPresenter traderPresenter;
    private TraderDoAppealPresenter traderDoAppealPresenter;
    private SellerCheckoutPresenter sellerCheckoutPresenter;

    private final String I_KNOW = "知道了";
    private final String APPEAL_SUCC="申诉成功！";
    private final String CHECKOUT_SUCC="放行成功！";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, titleBar);
        getIntentExtra();
        initView();
        traderPresenter = new TraderPresenter(new TraderModel());
        traderPresenter.attachView(this);
        traderDoAppealPresenter = new TraderDoAppealPresenter(new TraderDoAppealModel());
        traderDoAppealPresenter.attachView(this);
        sellerCheckoutPresenter = new SellerCheckoutPresenter(new SellerCheckoutModel());
        sellerCheckoutPresenter.attachView(this);
        doRequest();

    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_trader_paid);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    private void initView() {
        llTimeClock.setVisibility(View.GONE);
        if (current_step.equals(OrderListsTabFragment.BUYER_PAID)) {
            tvTitleCenter.setText("等待卖家放行");
            llHeaderRmb.setVisibility(View.GONE);
            tvBuyerPaid.setVisibility(View.GONE);
            llTradeInfoContainer.setVisibility(View.VISIBLE);

            llPaytime.setVisibility(View.VISIBLE);
            llSellerPart.setVisibility(View.GONE);

            btnBuyerAppeal.setVisibility(View.VISIBLE);
            llBtnsSeller.setVisibility(View.GONE);

        } else if (current_step.equals(OrderListsTabFragment.SELLER_PAID)) {
            tvTitleCenter.setText("收款");
            llHeaderRmb.setVisibility(View.VISIBLE);
            tvBuyerPaid.setVisibility(View.VISIBLE);
            llTradeInfoContainer.setVisibility(View.GONE);

            llPaytime.setVisibility(View.GONE);
            llSellerPart.setVisibility(View.VISIBLE);

            btnBuyerAppeal.setVisibility(View.GONE);
            llBtnsSeller.setVisibility(View.VISIBLE);
            llPaySnapshot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TraderPaidActivity.this,ImageBrowseActivity.class);
                    intent.putExtra(ImageBrowseActivity.IMG_URL,paySnapshotUrl);
                    startActivity(intent);
                }
            });
        }
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        ListOrder order = intent.getParcelableExtra(OrderListsTabFragment.LISTORDER);
        current_step = order.getNext_step();
        order_id = order.getOrder_id();
    }


    @OnClick({R.id.left_ll, R.id.btn_seller_appeal, R.id.btn_seller_checkout,R.id.btn_buyer_appeal})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.left_ll:
                finish();
                break;
            case R.id.btn_seller_appeal:
                showDialog(NewAlertDialog.TYPES[0],
                        "订单申诉",
                        appealWarning,
                        "确认",
                        null);
                break;
            case R.id.btn_seller_checkout:
                showDialog(NewAlertDialog.TYPES[1],
                        "确认收款并放行",
                        "请务必登录网银或第三方支付账号确定该笔款项",
                        "确认",
                        "取消");
                break;
            case R.id.btn_buyer_appeal:
                showDialog(NewAlertDialog.TYPES[0],
                        "订单申诉",
                        appealWarning,
                        "确认",
                        null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * perform after getIntentExtra().
     */
    private void doRequest() {
        if (traderPresenter == null) {
            traderPresenter = new TraderPresenter(new TraderModel());
            traderPresenter.attachView(this);
        }
        if (current_step.equals(OrderListsTabFragment.BUYER_PAID)) {
            traderPresenter.buyerPaid(this, order_id);
        } else if (current_step.equals(OrderListsTabFragment.SELLER_PAID)) {
            traderPresenter.sellerPaid(this, order_id);
        }
    }

    private void doAppeal(String detail) {
        if (traderDoAppealPresenter == null) {
            traderDoAppealPresenter = new TraderDoAppealPresenter(new TraderDoAppealModel());
            traderDoAppealPresenter.attachView(this);
        }
        if (TextUtils.isEmpty(detail)) {
            ToastUtils.showCustomToastMsg("未填写申诉内容",150);
            return;
        }
        if (current_step.equals(OrderListsTabFragment.BUYER_PAID)) {
            traderDoAppealPresenter.buyerDoAppeal(this, order_id, detail);
        } else if (current_step.equals(OrderListsTabFragment.SELLER_PAID)) {
            traderDoAppealPresenter.sellerDoAppeal(this, order_id, detail);

        }
    }

    private void doCheckout(String safe_pw) {
        if (sellerCheckoutPresenter == null) {
            sellerCheckoutPresenter = new SellerCheckoutPresenter(new SellerCheckoutModel());
            sellerCheckoutPresenter.attachView(this);
        }
        if (TextUtils.isEmpty(safe_pw)) {
            ToastUtils.showCustomToastMsg("请输入资金密码",150);
            return;
        }
        sellerCheckoutPresenter.sellerCheckout(this, order_id, safe_pw);
    }

    @Override
    public void onRequestSuccess(Object bean) {
        if (bean != null) {
            BuyerPaidOrderInfo buyerPaidOrderInfo;
            SellerPaidOrderInfo sellerPaidOrderInfo;

            if (current_step.equals(OrderListsTabFragment.BUYER_PAID)) {
                BuyerPaidResponse buyerPaidResponse = (BuyerPaidResponse) bean;
                buyerPaidOrderInfo = buyerPaidResponse.getOrder_info();
                if (buyerPaidResponse != null) {
                    payInfo = buyerPaidResponse.getPay_info();
                }
                if (buyerPaidOrderInfo != null) {
                    pay_code = buyerPaidOrderInfo.getPay_code();
                    createtime = buyerPaidOrderInfo.getCreatetime();
                    //买家内容
                    String rmbStr = TraderUnpayActivity.formatRmb(buyerPaidOrderInfo.getRmb(),"元");
                    tvRmb.setText(rmbStr);
                    tvTotal.setText(buyerPaidOrderInfo.getTotal());
                    String priceStr=TraderUnpayActivity.formatRmb(buyerPaidOrderInfo.getPrice(),"元/T");
                    tvPrice.setText(priceStr);
                    tvPaytime.setText(buyerPaidOrderInfo.getPaytime());
                }
            } else if (current_step.equals(OrderListsTabFragment.SELLER_PAID)) {
                SellerPaidResponse sellerPaidResponse = (SellerPaidResponse) bean;
                sellerPaidOrderInfo = sellerPaidResponse.getOrder_info();
                if (sellerPaidResponse != null) {
                    payInfo = sellerPaidResponse.getPay_info();
                }

                if (sellerPaidOrderInfo != null) {
                    pay_code = sellerPaidOrderInfo.getPay_code();
                    createtime = sellerPaidOrderInfo.getCreatetime();
                    //卖家内容
                    String rmbStr = TraderUnpayActivity.formatRmb(sellerPaidOrderInfo.getRmb(),"元");
                    tvBigRmb.setText(rmbStr);
                    tvTrader.setText("买家");
                    GlidImageManager.getInstance().loadCircleImg(this, sellerPaidOrderInfo.getBuyer_avatar(), imgTraderAvatar, R.mipmap.head, R.mipmap.head);
                    tvTraderNickname.setText(sellerPaidOrderInfo.getBuyer_nickname());
                    paySnapshotUrl=sellerPaidOrderInfo.getPay_snapshot();
                    GlidImageManager.getInstance().loadImageView(this,paySnapshotUrl,imgPaySnapshot,R.mipmap.qrcode);
                }
            }
            if (payInfo != null) {
                setCommonView(payInfo);
            }
        }
    }

    @Override
    public void onRequestFailed(String msg) {
        ToastUtils.showCustomToast(msg,0);
    }

    //卖家申诉成功、买家申诉成功
    @Override
    public void onSecondRequstSuccess(Object bean) {
        showDialog(null, "提示", APPEAL_SUCC, I_KNOW, null);
    }

    //申诉失败
    @Override
    public void onSecondRequstFailed(String msg) {
        ToastUtils.showCustomToast(msg,0);
    }

    //放行成功
    @Override
    public void onThirdRequestSucc(Object o) {
        showDialog(null, "提示", CHECKOUT_SUCC, I_KNOW, null);
    }

    //放行失败
    @Override
    public void onThirdRequestFail(String msg) {

    }

    private void setCommonView(PayInfo payInfo) {
        final int type = payInfo.getType();
        if (type == 1) {
            imgPayTypeBankCard.setVisibility(View.VISIBLE);
            imgPayTypeAlipay.setVisibility(View.GONE);
            imgPayTypeWechat.setVisibility(View.GONE);
            tvPayType.setText("银行卡");
            tvAccountNameTxt.setText("银行卡号");
            llBankInfo.setVisibility(View.VISIBLE);
            llQrcode.setVisibility(View.GONE);
            tvBankName.setText(payInfo.getBank_name());
            tvBankDetailName.setText(payInfo.getBank_detail_name());
        } else if (type == 2 || type == 3) {
            if (type == 2) {
                imgPayTypeBankCard.setVisibility(View.GONE);
                imgPayTypeAlipay.setVisibility(View.VISIBLE);
                imgPayTypeWechat.setVisibility(View.GONE);
                tvPayType.setText("支付宝");
            } else if (type == 3) {
                imgPayTypeBankCard.setVisibility(View.GONE);
                imgPayTypeAlipay.setVisibility(View.VISIBLE);
                imgPayTypeWechat.setVisibility(View.GONE);
                tvPayType.setText("支付宝");
            }
            tvUserName.setText(payInfo.getUser_name());
            tvAccountNameTxt.setText("收款账号");
            tvAccountName.setText(payInfo.getAccount_name());
            llBankInfo.setVisibility(View.GONE);
            llQrcode.setVisibility(View.VISIBLE);
            qrcodeUrl = payInfo.getQrcode();
        }
        tvPayCode.setText(pay_code);
        tvCreatetime.setText(createtime);


    }

    private void showDialog(final String type, String title, final String msg, final String positive, final String negative) {
        if (dialog == null) {
            dialog = new NewAlertDialog(this);
        }
        dialog.builder().setCancelable(true);
        dialog.setType(type)
                .setTitle(title)
                .setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(msg.equals(APPEAL_SUCC)){
                            dialog.dismiss();
                            finish();
                            //该事件发出，所有OrderListsTabFragment都刷新
                            EventBus.getDefault().post(new ChangeOrderStatusEvent("TraderPaidActivity", "trader_do_appeal"));
                            return;
                        }else if(msg.equals(CHECKOUT_SUCC)){
                            finish();
                            //该事件发出，所有OrderListsTabFragment都刷新
                            EventBus.getDefault().post(new ChangeOrderStatusEvent("TraderPaidActivity", "seller_checkout"));
                            return;
                        }

                        if (type.equals(NewAlertDialog.TYPES[0])) {//卖家发起申诉
                            appealConten = dialog.getAppealContent();
                            dialog.setAppealContent("");
                            doAppeal(appealConten);
                        } else if (type.equals(NewAlertDialog.TYPES[1])) {//卖家放行
                            safePw = dialog.getSafepwContent();
                            dialog.setSafepwContent("");
                            doCheckout(safePw);
                        } else if (TextUtils.isEmpty(type)) {
                            dialog.dismiss();
                        }

                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        if (traderPresenter != null) {
            traderPresenter.detachView();
        }
        super.onDestroy();
    }

}
