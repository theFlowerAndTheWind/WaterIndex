package com.jieshuizhibiao.waterindex.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.beans.ListTradeResponseBean;
import com.jieshuizhibiao.waterindex.beans.request.DelTradeReqParams;
import com.jieshuizhibiao.waterindex.beans.request.ListTradeReqParams;
import com.jieshuizhibiao.waterindex.contract.presenter.DelTradePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.ListTradePresenter;
import com.jieshuizhibiao.waterindex.contract.view.DelTradeViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.ListTradeViewImpl;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.ui.activity.TransactionDemandActivity;
import com.jieshuizhibiao.waterindex.ui.activity.TranscationReleaseBuyOrSellActivity;
import com.jieshuizhibiao.waterindex.ui.adapter.TransactionAdapter;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.utils.TimeUtils;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by songxiaotao on 2018/12/25.
 * Class Note:全部订单
 */

public class TranscationAllFragment extends BaseFragment implements ListTradeViewImpl, DelTradeViewImpl {

    @BindView(R.id.transaction_demand_all)
    XRecyclerView transcationAllList;
    @BindView(R.id.relative_hint)
    RelativeLayout relativeHint;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    private TransactionDemandActivity demandActivity;
    private AlertChainDialog alertChainDialog;
    private Unbinder unbinder;
    private ListTradePresenter listTradePresenter;
    private DelTradePresenter delTradePresenter;
    private int currentPage = 1, pageSize = 10;
    private TransactionAdapter transactionAdapter;
    private List<ListTradeResponseBean.TradeList> tradeListArrayList = new ArrayList<>();

    private int[] pay_info_list;
    public static final String TYPE = "type_txt";
    public static final String EXTRA_BUY = "extra_buy";
    public static final String EXTRA_SELL = "extra_sell";
    public static final String EXTRA_PAY_INFO_LIST ="extra_pay_info_list";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTradePresenter = new ListTradePresenter();
        listTradePresenter.attachView(this);
        delTradePresenter = new DelTradePresenter();
        delTradePresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transcation_all, container, false);
        ButterKnife.bind(this, rootView);
        alertChainDialog = new AlertChainDialog(getBaseActivity());
        initView();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initView() {

        transactionAdapter = new TransactionAdapter(getBaseActivity(), sortData(tradeListArrayList), new TransactionAdapter.TransactionListener() {
            @Override
            public void onLowerShelfClick(String id) {
                doRequestDel(id);
            }
        });
        transcationAllList.setArrowImageView(R.drawable.iconfont_downgrey);
        transcationAllList.setLayoutManager(new LinearLayoutManager(getActivity()));
        transcationAllList.addItemDecoration(new SpaceItemDecoration(Util.px2px(getBaseActivity(), 10, 1334)));
        transcationAllList.setAdapter(transactionAdapter);
        transcationAllList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;//后期数据需要分页时再用
                doReuqest();
            }

            @Override
            public void onLoadMore() {
//                currentPage++;
                doReuqest();
            }
        });
    }

    private List<ListTradeResponseBean.TradeList> sortData(List<ListTradeResponseBean.TradeList> mList) {
        Collections.sort(mList, new Comparator<ListTradeResponseBean.TradeList>() {

            @Override
            public int compare(ListTradeResponseBean.TradeList lhs, ListTradeResponseBean.TradeList rhs) {
                Date date1 = TimeUtils.stringToDate(lhs.getAdd_time());
                Date date2 = TimeUtils.stringToDate(rhs.getAdd_time());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date2.after(date1)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.demandActivity = (TransactionDemandActivity) context;
        if (context instanceof TransactionDemandActivity) {

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.demandActivity = (TransactionDemandActivity) activity;
        if (activity instanceof TransactionDemandActivity) {

        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doReuqest();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            doReuqest();
        }
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doReuqest();
    }

    public void doReuqest() {
        ListTradeReqParams params = new ListTradeReqParams();
        params.setType(HttpConfig.TRANSCATION_RELEASE_All);
        params.setPage(String.valueOf(currentPage));
        params.setPage_size(String.valueOf(pageSize));
        listTradePresenter.getListTrade(getBaseActivity(), params);
        showLoadingDialog();
    }

    public void doRequestDel(String sn) {
        DelTradeReqParams params = new DelTradeReqParams();
        params.setId(sn);
        delTradePresenter.delTrade(getBaseActivity(), params);
    }

    @OnClick({R.id.btn_release_transaction_buy, R.id.btn_release_transaction_sell})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_release_transaction_buy:
                Bundle bundle=new Bundle();
                bundle.putString(TYPE, EXTRA_BUY);
                bundle.putIntArray(EXTRA_PAY_INFO_LIST,pay_info_list);
                jumpActivity(bundle,TranscationReleaseBuyOrSellActivity.class);
                break;
            case R.id.btn_release_transaction_sell:
                Bundle b=new Bundle();
                b.putString(TYPE, EXTRA_SELL);
                b.putIntArray(EXTRA_PAY_INFO_LIST,pay_info_list);
                jumpActivity(b,TranscationReleaseBuyOrSellActivity.class);
                break;
            default:
                break;
        }
    }
    public void jumpActivity(Bundle bundle,Class<?>cls){
        Intent intent=new Intent(getActivity(),cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onListTradeSuccess(ListTradeResponseBean bean) {
        if (bean != null) {
            pay_info_list = bean.getPay_info_list();//该数据下一页需要
        }
    }

    @Override
    public void onloadMoreListTrade(List<ListTradeResponseBean.TradeList> tradeLists) {
        dismissLoadingDialog();
        if(tradeLists!=null&&tradeLists.size()>0){
            currentPage+=1;
        }
        tradeListArrayList.addAll(tradeLists);
        sortData(tradeListArrayList);
        transactionAdapter.notifyDataSetChanged();
        transcationAllList.loadMoreComplete();

        if (tradeListArrayList.size() <= 0) {
            transcationAllList.setVisibility(View.GONE);
            relativeHint.setVisibility(View.VISIBLE);
            tvDetail.setText("暂无内容！");
            tvDetail.setTextColor(getResources().getColor(R.color.text_black));
            tvDetail.setEnabled(false);
        } else {
            transcationAllList.setVisibility(View.VISIBLE);
            relativeHint.setVisibility(View.GONE);
            tvDetail.setEnabled(false);
        }
    }

    @Override
    public void onRefreshListTrade(List<ListTradeResponseBean.TradeList> tradeLists) {
        dismissLoadingDialog();
        if(tradeLists!=null&&tradeLists.size()>0){
            currentPage+=1;
        }
        tradeListArrayList.clear();
        tradeListArrayList.addAll(tradeLists);
        sortData(tradeListArrayList);
        transactionAdapter.notifyDataSetChanged();
        transcationAllList.refreshComplete();

        if (tradeListArrayList.size() <= 0) {
            transcationAllList.setVisibility(View.GONE);
            relativeHint.setVisibility(View.VISIBLE);
            tvDetail.setText("暂无内容！");
            tvDetail.setTextColor(getResources().getColor(R.color.text_black));
            tvDetail.setEnabled(false);
        } else {
            transcationAllList.setVisibility(View.VISIBLE);
            relativeHint.setVisibility(View.GONE);
            tvDetail.setEnabled(false);
        }

    }

    @Override
    public void onLoadListTradeError(boolean isRefresh, String msg) {
        if (isRefresh) {
            transcationAllList.refreshComplete();
        } else {
            transcationAllList.loadMoreComplete();
        }
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg, 0);
    }

    @Override
    public void onDelTradeSuccess() {
        ToastUtils.showCustomToast("下架成功", 1);
    }

    @Override
    public void onDelTradeFailed(String msg) {
        ToastUtils.showCustomToast(msg, 0);
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            outRect.bottom = space;
            if (parent.getChildPosition(view) != 0) {
                outRect.top = space;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listTradePresenter != null) {
            listTradePresenter.detachView();
        }
        if (delTradePresenter != null) {
            delTradePresenter.detachView();
        }
    }
}
