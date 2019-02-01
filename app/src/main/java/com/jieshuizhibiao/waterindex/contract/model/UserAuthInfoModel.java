package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserAuthInfo;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:身份认证--info 与注册登录认证不同
 */

public class UserAuthInfoModel {

    public void userAuthInfo(BaseActivity activity,final UserAuthInfoCallBack callBack){
        RetrofitFactory.getInstance().createService()
                .userAuthInfo(RequestUtil.getRequestBeanBody(null,false))
                .compose(activity.<BaseEntity<UserAuthInfo>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<UserAuthInfo>>io())
                .subscribe(new BaseObserver<UserAuthInfo>(activity) {
                    @Override
                    protected void onSuccess(UserAuthInfo userAuthInfo) throws Exception {
                        callBack.success(userAuthInfo);
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

    public interface UserAuthInfoCallBack{
        void success(UserAuthInfo userAuthInfo);
        void failed(String msg);
    }
}
