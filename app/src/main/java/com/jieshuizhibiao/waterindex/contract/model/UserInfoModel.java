package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserDetailResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserInfoResponseBean;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

/**
 * Created by songxiaotao on 2018/12/12.
 * Class Note:用户信息
 */

public class UserInfoModel {

    public void getUserInfo(BaseActivity activity, final UserInfoCallBack callBack){
        RetrofitFactory.getInstance().createService()
                .userInfo(RequestUtil.getRequestHashBody(null,false))
                .compose(activity.<BaseEntity<UserInfoResponseBean>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<UserInfoResponseBean>>io())
                .subscribe(new BaseObserver<UserInfoResponseBean>() {

                    @Override
                    protected void onSuccess(UserInfoResponseBean userDetailBean) throws Exception {
                        callBack.success(userDetailBean);
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

    public interface UserInfoCallBack{
        void success(UserInfoResponseBean userInfoResponseBean);
        void failed(String msg);
    }
}
