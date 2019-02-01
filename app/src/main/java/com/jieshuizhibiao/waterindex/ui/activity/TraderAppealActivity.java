package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.ListOrder;
import com.jieshuizhibiao.waterindex.beans.appeal.AppealResponse;
import com.jieshuizhibiao.waterindex.beans.appeal.OrderInfo;
import com.jieshuizhibiao.waterindex.beans.appeal.PayInfo;
import com.jieshuizhibiao.waterindex.contract.model.TraderModel;
import com.jieshuizhibiao.waterindex.contract.presenter.TraderPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.ui.fragment.OrderListsTabFragment;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.image.GlidImageManager;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class TraderAppealActivity extends BaseActivity implements CommonViewImpl {

    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.title_bar)
    View titleBar;
    @BindView(R.id.img_succ)
    ImageView imgSucc;
    @BindView(R.id.img_appeal)
    ImageView imgAppeal;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    @BindView(R.id.tv_order_succ)
    TextView tvOrderSucc;
    @BindView(R.id.tv_order_appeal)
    TextView tvOrderAppeal;
    @BindView(R.id.tv_order_cancel)
    TextView tvOrderCancel;
    @BindView(R.id.tv_rmb)
    TextView tvRmb;
    @BindView(R.id.tv_trade_type)
    TextView tvTradeType;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.img_pay_type_bank_card)
    ImageView imgPayTypeBankCard;
    @BindView(R.id.img_pay_type_alipay)
    ImageView imgPayTypeAlipay;
    @BindView(R.id.img_pay_type_wechat)
    ImageView imgPayTypeWechat;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_trader)
    TextView tvTrader;
    @BindView(R.id.img_trader_avatar)
    ImageView imgTraderAvatar;
    @BindView(R.id.tv_trader_nickname)
    TextView tvTraderNickname;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_pay_code)
    TextView tvPayCode;
    @BindView(R.id.tv_createtime)
    TextView tvCreatetime;
    @BindView(R.id.tv_paytime)
    TextView tvPaytime;
    @BindView(R.id.tv_appealtime)
    TextView tvAppealtime;

    @BindColor(R.color.text_black)
    int textBlack;

    private ListOrder listOrder;
    private String current_step;
    private long order_id;

    private TraderPresenter traderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, titleBar);
        getIntentExtra();
        initView();
        traderPresenter = new TraderPresenter(new TraderModel());
        traderPresenter.attachView(this);
        doRequest();
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        listOrder = intent.getParcelableExtra(OrderListsTabFragment.LISTORDER);
        if (listOrder != null) {
            current_step = listOrder.getNext_step();
            order_id = listOrder.getOrder_id();
        }
    }

    private void initView() {
        tvTitleCenter.setText("申诉处理中");
        if (current_step.equals(OrderListsTabFragment.BUYER_APPEAL)) {
            tvTrader.setText("卖家");
        } else if (current_step.equals(OrderListsTabFragment.SELLER_APPEAL)) {
            tvTrader.setText("买家");
        }
        imgAppeal.setVisibility(View.VISIBLE);
        imgCancel.setVisibility(View.GONE);
        imgSucc.setVisibility(View.GONE);
        tvOrderAppeal.setVisibility(View.VISIBLE);
        tvOrderCancel.setVisibility(View.GONE);
        tvOrderSucc.setVisibility(View.GONE);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_trader_appeal);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.left_ll})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.left_ll:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestSuccess(Object bean) {
        if (bean != null) {
            AppealResponse appealResponse = (AppealResponse) bean;
            final OrderInfo order_info = appealResponse.getOrder_info();
            final PayInfo pay_info = appealResponse.getPay_info();
            if (appealResponse != null) {

                String avatarUrl = null;
                String nickname = null;
                if (current_step.equals(OrderListsTabFragment.BUYER_APPEAL)) {
                    if (order_info != null) {
                        avatarUrl = order_info.getSeller_avatar();
                        nickname = order_info.getSeller_nickname();
                    }
                } else if (current_step.equals(OrderListsTabFragment.SELLER_APPEAL)) {
                    if (order_info != null) {
                        avatarUrl = order_info.getBuyer_avatar();
                        nickname = order_info.getBuyer_nickname();
                    }
                }
                if (order_info != null) {
                    tvTitleCenter.setText(order_info.getTitle());
                    String rmbStr = TraderUnpayActivity.formatRmb(order_info.getRmb(),"元");
                    tvRmb.setText(rmbStr);
                    tvRmb.setTextColor(textBlack);
                    tvTotal.setText(order_info.getTotal());
                    String priceStr=TraderUnpayActivity.formatRmb(order_info.getPrice(),"元/T");
                    tvPrice.setText(priceStr);
                    String pay_type = order_info.getPay_type();
                    if (pay_type.equals("1")) {
                        imgPayTypeBankCard.setVisibility(View.VISIBLE);
                        imgPayTypeAlipay.setVisibility(View.GONE);
                        imgPayTypeWechat.setVisibility(View.GONE);
                        tvPayType.setText("银行卡");
                    } else if (pay_type.equals("2")) {
                        imgPayTypeBankCard.setVisibility(View.GONE);
                        imgPayTypeAlipay.setVisibility(View.VISIBLE);
                        imgPayTypeWechat.setVisibility(View.GONE);
                        tvPayType.setText("支付宝");
                    } else if (pay_type.equals("3")) {
                        imgPayTypeBankCard.setVisibility(View.GONE);
                        imgPayTypeAlipay.setVisibility(View.GONE);
                        imgPayTypeWechat.setVisibility(View.VISIBLE);
                        tvPayType.setText("微信");
                    }
                    GlidImageManager.getInstance().loadCircleImg(this, avatarUrl, imgTraderAvatar, R.mipmap.head, R.mipmap.head);
                    tvTraderNickname.setText(nickname);
                    tvOrderSn.setText(order_info.getOrder_sn());
                    tvPayCode.setText(order_info.getPay_code());
                    tvCreatetime.setText(order_info.getCreatetime());
                    tvPaytime.setText(order_info.getPaytime());
                    tvAppealtime.setText(order_info.getAppealtime());
                }
            }
        }
    }

    @Override
    public void onRequestFailed(String msg) {
        ToastUtils.showCustomToast(msg,0);
    }

    private void doRequest() {
        if (traderPresenter == null) {
            traderPresenter = new TraderPresenter(new TraderModel());
            traderPresenter.attachView(this);
        }
        if (current_step.equals(OrderListsTabFragment.BUYER_APPEAL)) {
            traderPresenter.buyerAppeal(this, order_id);
        } else if (current_step.equals(OrderListsTabFragment.SELLER_APPEAL)) {
            traderPresenter.sellerAppeal(this, order_id);
        }
    }


    @Override
    protected void onDestroy() {
        if (traderPresenter != null) {
            traderPresenter.detachView();
        }
        super.onDestroy();
    }
}
