package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.TradeIndexBase;
import com.jieshuizhibiao.waterindex.contract.model.callback.CommonCallback;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;

public class TradeIndexModel {

    public void getTradeIndex(BaseActivity activity,String type,String page, String page_size,final CommonCallback callback){
        HashMap<String,Object>params=new HashMap<>();
        if(!TextUtils.isEmpty(type)){
            params.put("type",type);
        }
        if(!TextUtils.isEmpty(type)){
            params.put("page",page);
        }
        if (!TextUtils.isEmpty(type)) {
            params.put("page_size", page_size);
        }
        RetrofitFactory.getInstance().createService()
                .tradeIndex(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity<TradeIndexBase>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<TradeIndexBase>>io())
                .subscribe(new BaseObserver<TradeIndexBase>(activity) {
                    @Override
                    protected void onSuccess(TradeIndexBase tradeIndexBase) throws Exception {
                        callback.onRequestSuccess(tradeIndexBase);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callback.onRequestFailed(msg);
                    }
                });

    }
}
