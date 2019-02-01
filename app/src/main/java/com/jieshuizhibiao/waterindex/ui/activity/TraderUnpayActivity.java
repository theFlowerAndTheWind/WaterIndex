package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.ListOrder;
import com.jieshuizhibiao.waterindex.beans.unpay.BaseUnpayOrderInfo;
import com.jieshuizhibiao.waterindex.beans.unpay.BuyerUnpayOrderInfo;
import com.jieshuizhibiao.waterindex.beans.unpay.BuyerUnpayResponse;
import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;
import com.jieshuizhibiao.waterindex.beans.unpay.SellerUnpayOrderInfo;
import com.jieshuizhibiao.waterindex.beans.unpay.SellerUnpayResponse;
import com.jieshuizhibiao.waterindex.contract.model.CancelOrderModel;
import com.jieshuizhibiao.waterindex.contract.model.TraderModel;
import com.jieshuizhibiao.waterindex.contract.presenter.CancelOrderPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.TraderPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CancelOrderViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.event.ChangeOrderStatusEvent;
import com.jieshuizhibiao.waterindex.event.UserDoPayEvent;
import com.jieshuizhibiao.waterindex.ui.fragment.OrderListsTabFragment;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.utils.TimeUtils;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.image.GlidImageManager;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * 本页面为buyerUnpay,selllerUnpay共用
 */
public class TraderUnpayActivity extends BaseActivity implements CommonViewImpl, CancelOrderViewImpl {

    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;

    @BindView(R.id.ll_card)
    LinearLayout llCard;
    @BindView(R.id.divider_under_card)
    LinearLayout dividerUnderCard;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;
    @BindView(R.id.divider_under_alipay)
    LinearLayout dividerUnderAlipay;
    @BindView(R.id.ll_wechat)
    LinearLayout llWechat;
    @BindView(R.id.img_right_bank)
    ImageView imgRightBank;
    @BindView(R.id.img_right_alipay)
    ImageView imgRightAlipay;
    @BindView(R.id.img_right_wechat)
    ImageView imgRightWechat;

    @BindView(R.id.tv_pay_cate_txt)
    TextView tvPayCateTxt;
    @BindView(R.id.ll_expire_time_container)
    LinearLayout llExpireTimeContainer;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_expire_time)
    TextView tvExpireTime;
    @BindView(R.id.tv_bank_name_detail)
    TextView tvBankNameDetail;
    @BindView(R.id.tv_rmb)
    TextView tvRmb;
    @BindView(R.id.tv_trade_type)
    TextView tvTradeType;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_pay_code)
    TextView tvPayCode;
    @BindView(R.id.tv_createtime)
    TextView tvCreatetime;
    @BindView(R.id.tv_trader)
    TextView tvTrader;
    @BindView(R.id.img_trader_avatar)
    ImageView imgTraderAvatar;
    @BindView(R.id.tv_trader_nickname)
    TextView tvTraderNickname;
    @BindView(R.id.ll_btns_buyer)
    LinearLayout llBtnsBuyer;
    @BindView(R.id.ll_btns_seller)
    LinearLayout llBtnsSeller;

    private Unbinder unbinder;

    private ListOrder order;
    //    private String my_action;
    private String current_step;
    private long order_id;
    private TraderPresenter traderPresenter;
    private CancelOrderPresenter cancelOrderPresenter;
    private CountDownTimer TimeCount;
    private int pay_type = 0;//1选择卡   2选择支付宝    3选择微信
    private String pay_code;//付款参考号
    private String createtime;
    private String expire_time;
    private AlertChainDialog dialog;
    private HashMap<Integer, PayInfo> map = new HashMap<>();
    private long millis = 0;
    private boolean isTimeout = false;//倒计时是否结束，结束订单将取消

    private final String CANCEL_SUCC = "取消成功!";
    private final String I_KNOW = "知道了";
    public static final String CURRENT_STEP = "current_step";
    public static final String PAY_TYPE = "pay_type";
    public static final String PAYINFO = "PayInfo";
    public static final String MILLIS = "millisUntilFinished";
    public static final String ORDERINFO = "OrderInfo";

    private BuyerUnpayOrderInfo buyerUnpayOrderInfo;
    private SellerUnpayOrderInfo sellerUnpayOrderInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getIntentExtra();
        initView();
        traderPresenter = new TraderPresenter(new TraderModel());
        traderPresenter.attachView(this);
        doRequest(current_step);
        cancelOrderPresenter = new CancelOrderPresenter(new CancelOrderModel());
        cancelOrderPresenter.attachView(this);
    }

    private void getIntentExtra() {
        order = getIntent().getParcelableExtra(OrderListsTabFragment.LISTORDER);
        if (order != null) {
            current_step = order.getNext_step();
            order_id = order.getOrder_id();
        }
    }

    private void initView() {
        if (order != null) {
            if (current_step.equals(OrderListsTabFragment.BUYER_UNPAY)) {
                tvTitleCenter.setText("购买节水指标");
                llExpireTimeContainer.setVisibility(View.VISIBLE);
                tvTradeType.setText("购买");
                tvTrader.setText("卖家");
                llBtnsBuyer.setVisibility(View.VISIBLE);
                llBtnsSeller.setVisibility(View.GONE);
            } else if (current_step.equals(OrderListsTabFragment.SELLER_UNPAY)) {
                tvTitleCenter.setText("出售节水指标");
                tvPayCateTxt.setText("收款方式");
                llExpireTimeContainer.setVisibility(View.GONE);//如果产品要求卖单不显示倒计时，执行这句
                tvTradeType.setText("出售");
                tvTrader.setText("买家");
                llBtnsBuyer.setVisibility(View.GONE);
                llBtnsSeller.setVisibility(View.VISIBLE);
                llCard.setEnabled(false);
                llAlipay.setEnabled(false);
                llWechat.setEnabled(false);
            }
        }
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_trader_unpay);

    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @Override
    public void onRequestSuccess(Object bean) {
        if (bean != null) {
            List<PayInfo> pay_info_list = null;
            BaseUnpayOrderInfo baseUnpayOrderInfo = null;
            String nickname = null;
            String avatarUrl = null;
            if (OrderListsTabFragment.BUYER_UNPAY.equals(current_step)) {
                BuyerUnpayResponse buyerUnpayResponse = (BuyerUnpayResponse) bean;
                pay_info_list = buyerUnpayResponse.getPay_info_list();
                buyerUnpayOrderInfo = buyerUnpayResponse.getOrder_info();
                avatarUrl = buyerUnpayOrderInfo.getSeller_avatar();
                nickname = buyerUnpayOrderInfo.getSeller_nickname();
                baseUnpayOrderInfo = buyerUnpayOrderInfo;
            } else if (OrderListsTabFragment.SELLER_UNPAY.equals(current_step)) {
                SellerUnpayResponse sellerUnpayResponse = (SellerUnpayResponse) bean;
                pay_info_list = sellerUnpayResponse.getPay_info_list();
                sellerUnpayOrderInfo = sellerUnpayResponse.getOrder_info();
                avatarUrl = sellerUnpayOrderInfo.getBuyer_avatar();
                nickname = sellerUnpayOrderInfo.getBuyer_nickname();
                baseUnpayOrderInfo = sellerUnpayOrderInfo;
            }
            pay_code = baseUnpayOrderInfo.getPay_code();
            createtime = baseUnpayOrderInfo.getCreatetime();
            setView(baseUnpayOrderInfo, pay_info_list, avatarUrl, nickname);
        }
        dismissLoadingDialog();
    }

    private void setView(BaseUnpayOrderInfo baseUnpayOrderInfo, List<PayInfo> list, String avatarUrl, String nickname) {
        if (baseUnpayOrderInfo != null && list != null) {
            expire_time = baseUnpayOrderInfo.getExpire_time();
            if (expire_time.equals("不限制")) {
                tvExpireTime.setText("不限制");
                tvLeft.setVisibility(View.VISIBLE);
                tvLeft.setText("付款时限：");
                tvRight.setVisibility(View.GONE);
            } else if (isNumeric(expire_time)) {
                if (current_step.equals(OrderListsTabFragment.SELLER_UNPAY)) {
                    tvLeft.setVisibility(View.VISIBLE);
                    tvRight.setVisibility(View.VISIBLE);
                    tvLeft.setText("对方将在：");
                    tvRight.setText("内付款");
                }
                long expiretime = Long.valueOf(expire_time);
                countDownPayTime(expiretime);
            } else {
                if (current_step.equals(OrderListsTabFragment.BUYER_UNPAY)) {
                    tvExpireTime.setText("请尽快付款");
                } else if (current_step.equals(OrderListsTabFragment.SELLER_UNPAY)) {
                    tvExpireTime.setText("对方将尽快付款");
                }
                tvLeft.setVisibility(View.GONE);
                tvRight.setVisibility(View.GONE);
            }
            String rmbStr=formatRmb(baseUnpayOrderInfo.getRmb(),"元");
            tvRmb.setText(rmbStr);
            tvTotal.setText(baseUnpayOrderInfo.getTotal());
            String priceStr=formatRmb(baseUnpayOrderInfo.getPrice(),"元/T");
            tvPrice.setText(priceStr);
            tvOrderSn.setText(baseUnpayOrderInfo.getOrder_sn());
            tvPayCode.setText(baseUnpayOrderInfo.getPay_code());
            tvCreatetime.setText(baseUnpayOrderInfo.getCreatetime());
            GlidImageManager.getInstance().loadCircleImg(this, avatarUrl, imgTraderAvatar, R.mipmap.head, R.mipmap.head);
            tvTraderNickname.setText(nickname);

            for (PayInfo payInfo : list) {
                int type = payInfo.getType();
                if (type == 1) {//card
                    llCard.setVisibility(View.VISIBLE);
                    dividerUnderCard.setVisibility(View.VISIBLE);
                    tvBankNameDetail.setText(new StringBuilder().append(payInfo.getBank_name()).append("   ").append(payInfo.getBank_detail_name()).toString());
                } else if (type == 2) {//alipay
                    llAlipay.setVisibility(View.VISIBLE);
                    dividerUnderAlipay.setVisibility(View.VISIBLE);
                } else if (type == 3) {//wechat
                    llWechat.setVisibility(View.VISIBLE);
                }
                map.put(type, payInfo);
            }
        }
    }

    public static String formatRmb(String rmb,String replaceTarget){
        rmb=rmb.replace(replaceTarget,"").trim();
        rmb=String.format("%.2f",Float.valueOf(rmb));
        return rmb+replaceTarget;
    }


    @Override
    public void onRequestFailed(String msg) {
        ToastUtils.showCustomToast(msg, 0);
        dismissLoadingDialog();
    }

    @Override
    public void onCancelSucc(Object bean) {
        dismissLoadingDialog();
        showDialog("提示", CANCEL_SUCC, I_KNOW);
    }

    @Override
    public void onCancelFail(String msg) {
        ToastUtils.showCustomToast(msg, 0);
        dismissLoadingDialog();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    private void countDownPayTime(long expire_time) {
        TimeCount = new CountDownTimer(expire_time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (!TraderUnpayActivity.this.isFinishing()) {
                    String countDown = TimeUtils.getToMSTime((int) (millisUntilFinished / 1000));

                    tvExpireTime.setText(countDown);
                    millis = millisUntilFinished;
                }
            }

            @Override
            public void onFinish() {
                if (!TraderUnpayActivity.this.isFinishing()) {
//                    cancelOrder(current_step, order_id);
                    tvExpireTime.setText("00分00秒");
                    isTimeout = true;
                }
            }

        };
        TimeCount.start();
    }

    @OnClick({R.id.left_ll, R.id.ll_card, R.id.ll_alipay, R.id.ll_wechat,
            R.id.btn_buyer_cancle, R.id.btn_buyer_pay,  R.id.btn_seller_pay})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.left_ll:
                finish();
                break;
            case R.id.ll_card:
                showOrGoneRightImg(imgRightBank);
                if (imgRightBank.getVisibility() == View.VISIBLE) {
                    imgRightWechat.setVisibility(View.GONE);
                    imgRightAlipay.setVisibility(View.GONE);
                    pay_type = 1;
                }
                break;
            case R.id.ll_alipay:
                showOrGoneRightImg(imgRightAlipay);
                if (imgRightAlipay.getVisibility() == View.VISIBLE) {
                    imgRightWechat.setVisibility(View.GONE);
                    imgRightBank.setVisibility(View.GONE);
                    pay_type = 2;
                }
                break;
            case R.id.ll_wechat:
                showOrGoneRightImg(imgRightWechat);
                if (imgRightWechat.getVisibility() == View.VISIBLE) {
                    imgRightBank.setVisibility(View.GONE);
                    imgRightAlipay.setVisibility(View.GONE);
                    pay_type = 3;
                }
                break;
            case R.id.btn_buyer_cancle:
                if (!isTimeout) {
                    cancelOrder(current_step, order_id);
                } else {
                    ToastUtils.showCustomToastMsg("订单付款已超时，请重新下单",150);
                }
                break;

            case R.id.btn_buyer_pay:
                if(!isTimeout){
                    //该步骤仅买家身份有，卖家身份没有
                    jumpPayActivity();
                }else{
                    ToastUtils.showCustomToastMsg("订单付款已超时，请重新下单",150);
                }

                break;
            //产品取消该功能
            //case R.id.btn_seller_cancle:
            //   //todo 卖家取消订单  无对应接口
            //break;
            case R.id.btn_seller_pay:
                //do nothing

                break;
        }
    }

    private void jumpPayActivity() {
        if (pay_type <= 0) {
            showDialog("提示", "请选择付款方式！", I_KNOW);
            return;
        } else if (pay_type >= 1 || pay_type <= 3) {
            Intent intent = new Intent(TraderUnpayActivity.this, PayActivity.class);
            //1
            if (current_step.equals(OrderListsTabFragment.BUYER_UNPAY)) {
                intent.putExtra(ORDERINFO, buyerUnpayOrderInfo);
            } else if (current_step.equals(OrderListsTabFragment.SELLER_UNPAY)) {
                intent.putExtra(ORDERINFO, sellerUnpayOrderInfo);
            }
            //2
            intent.putExtra(PAYINFO, map.get(pay_type));
            //3
            intent.putExtra(CURRENT_STEP, current_step);
            //4
            intent.putExtra(PAY_TYPE, pay_type);
            //5
            if (current_step.equals(OrderListsTabFragment.BUYER_UNPAY)) {
                if (expire_time.equals("不限制")) {
                    intent.putExtra(MILLIS, "不限制");
                } else if (isNumeric(expire_time)) {
                    if (millis > 1000) {//1s
                        intent.putExtra(MILLIS, String.valueOf(millis));
                    }
                } else {
                    intent.putExtra(MILLIS, "请尽快付款");
                }
            }
            startActivity(intent);
        }
    }

    private void jumpPaidActivity() {

    }


    private void doRequest(String next_step) {
        showLoadingDialog();
        if (traderPresenter == null) {
            traderPresenter = new TraderPresenter(new TraderModel());
        }
        if (OrderListsTabFragment.BUYER_UNPAY.equals(next_step)) {
            traderPresenter.buyerUnpay(this, order_id);
        } else if (OrderListsTabFragment.SELLER_UNPAY.equals(next_step)) {
            traderPresenter.sellerUnpay(this, order_id);
        }
    }

    private void cancelOrder(String next_step, long order_id) {
        showLoadingDialog();
        if (cancelOrderPresenter == null) {
            cancelOrderPresenter = new CancelOrderPresenter(new CancelOrderModel());
        }
        if(OrderListsTabFragment.BUYER_UNPAY.equals(next_step)){
            cancelOrderPresenter.cancelOrder(this, order_id);
        }
    }

    private void showOrGoneRightImg(ImageView img) {
        int visibility = img.getVisibility();
        if (visibility == View.VISIBLE) {
            img.setVisibility(View.GONE);
        } else if (visibility == View.GONE || visibility == View.INVISIBLE) {
            img.setVisibility(View.VISIBLE);
        }
    }

    private void showDialog(final String title, final String msg, final String positive) {
        if (dialog == null) {
            dialog = new AlertChainDialog(this);
        }
        dialog.builder().setCancelable(true);
        dialog.setTitle(title)
                .setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (positive.equals(I_KNOW)) {
                            dialog.dismiss();
                            if (msg.equals(CANCEL_SUCC)) {
                                finish();
                                //该事件发出，所有OrderListsTabFragment都刷新
                                EventBus.getDefault().post(new ChangeOrderStatusEvent("TraderUnpayActivity", "buyer_cancel"));
                                return;
                            }
                        }
//
                    }
                })
                .show();
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
        if (traderPresenter != null) {
            traderPresenter.detachView();
        }
        if (cancelOrderPresenter != null) {
            cancelOrderPresenter.detachView();
        }
        if (TimeCount != null) TimeCount.cancel();
        EventBus.getDefault().unregister(this);
        if(TimeCount!=null) TimeCount.cancel();
        super.onDestroy();
    }


}
