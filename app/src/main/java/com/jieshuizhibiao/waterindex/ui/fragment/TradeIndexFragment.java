package com.jieshuizhibiao.waterindex.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.beans.TradeIndex;
import com.jieshuizhibiao.waterindex.beans.TradeIndexBase;
import com.jieshuizhibiao.waterindex.contract.model.TradeIndexModel;
import com.jieshuizhibiao.waterindex.contract.presenter.TradeIndexPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.event.TradeIndexRefreshEvent;
import com.jieshuizhibiao.waterindex.event.LoginStatusChangedEvent;
import com.jieshuizhibiao.waterindex.ui.activity.AuthActivity;
import com.jieshuizhibiao.waterindex.ui.activity.LoginActivity;
import com.jieshuizhibiao.waterindex.ui.activity.UserOrderActivity;
import com.jieshuizhibiao.waterindex.ui.adapter.TradeIndexAdapter;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.utils.LogUtils;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TradeIndexFragment extends BaseFragment implements CommonViewImpl, TradeIndexAdapter.OnUserOrderListener {

    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.ll_warning)
    LinearLayout llWarning;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.btn_sell)
    Button btnSell;
    @BindView(R.id.xrv_buy)
    XRecyclerView xrvBuy;
    @BindView(R.id.xrv_sell)
    XRecyclerView xrvSell;
    private Unbinder unbinder;
    private String type = "2";//购买2 出售1
    private TradeIndexPresenter tradeIndexPresenter;
    private int buyCounter = 1;//buy返回数据页数统计
    private int sellCounter = 1;//sell返回数据页数统计
    private List<TradeIndex> buyList = new ArrayList<>();
    private List<TradeIndex> sellList = new ArrayList<>();
    private TradeIndexAdapter buyAdapter;
    private TradeIndexAdapter sellAdapter;
    private String is_login;//是否登录   "1"登录   "0"未登录
    private int is_auth;//是否注册  1是认证中 3是已认证 0是未认证
    private String tip;
    private AlertChainDialog dialog;
    private String loginStatus="false_token";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trade_index, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        tradeIndexPresenter = new TradeIndexPresenter(new TradeIndexModel());
        tradeIndexPresenter.attachView(this);
        showLoadingDialog();
        doRequest();

        initView();
        loginStatus=getLoginStatus();
        return rootView;
    }

    private void initView() {
        buyAdapter = new TradeIndexAdapter(getActivity(), buyList, "2", this);
        sellAdapter = new TradeIndexAdapter(getActivity(), sellList, "1", this);
        xrvBuy.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrvSell.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrvBuy.setPullRefreshEnabled(true);
        xrvSell.setPullRefreshEnabled(true);
        xrvBuy.setLoadingMoreEnabled(true);
        xrvSell.setLoadingMoreEnabled(true);
        xrvBuy.setArrowImageView(R.drawable.iconfont_downgrey);
        xrvSell.setArrowImageView(R.drawable.iconfont_downgrey);
        xrvBuy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                buyCounter = 0;
                buyList.clear();
                showLoadingDialog();
                doRequest();
                xrvBuy.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                doRequest();
                xrvBuy.loadMoreComplete();
            }
        });
        xrvSell.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                sellCounter = 0;
                sellList.clear();
//                showLoadingDialog();
                doRequest();
                xrvSell.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                doRequest();
                xrvSell.loadMoreComplete();
            }
        });
        xrvBuy.setAdapter(buyAdapter);
        xrvSell.setAdapter(sellAdapter);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.btn_buy, R.id.btn_sell, R.id.ll_warning})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_buy:
                type = "2";
                xrvBuy.setVisibility(View.VISIBLE);
                xrvSell.setVisibility(View.GONE);
                setBtnYellowBgShape(btnBuy);
                setBtnYellowBorderBgShape(btnSell);
                if (buyList.size() == 0) {
                    showLoadingDialog();
                    doRequest();
                }
                break;

            case R.id.btn_sell:
                type = "1";
                xrvBuy.setVisibility(View.GONE);
                xrvSell.setVisibility(View.VISIBLE);
                setBtnYellowBgShape(btnSell);
                setBtnYellowBorderBgShape(btnBuy);
                if (sellList.size() == 0) {
                    showLoadingDialog();
                    doRequest();
                }
                break;

            case R.id.ll_warning:
                if (is_login.equals("0")) {
                    jumpLogin();
                    break;
                }
                if (is_auth == 0) {
                    jump(AuthActivity.class, null);
                }

                break;

            default:
                break;
        }
    }

    private void jump(Class<?> clz, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.setClass(getActivity(), clz);
        startActivity(intent);
    }


    private void setBtnYellowBgShape(Button v) {
        v.setBackground(getResources().getDrawable(R.drawable.blue_bg_shape));
        v.setTextColor(getResources().getColor(R.color.white));
    }

    private void setBtnYellowBorderBgShape(Button v) {
        v.setBackground(getResources().getDrawable(R.drawable.blue_border_bg_shape));
        v.setTextColor(getResources().getColor(R.color.primary_yellow));
    }

    private void doRequest() {
        if (tradeIndexPresenter == null) {
            tradeIndexPresenter = new TradeIndexPresenter(new TradeIndexModel());
        }
        if (type.equals("2")) {
            tradeIndexPresenter.getTradeIndex(getBaseActivity(), type, String.valueOf(buyCounter), null);
        } else if (type.equals("1")) {
            tradeIndexPresenter.getTradeIndex(getBaseActivity(), type, String.valueOf(sellCounter), null);
        } else {
            tradeIndexPresenter.getTradeIndex(getBaseActivity(), type, null, null);
        }
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        if (tradeIndexPresenter != null) tradeIndexPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onRequestSuccess(Object bean) {
        TradeIndexBase tradeIndexBase = (TradeIndexBase) bean;
        if (bean != null) {
            is_login = tradeIndexBase.getIs_login();
            is_auth = tradeIndexBase.getIs_auth();
            SPUtil.insert(getActivity(),SPUtil.IS_AUTH,is_auth);
            tip = tradeIndexBase.getTip();
            if(is_auth==1){
                llWarning.setEnabled(false);
                tvGo.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(tradeIndexBase.getTip())) {
                llWarning.setVisibility(View.VISIBLE);
                tvTip.setText(tradeIndexBase.getTip());
            } else {
                llWarning.setVisibility(View.GONE);
            }
            if (type.equals("2")) {
                buyCounter += 1;
                List<TradeIndex> list2 = tradeIndexBase.getList();
                buyList.addAll(list2);
                buyAdapter.notifyDataSetChanged();


            } else if (type.equals("1")) {
                sellCounter += 1;
                List<TradeIndex> list1 = tradeIndexBase.getList();
                sellList.addAll(list1);
                sellAdapter.notifyDataSetChanged();
            }
        }
        dismissLoadingDialog();
        loginStatus=getLoginStatus();
    }

    @Override
    public void onRequestFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);
    }

    public void onEventMainThread(LoginStatusChangedEvent event) {
        if (event != null) {
            if( event.getMsg().equals("login_status_changed_main_trade_index_reconnect")){
                buyCounter = 1;
                buyList.clear();
                sellCounter = 1;
                sellList.clear();
                doRequest();
            }}
    }

    public void onEventMainThread(TradeIndexRefreshEvent event) {
        if (event != null) {
            if (event.getTitle().equals("creat_order_success")) {
                if(type=="2"){
                    buyCounter=1;
                    showLoadingDialog();
                    buyList.clear();
                    doRequest();
                }else if(type=="1"){
                    sellCounter=1;
                    showLoadingDialog();
                    sellList.clear();
                    doRequest();
                }
                showDialog("提示", "下单成功！", "确定");
            } else if (event.getTitle().equals("creat_order_failed")) {
                if (!TextUtils.isEmpty(event.getMsg())) showDialog("提示", event.getMsg(), "确定");
                else showDialog("提示", "下单失败！", "确定");
            }/*else if (event.getTitle().equals("time_too_late")) {
                showDialog("提示", "非交易时间，禁止交易", "queding");
            }*/
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreateOrder(TradeIndex tradeIndex) {
        is_auth=(int)SPUtil.get(getActivity(),SPUtil.IS_AUTH,0);
        if (is_login.equals("0")) {
            showDialog("交易信息", tip, "去登陆");
            return;
        }
        if (is_auth == 0) {
            showDialog("交易信息", tip, "去认证");
            return;
        } else if (is_auth == 1) {
            showDialog("交易信息", tip, "知道了");
            return;
        }
        if (is_login.equals("1") && is_auth == 3) {
            Intent intent = new Intent(getActivity(), UserOrderActivity.class);
            intent.putExtra("trade_index", tradeIndex);
            intent.putExtra("type", type);
            jump(UserOrderActivity.class, intent);
            getActivity().overridePendingTransition(R.anim.actionsheet_dialog_in, 0);
        }else{
            ToastUtils.showCustomToastMsg("确保您已经登录且已完善必要的交易信息！",150);
        }

    }

    private void showDialog(final String title, String msg, final String positive) {
        if (dialog == null) {
            dialog = new AlertChainDialog(getActivity());
        }
        dialog.builder().setCancelable(true);
        dialog.setTitle(title)
                .setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (positive.equals("知道了") || title.equals("提示")) {
                            dialog.dismiss();
                        } else if (positive.equals("去认证")) {
                            jump(AuthActivity.class, null);
                        } else if (positive.equals("去登陆")) {
                            jumpLogin();
                        }
                    }
                })
                .show();

    }

    private void jumpLogin() {
        Intent intent = new Intent();
        intent.putExtra("target", "main_trade_index");
        jump(LoginActivity.class, intent);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {//刷新
            final String currentStatus = getLoginStatus();
            if(!currentStatus.equals(loginStatus)){
                buyCounter = 1;
                buyList.clear();
                sellCounter = 1;
                sellList.clear();
                showLoadingDialog();
                doRequest();
            }
        }
    }

    private String getLoginStatus(){
        boolean isLogin= (boolean) SPUtil.get(getActivity(),SPUtil.IS_LOGIN,false);
        String token=(String)SPUtil.get(getActivity(),SPUtil.TOKEN,"token");
        return new StringBuilder().append(isLogin).append("_").append(token).toString();
    }


}
