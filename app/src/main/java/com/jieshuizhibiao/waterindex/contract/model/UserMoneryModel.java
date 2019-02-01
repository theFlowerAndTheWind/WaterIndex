package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserMoney;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:我的资产
 */

public class UserMoneryModel {

    public void userMonery(BaseActivity activity,final UserMoneryCallBack callBack){
        RetrofitFactory.getInstance().createService()
                .userMoney(RequestUtil.getRequestBeanBody(null,false))
                .compose(activity.<BaseEntity<UserMoney>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<UserMoney>>io())
                .subscribe(new BaseObserver<UserMoney>() {
                    @Override
                    protected void onSuccess(UserMoney userMoney) throws Exception {
                        callBack.success(userMoney);
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

    public interface UserMoneryCallBack{
        void success(UserMoney userMoney);
        void failed(String msg);
    }
}
