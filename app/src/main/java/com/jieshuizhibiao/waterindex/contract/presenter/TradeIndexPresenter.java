package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.callback.CommonCallback;
import com.jieshuizhibiao.waterindex.contract.model.TradeIndexModel;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

public class TradeIndexPresenter extends BasePresenter<CommonViewImpl> {
    private TradeIndexModel tradeIndexModel;

    public TradeIndexPresenter(TradeIndexModel tradeIndexModel) {
        this.tradeIndexModel = tradeIndexModel;
    }

    public void getTradeIndex(BaseActivity activity, String type, String page, String page_size){
        if(tradeIndexModel==null){
            tradeIndexModel=new TradeIndexModel();
        }
        tradeIndexModel.getTradeIndex(activity, type, page, page_size, new CommonCallback() {
            @Override
            public void onRequestSuccess(Object bean) {
                if(mView!=null){
                    mView.onRequestSuccess(bean);
                }
            }

            @Override
            public void onRequestFailed(String msg) {
                if(mView!=null){
                    mView.onRequestFailed(msg);
                }
            }
        });

    }
}
