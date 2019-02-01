package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.PayMentResponseBean;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:支付方式
 */

public class PayMentTypeModel {

    public void payMentType(BaseActivity activity,final PayMentTypeCallBack callBack){
        RetrofitFactory.getInstance().createService()
                .payMentType(RequestUtil.getRequestBeanBody(null,false))
                .compose(activity.<BaseEntity<PayMentResponseBean>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<PayMentResponseBean>>io())
                .subscribe(new BaseObserver<PayMentResponseBean>(activity) {
                    @Override
                    protected void onSuccess(PayMentResponseBean payMentResponseBean) throws Exception {
                        callBack.success(payMentResponseBean);
                    }

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

    public interface PayMentTypeCallBack{
        void success(PayMentResponseBean payMentResponseBean);
        void failed(String msg);
    }
}
