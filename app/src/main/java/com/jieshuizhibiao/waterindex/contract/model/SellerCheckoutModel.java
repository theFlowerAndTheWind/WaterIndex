package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.model.callback.ThirdRequestCallback;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;

public class SellerCheckoutModel {

    public void sellerCheckout(BaseActivity activity, long order_id, String safe_pw, final ThirdRequestCallback callback){
        HashMap<String,Object>params=new HashMap<>();
        params.put("order_id",String.valueOf(order_id));
        params.put("safe_pw",safe_pw);
        RetrofitFactory.getInstance().createService()
                .sellerCheckout(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity>io())
                .subscribe(new BaseObserver(activity) {

                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        callback.onThirdRequestSucc(o);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onThirdRequestFail(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onThirdRequestFail(e.getMessage());
                            }
                        } else {
                            callback.onThirdRequestFail("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callback.onThirdRequestFail(msg);
                    }
                });
    }

}
