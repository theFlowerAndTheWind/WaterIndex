package com.jieshuizhibiao.waterindex.ui.fragment;

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
import com.jieshuizhibiao.waterindex.ui.adapter.TransactionAdapter;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by songxiaotao on 2018/12/25.
 * Class Note:出售
 */

public class TranscationSellFragment extends BaseFragment implements ListTradeViewImpl,DelTradeViewImpl {

    @BindView(R.id.transaction_demand_sell)
    XRecyclerView transcationSellList;
    @BindView(R.id.relative_hint)
    RelativeLayout relativeHint;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    private AlertChainDialog alertChainDialog;
    private Unbinder unbinder;
    private ListTradePresenter listTradePresenter;
    private DelTradePresenter delTradePresenter;
    private int currentPage = 1, pageSize = 10;
    private TransactionAdapter transactionAdapter;
    private List<ListTradeResponseBean.TradeList> tradeListArrayList = new ArrayList<>();

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
        View rootView=inflater.inflate(R.layout.fragment_transcation_sell,container,false);
        ButterKnife.bind(this, rootView);
        alertChainDialog = new AlertChainDialog(getBaseActivity());
        initView();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initView() {

        transactionAdapter = new TransactionAdapter(getBaseActivity(), tradeListArrayList, new TransactionAdapter.TransactionListener() {
            @Override
            public void onLowerShelfClick(String id) {
                doRequestDel(id);
            }
        });
        transcationSellList.setArrowImageView(R.drawable.iconfont_downgrey);
        transcationSellList.setLayoutManager(new LinearLayoutManager(getActivity()));
        transcationSellList.addItemDecoration(new SpaceItemDecoration(Util.px2px(getBaseActivity(), 10, 1334)));
        transcationSellList.setAdapter(transactionAdapter);
        transcationSellList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;//后期数据需要分页时再用
                doReuqest();
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                doReuqest();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doReuqest();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            doReuqest();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doReuqest();
    }

    public void doReuqest(){
        ListTradeReqParams params = new ListTradeReqParams();
        params.setType(HttpConfig.TRANSCATION_RELEASE_SELL);
        params.setPage(String.valueOf(currentPage));
        params.setPage_size(String.valueOf(pageSize));
        listTradePresenter.getListTrade(getBaseActivity(),params);
        showLoadingDialog();
    }

    public void doRequestDel(String sn){
        DelTradeReqParams params = new DelTradeReqParams();
        params.setId(sn);
        delTradePresenter.delTrade(getBaseActivity(),params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listTradePresenter!=null){
            listTradePresenter.detachView();
        }
        if (delTradePresenter!=null){
            delTradePresenter.detachView();
        }
    }

    @Override
    public void onListTradeSuccess(ListTradeResponseBean bean) {

    }

    @Override
    public void onloadMoreListTrade(List<ListTradeResponseBean.TradeList> tradeLists) {
        dismissLoadingDialog();
        tradeListArrayList.addAll(tradeLists);
        transactionAdapter.notifyDataSetChanged();
        transcationSellList.loadMoreComplete();

        if (tradeListArrayList.size() <= 0) {
            transcationSellList.setVisibility(View.GONE);
            relativeHint.setVisibility(View.VISIBLE);
            tvDetail.setText("暂无内容！");
            tvDetail.setTextColor(getResources().getColor(R.color.text_black));
            tvDetail.setEnabled(false);
        } else {
            transcationSellList.setVisibility(View.VISIBLE);
            relativeHint.setVisibility(View.GONE);
            tvDetail.setEnabled(false);
        }
    }

    @Override
    public void onRefreshListTrade(List<ListTradeResponseBean.TradeList> tradeLists) {
        dismissLoadingDialog();
        tradeListArrayList.clear();
        tradeListArrayList.addAll(tradeLists);
        transactionAdapter.notifyDataSetChanged();
        transcationSellList.refreshComplete();

        if (tradeListArrayList.size() <= 0) {
            transcationSellList.setVisibility(View.GONE);
            relativeHint.setVisibility(View.VISIBLE);
            tvDetail.setText("暂无内容！");
            tvDetail.setTextColor(getResources().getColor(R.color.text_black));
            tvDetail.setEnabled(false);
        } else {
            transcationSellList.setVisibility(View.VISIBLE);
            relativeHint.setVisibility(View.GONE);
            tvDetail.setEnabled(false);
        }

    }

    @Override
    public void onLoadListTradeError(boolean isRefresh, String msg) {
        if (isRefresh) {
            transcationSellList.refreshComplete();
        } else {
            transcationSellList.loadMoreComplete();
        }
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);
    }

    @Override
    public void onDelTradeSuccess() {
        ToastUtils.showCustomToast("下架成功",1);
    }

    @Override
    public void onDelTradeFailed(String msg) {
        ToastUtils.showCustomToast(msg,0);
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

}
