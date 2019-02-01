package com.jieshuizhibiao.waterindex.contract.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.OrderListsResponseBean;
import com.jieshuizhibiao.waterindex.beans.TradeDetailResponseBean;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;

/**
 * Created by WanghongHe on 2018/12/12 18:08.
 * 成交明细
 */

public class TradeDetailModel {

    public void getTradeDetail(BaseActivity activity, int id, final TradeDetailCallBack callBack){
        HashMap<String , Object> params = new HashMap<>();
        params.put("id",id);
        RetrofitFactory.getInstance().createService()
                .tradeDetail(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity>io())
                .subscribe(new BaseObserver(activity) {

                    /**
                     * 返回成功
                     *
                     * @param o
                     * @throws Exception
                     */
                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        Gson gson = new Gson();
                        TradeDetailResponseBean tradeDetailBean = gson.fromJson((JsonElement) o,new TypeToken<OrderListsResponseBean>() {}.getType());
                        callBack.success(tradeDetailBean);
                    }

                    /**
                     * 返回失败
                     *
                     * @param e
                     * @param isNetWorkError 是否是网络错误
                     * @throws Exception
                     */
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callBack.failed(HttpConfig.ERROR_MSG);
                            } else {
                                callBack.failed(e.getMessage());
                            }
                        } else {
                            callBack.failed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callBack.failed(msg);
                    }
                });
    }

    public interface TradeDetailCallBack{
        void success(TradeDetailResponseBean tradeDetailResponseBean);
        void failed(String msg);
    }

}
