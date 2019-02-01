package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserAuthInfo;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.UserAuthInfoModel;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class UserAuthInfoPresenter extends BasePresenter<CommonViewImpl> {

    private UserAuthInfoModel userAuthInfoModel;

    public UserAuthInfoPresenter(){}

    public UserAuthInfoPresenter(UserAuthInfoModel userAuthInfoModel){
        this.userAuthInfoModel = userAuthInfoModel;
    }

    public void userAuthInfo(BaseActivity activity){
        if(userAuthInfoModel == null){
            userAuthInfoModel = new UserAuthInfoModel();
        }
        userAuthInfoModel.userAuthInfo(activity, new UserAuthInfoModel.UserAuthInfoCallBack() {
            @Override
            public void success(UserAuthInfo userAuthInfo) {
                if (mView!=null){
                    mView.onRequestSuccess(userAuthInfo);
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    mView.onRequestFailed(msg);
                }

            }
        });
    }
}
