package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.model.callback.SecondRequestCallback;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;

/**
 * 卖家申诉，卖家申诉
 */
public class TraderDoAppealModel {

    public void sellerDoAppeal(BaseActivity activity, long order_id, String detail, final SecondRequestCallback callback){
        HashMap<String,Object> params=new HashMap<>();
        params.put("order_id",String.valueOf(order_id));
        params.put("detail",detail);
        RetrofitFactory.getInstance().createService()
                .sellerDoAppeal(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity>io())
                .subscribe(new BaseObserver(activity) {
                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        callback.onSecondRequstSuccess(o);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onSecondRequstFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onSecondRequstFailed(e.getMessage());
                            }
                        } else {
                            callback.onSecondRequstFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callback.onSecondRequstFailed(msg);
                    }
                });
    }

    public void buyerDoAppeal(BaseActivity activity, long order_id, String detail, final SecondRequestCallback callback){
        HashMap<String,Object> params=new HashMap<>();
        params.put("order_id",String.valueOf(order_id));
        params.put("detail",detail);
        RetrofitFactory.getInstance().createService()
                .buyerDoAppeal(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity>io())
                .subscribe(new BaseObserver(activity) {
                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        callback.onSecondRequstSuccess(o);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onSecondRequstFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onSecondRequstFailed(e.getMessage());
                            }
                        } else {
                            callback.onSecondRequstFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callback.onSecondRequstFailed(msg);
                    }
                });
    }
}
