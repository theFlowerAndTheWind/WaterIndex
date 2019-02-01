package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserInfoResponseBean;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.UserInfoModel;
import com.jieshuizhibiao.waterindex.contract.view.UserInfoViewImpl;

/**
 * Created by songxiaotao on 2018/12/13.
 * Class Note:
 */

public class UserInfoPresenter extends BasePresenter<UserInfoViewImpl> {

    private UserInfoModel userInfoModel;

    public UserInfoPresenter(){}

    public UserInfoPresenter(UserInfoModel userInfoModel){
        this.userInfoModel = userInfoModel;
    }

    public void getUserInfo(BaseActivity activity){
        if(userInfoModel == null){
            userInfoModel = new UserInfoModel();
        }

        userInfoModel.getUserInfo(activity, new UserInfoModel.UserInfoCallBack() {
            @Override
            public void success(UserInfoResponseBean userInfoResponseBean) {
                if(mView!=null){
                    mView.onUserInfoSuccess(userInfoResponseBean);
                }
            }

            @Override
            public void failed(String msg) {
                if(mView!=null){
                    mView.onUserInfoFailed(msg);
                }

            }
        });
    }
}
