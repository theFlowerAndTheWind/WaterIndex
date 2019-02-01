package com.jieshuizhibiao.waterindex.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.ListOrder;
import com.jieshuizhibiao.waterindex.contract.model.OrderListsModel;
import com.jieshuizhibiao.waterindex.contract.presenter.OrderListsPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.event.ChangeOrderStatusEvent;
import com.jieshuizhibiao.waterindex.event.ShouldRefreshEvent;
import com.jieshuizhibiao.waterindex.ui.activity.TestActivity;
import com.jieshuizhibiao.waterindex.ui.activity.TraderAppealActivity;
import com.jieshuizhibiao.waterindex.ui.activity.TraderCancelActivity;
import com.jieshuizhibiao.waterindex.ui.activity.TraderPaidActivity;
import com.jieshuizhibiao.waterindex.ui.activity.TraderSuccActivity;
import com.jieshuizhibiao.waterindex.ui.activity.TraderUnpayActivity;
import com.jieshuizhibiao.waterindex.ui.adapter.OrderListsAdapter;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 全部，进行中，已完成，已取消，申诉处理共用
 */
public class OrderListsTabFragment extends BaseFragment implements CommonViewImpl, OrderListsAdapter.onItemClickedListener, XRecyclerView.LoadingListener {

    Unbinder unbinder;
    @BindView(R.id.xrv)
    XRecyclerView xrv;

    @BindView(R.id.relative_hint)
    RelativeLayout rlHint;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    private String title = "全部";
    private String type = "0";//0 全部 1 进行中 2 已完成 3 已取消 4 申诉处理
    private String page = "1";//页码
    private int intPage = 1;//loadMore执行成功时+1    refresh时归位至1
    private String page_size = "10";//每页数量
    private OrderListsAdapter orderListsAdapter;
    private List<ListOrder> list = new LinkedList<>();

    private boolean isLoadMore = false;//如果执行loadMore进行网络请求，该值设置为true,list累加,请求成功后必须归位至false;
    private boolean isRefresh;//数据是否刷新标志 true:表示数据需要刷新，list数据clear   false：加载更多或初次加载等情况
    private boolean isVisiable = false;
    private int isFirstVisable = 0;//懒加载使用  为1时进行网络请求

    private OrderListsPresenter orderListsPresenter = null;
    private BaseActivity baseActivity;//懒加载需要需用
    public static final String BUYER_UNPAY = "buyerUnpay";
    public static final String BUYER_PAID = "buyerPaid";
    public static final String BUYER_APPEAL = "buyerAppeal";
    public static final String BUYER_SUCC = "buyerSucc";
    public static final String BUYER_CANCEL = "cancelOrder";
    public static final String SELLER_UNPAY = "sellerUnpay";
    public static final String SELLER_PAID = "sellerPaid";
    public static final String SELLER_APPEAL = "sellerAppeal";
    public static final String SELLER_SUCC = "sellerSucc";
    public static final String SELLER_CANCEL = "sellerCancel";

    public static final String LISTORDER = "ListOrder";


    /**
     * must perform before onCreateView/doRequest
     *
     * @param title
     */
    public void setTitle(String title, BaseActivity activity) {
        this.title = title;
        this.baseActivity = activity;
        selectType();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_lists_tab, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        if (orderListsPresenter == null) {
            orderListsPresenter = new OrderListsPresenter(new OrderListsModel());
            orderListsPresenter.attachView(this);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * viewpager滑动时，仅保留当前Fragment及其左右两侧两个Fragment,共三个Fragment
         *                 最左侧Fragment显示时，其右侧一个Fragment也保留，共2个
         *                 最右侧Fragment显示时，其左侧一个Fragment也保留，共2个
         *                 其他已加载过的Fragment走生命周期onResume
         *
         *
         * 故有以下判断：fragment可见  &&  接收到订单详情页Event
         * 正常滑动情况不执行网络请求
         */
        if (isVisiable && isRefresh) {
            doRequest();
        }
    }

    private void init() {
        orderListsAdapter = new OrderListsAdapter(getActivity(), list, this);
        xrv.setArrowImageView(R.drawable.iconfont_downgrey);
        xrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrv.setAdapter(orderListsAdapter);
        xrv.setLoadingMoreEnabled(true);
        xrv.setPullRefreshEnabled(true);
        xrv.setLoadingListener(this);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @Override
    public void onItemClicked(ListOrder order) {
        boolean isLogin=(boolean) SPUtil.get(getActivity(),SPUtil.IS_LOGIN,false);
        if(!isLogin){
            ToastUtils.showCustomToastMsg("请重新登录",150);
            return;
        }
        if (order != null) {
            final String next_step = order.getNext_step();

            Intent intent = new Intent(baseActivity, TestActivity.class);
            intent.putExtra(LISTORDER, order);
            if (next_step.equals(BUYER_UNPAY) || next_step.equals(SELLER_UNPAY)) {
                jump(TraderUnpayActivity.class, intent);
            } else if (next_step.equals(BUYER_PAID) || next_step.equals(SELLER_PAID)) {
                jump(TraderPaidActivity.class, intent);
            } else if (next_step.equals(BUYER_APPEAL) || next_step.equals(SELLER_APPEAL)) {
                jump(TraderAppealActivity.class, intent);
            } else if (next_step.equals(BUYER_SUCC) || next_step.equals(SELLER_SUCC)) {
                jump(TraderSuccActivity.class, intent);
            } else if (next_step.equals(BUYER_CANCEL) || next_step.equals(SELLER_CANCEL)) {
                jump(TraderCancelActivity.class, intent);
            }
        }
    }

    @Override
    public void onRequestSuccess(Object bean) {
        List<ListOrder> datas = (List<ListOrder>) bean;
        if (datas != null) {
            if (list != null && list.size() == 0 && datas.size() == 0) {//第一次执行dorequest
                xrv.setVisibility(View.GONE);
                rlHint.setVisibility(View.VISIBLE);
                tvDetail.setText("您还没有相关订单，赶快下单吧！");
            } else {
                xrv.setVisibility(View.VISIBLE);
                rlHint.setVisibility(View.GONE);
                if (isRefresh) list.clear();
                list.addAll(datas);
                //sort
                LinkedList<ListOrder>temp=new LinkedList<>();
                Map<Long,ListOrder>map=new TreeMap<>(new Comparator<Long>() {
                    @Override
                    public int compare(Long o1, Long o2) {
                        return o1.compareTo(o2);
                    }
                });
                for (ListOrder listOrder:list){
                    final String createtime = listOrder.getCreatetime();
                    long longCreatetime= Util.time2MilliseSecond(createtime);
                    map.put(longCreatetime,listOrder);
                }
                for (Map.Entry<Long,ListOrder> entry:map.entrySet()){
                    temp.addFirst(entry.getValue());
                }
                list.clear();
                list.addAll(temp);
                if (datas.size() > 0) intPage += 1;
                orderListsAdapter.notifyDataSetChanged();
            }
        }
        //如果因为加载更多进行网络请求，请求完毕后isLoadMore归位至false;
        if (isLoadMore) {
            isLoadMore = false;
        }
        isRefresh = false;//每次执行完毕后归位为不刷新
        dismissLoadingDialog();
    }

    @Override
    public void onRequestFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg, 0);
        //如果因为加载更多进行网络请求，请求完毕后isLoadMore归位至false;
        if (isLoadMore) {
            isLoadMore = false;
        }
        isRefresh = false;

    }

    private void selectType() {
        switch (title) {
            case "全部":
                type = "0";
                break;
            case "进行中":
                type = "1";
                break;
            case "已完成":
                type = "2";
                break;
            case "已取消":
                type = "3";
                break;
            case "申诉处理":
                type = "4";
                break;
            default:
                break;
        }
    }


    /**
     * 1、onCreateView创建时请求
     * 2、可见、且收到订单详情页event时请求
     * 3.以防万一，在setUserVisibleHint执行2
     * 4.下拉刷新
     * 5.上垃加载更多
     */
    private void doRequest() {
        if (!isLoadMore) showLoadingDialog();
        if (orderListsPresenter == null) {
            orderListsPresenter = new OrderListsPresenter(new OrderListsModel());
            orderListsPresenter.attachView(this);
        }
        page = String.valueOf(intPage);
//        orderListsPresenter.getOrderList(getBaseActivity(), type, page, page_size);//懒加载时拿到的activity为null
        orderListsPresenter.getOrderList(baseActivity, type, page, page_size);
    }

    private void jump(Class<?> cls, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.setClass(baseActivity, cls);
        startActivity(intent);
    }


    public void onEventMainThread(ChangeOrderStatusEvent event) {
        if (event != null) {
            String from = event.getFrom();
            String msg = event.getMsg();
            if ("TraderUnpayActivity,TraderPaidActivity,PayFloatActivity,PayActivity".contains(from)) {
                if ("buyer_cancel,trader_do_appeal,seller_checkout,buyer_do_pay".contains(msg)) {
                    isRefresh = true;//所有页面都要刷新
                    intPage = 1;
                }
//                if ("全部，进行中".contains(title) && isVisiable) {
//                    doRequest();//立即刷新
//                    baseActivity.dismissLoadingDialog();
//                }
            }

        }
    }

    @Override
    public void showLoadingDialog() {
        if (isVisiable) {
//            super.showLoadingDialog();//懒加载时直接使用fragment的showloadingDialog方法报异常。跟生命周期有关
            baseActivity.showLoadingDialog();
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (isVisiable) {
            baseActivity.dismissLoadingDialog();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisiable = isVisibleToUser;
        if (isRefresh && intPage == 1) {
            doRequest();
            baseActivity.dismissLoadingDialog();
            return;
        }
        if (isVisiable) {
            isFirstVisable += 1;
            //用户滑动到该fragment显示时，且该fragment接收到刷新event
            if (isRefresh) {
                doRequest();
                return;
            }
            //懒加载
            if (isFirstVisable == 1) {
                doRequest();
            }
        }

    }


    @Override
    public void onRefresh() {
        if (isVisiable) {
            isRefresh = true;
            intPage = 1;
            doRequest();
            xrv.refreshComplete();
        }
    }

    @Override
    public void onLoadMore() {
        if (isVisiable) {
            isLoadMore = true;
//            intPage += 1;
            doRequest();
            xrv.loadMoreComplete();
        }
    }

    @Override
    public void onDestroy() {
        if (orderListsPresenter != null) orderListsPresenter.detachView();
        unbinder.unbind();
        super.onDestroy();

    }
}
