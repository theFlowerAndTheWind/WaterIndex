package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.SellerCheckoutModel;
import com.jieshuizhibiao.waterindex.contract.model.callback.ThirdRequestCallback;
import com.jieshuizhibiao.waterindex.contract.view.ThirdRequestViewImpl;

public class SellerCheckoutPresenter extends BasePresenter<ThirdRequestViewImpl> {
    private SellerCheckoutModel sellerCheckoutModel;

    public SellerCheckoutPresenter(SellerCheckoutModel sellerCheckoutModel) {
        this.sellerCheckoutModel = sellerCheckoutModel;
    }

    public void sellerCheckout(BaseActivity activity, long order_id, String safe_pw){
        if(sellerCheckoutModel==null){
            sellerCheckoutModel=new SellerCheckoutModel();
        }
        sellerCheckoutModel.sellerCheckout(activity, order_id, safe_pw, new ThirdRequestCallback() {
            @Override
            public void onThirdRequestSucc(Object o) {
                if(mView!=null){
                    mView.onThirdRequestSucc(o);
                }
            }

            @Override
            public void onThirdRequestFail(String msg) {
                if(mView!=null){
                    mView.onThirdRequestFail(msg);
                }
            }
        });
    }
}
