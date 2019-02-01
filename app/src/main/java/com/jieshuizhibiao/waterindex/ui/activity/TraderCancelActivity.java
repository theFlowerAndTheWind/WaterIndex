package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.ListOrder;
import com.jieshuizhibiao.waterindex.beans.appeal.OrderInfo;
import com.jieshuizhibiao.waterindex.beans.cancel.CancelResponse;
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

public class TraderCancelActivity extends BaseActivity implements CommonViewImpl {

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
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_trader)
    TextView tvTrader;
    @BindView(R.id.img_trader_avatar)
    ImageView imgTraderAvatar;
    @BindView(R.id.tv_trader_nickname)
    TextView tvTraderNickname;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_createtime)
    TextView tvCreatetime;

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
        tvTitleCenter.setText("交易已取消");
        if (current_step.equals(OrderListsTabFragment.BUYER_CANCEL)) {
            tvTrader.setText("卖家");
        } else if (current_step.equals(OrderListsTabFragment.SELLER_CANCEL)) {

            tvTrader.setText("买家");
        }
        imgAppeal.setVisibility(View.GONE);
        imgCancel.setVisibility(View.VISIBLE);
        imgSucc.setVisibility(View.GONE);
        tvOrderAppeal.setVisibility(View.GONE);
        tvOrderCancel.setVisibility(View.VISIBLE);
        tvOrderSucc.setVisibility(View.GONE);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_trader_cancel);
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
            CancelResponse cancelResponse = (CancelResponse) bean;

            if (cancelResponse != null) {
                OrderInfo orderInfo = cancelResponse.getOrder_info();
                if (orderInfo != null) {
                    String avatarUrl = null;
                    String nickname = null;
                    if (current_step.equals(OrderListsTabFragment.BUYER_CANCEL)) {
                        avatarUrl = orderInfo.getSeller_avatar();
                        nickname = orderInfo.getSeller_nickname();
                    } else if (current_step.equals(OrderListsTabFragment.SELLER_CANCEL)) {
                        avatarUrl = orderInfo.getBuyer_avatar();
                        nickname = orderInfo.getBuyer_nickname();
                    }
                    tvTitleCenter.setText(orderInfo.getTitle());
                    String rmbStr = TraderUnpayActivity.formatRmb(orderInfo.getRmb(),"元");
                    tvRmb.setText(rmbStr);
                    tvRmb.setTextColor(textBlack);
                    tvTotal.setText(orderInfo.getTotal());
                    String priceStr=TraderUnpayActivity.formatRmb(orderInfo.getPrice(),"元/T");
                    tvPrice.setText(priceStr);
                    GlidImageManager.getInstance().loadCircleImg(this, avatarUrl, imgTraderAvatar, R.mipmap.head, R.mipmap.head);
                    tvTraderNickname.setText(nickname);
                    tvOrderSn.setText(orderInfo.getOrder_sn());
                    tvCreatetime.setText(orderInfo.getCreatetime());
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
        if (current_step.equals(OrderListsTabFragment.BUYER_CANCEL)) {
            traderPresenter.buyerCancel(this, order_id);
        } else if (current_step.equals(OrderListsTabFragment.SELLER_CANCEL)) {
            traderPresenter.sellerCancel(this, order_id);
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
