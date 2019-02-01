package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.SysConfigResponseBean;
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
 * Created by WanghongHe on 2019/1/22 11:52.
 * 公共 model
 */

public class CommonSysConfigModel {
    public void getSysConfig(BaseActivity activity, final CommonSysConfigCallBack callBack){
        HashMap<String,Object> params=new HashMap<>();
        params.put("param","ds_price");
        RetrofitFactory.getInstance().createService()
                .sysConfig(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity<SysConfigResponseBean>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<SysConfigResponseBean>>io())
                .subscribe(new BaseObserver<SysConfigResponseBean>(activity) {

                    @Override
                    protected void onSuccess(SysConfigResponseBean bean) throws Exception {
                        callBack.success(bean);
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

    public interface CommonSysConfigCallBack{
        void success(SysConfigResponseBean bean);
        void failed(String msg);
    }
}
