package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.model.callback.CancelOrderCallback;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;

public class CancelOrderModel {

    public void cancelOrder(BaseActivity activity, long order_id, final CancelOrderCallback callback){
        RetrofitFactory.getInstance().createService()
                .buyerCancle(RequestUtil.getRequestHashBody(initParams(order_id),false))
                .compose(activity.<BaseEntity>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity>io())
                .subscribe(new BaseObserver(activity) {
                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        callback.onCancleSucc(o);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onCancleFail(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onCancleFail(e.getMessage());
                            }
                        } else {
                            callback.onCancleFail("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onCancleFail(msg);
                    }
                });
    }

    private HashMap<String, Object> initParams(long order_id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("order_id", String.valueOf(order_id));
        return params;
    }

}
