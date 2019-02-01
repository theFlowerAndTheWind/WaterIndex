package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserIndexResponseBean;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:用户首页
 */

public class UserIndexModel {

    public void usetIndex(BaseActivity activity,final UserIndexCallBack callBack){
        RetrofitFactory.getInstance().createService()
                .userIndex(RequestUtil.getRequestBeanBody(null,false))
                .compose(activity.<BaseEntity<UserIndexResponseBean>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<UserIndexResponseBean>>io())
                .subscribe(new BaseObserver<UserIndexResponseBean>() {
                    @Override
                    protected void onSuccess(UserIndexResponseBean bean) throws Exception {
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

    public interface UserIndexCallBack{
        void success(UserIndexResponseBean bean);
        void failed(String msg);
    }

}
