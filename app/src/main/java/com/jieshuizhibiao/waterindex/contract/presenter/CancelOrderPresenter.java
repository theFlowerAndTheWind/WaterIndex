package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.CancelOrderModel;
import com.jieshuizhibiao.waterindex.contract.model.callback.CancelOrderCallback;
import com.jieshuizhibiao.waterindex.contract.view.CancelOrderViewImpl;

public class CancelOrderPresenter extends BasePresenter<CancelOrderViewImpl> {
    private CancelOrderModel model;

    public CancelOrderPresenter(CancelOrderModel model) {
        this.model = model;
    }

    public void cancelOrder(BaseActivity activity, long order_id){
        if(model==null){
            model=new CancelOrderModel();
        }
        model.cancelOrder(activity, order_id, new CancelOrderCallback() {
            @Override
            public void onCancleSucc(Object bean) {
                if(mView!=null){
                    mView.onCancelSucc(bean);
                }
            }

            @Override
            public void onCancleFail(String msg) {
                if(mView!=null){
                    mView.onCancelFail(msg);
                }
            }
        });
    }



}
