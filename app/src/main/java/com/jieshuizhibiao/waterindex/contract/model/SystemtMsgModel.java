package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.SystemMsgResponseBean;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class SystemtMsgModel {

    public void getSystemMsg(BaseActivity activity,final SystemMsgCallBack callBack){
        RetrofitFactory.getInstance().createService()
                .systemMsg(RequestUtil.getRequestBeanBody(null,false))
                .compose(activity.<BaseEntity<SystemMsgResponseBean>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<SystemMsgResponseBean>>io())
                .subscribe(new BaseObserver<SystemMsgResponseBean>(activity) {
                    @Override
                    protected void onSuccess(SystemMsgResponseBean systemMsgResponseBean) throws Exception {
                        callBack.success(systemMsgResponseBean);
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

    public interface SystemMsgCallBack{
        void success(SystemMsgResponseBean systemMsgResponseBean);
        void failed(String msg);
    }
}
