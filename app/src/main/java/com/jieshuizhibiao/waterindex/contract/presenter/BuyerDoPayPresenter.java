package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.BuyerDoPayModel;
import com.jieshuizhibiao.waterindex.contract.model.callback.CommonCallback;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

public class BuyerDoPayPresenter extends BasePresenter<CommonViewImpl> {
    private BuyerDoPayModel buyerDoPayModel;

    public BuyerDoPayPresenter(BuyerDoPayModel buyerDoPayModel) {
        this.buyerDoPayModel = buyerDoPayModel;
    }
    public void buyerDoPay(BaseActivity activity,long order_id,long pi_id,String pay_snapshot){
        if(buyerDoPayModel==null){
            buyerDoPayModel=new BuyerDoPayModel();
        }
        buyerDoPayModel.buyerDoPay(activity, order_id, pi_id, pay_snapshot, new CommonCallback() {
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
