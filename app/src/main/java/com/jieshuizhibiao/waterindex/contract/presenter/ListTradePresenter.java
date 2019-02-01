package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.ListTradeResponseBean;
import com.jieshuizhibiao.waterindex.beans.request.ListTradeReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.ListTradeModel;
import com.jieshuizhibiao.waterindex.contract.view.ListTradeViewImpl;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class ListTradePresenter extends BasePresenter<ListTradeViewImpl> {

    private ListTradeModel listTradeModel;

    public ListTradePresenter(){}

    public ListTradePresenter(ListTradeModel listTradeModel){
        this.listTradeModel = listTradeModel;
    }

    public void getListTrade(BaseActivity activity, final ListTradeReqParams params){
        if (listTradeModel == null){
            listTradeModel = new ListTradeModel();
        }
        listTradeModel.getListTrade(activity, params, new ListTradeModel.ListTradeCallBack() {
            @Override
            public void success(ListTradeResponseBean listTradeResponseBean) {
                if (mView!=null){
                    if (Integer.parseInt(params.getPage()) == 1){
                        mView.onRefreshListTrade(listTradeResponseBean.getTrade_list());
                    }else {
                        mView.onloadMoreListTrade(listTradeResponseBean.getTrade_list());
                    }
                    mView.onListTradeSuccess(listTradeResponseBean);
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    if (Integer.parseInt(params.getPage()) == 1) {
                        mView.onLoadListTradeError(true, msg);
                    } else {
                        mView.onLoadListTradeError(false, msg);
                    };
                }

            }
        });
    }
}
