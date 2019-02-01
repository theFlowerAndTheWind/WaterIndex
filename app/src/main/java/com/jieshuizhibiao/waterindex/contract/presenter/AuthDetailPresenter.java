package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.AuthDetailResponseBean;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.AuthDetailModel;
import com.jieshuizhibiao.waterindex.contract.view.AuthDetailViewImpl;

/**
 * Created by songxiaotao on 2018/12/12.
 * Class Note:
 */

public class AuthDetailPresenter extends BasePresenter<AuthDetailViewImpl> {

    private AuthDetailModel authDetailModel;

    public AuthDetailPresenter(){}

    public AuthDetailPresenter(AuthDetailModel authDetailModel){
        this.authDetailModel = authDetailModel;
    }

    public void getAuthDetail(BaseActivity activity){
        if(authDetailModel == null){
            authDetailModel = new AuthDetailModel();
        }
        authDetailModel.getAuthDetail(activity, new AuthDetailModel.AuthDetailCallBack() {
            @Override
            public void success(AuthDetailResponseBean authDetailResponseBean) {
                if(mView!=null){
                    mView.authDetailSuccess(authDetailResponseBean);
                }
            }

            @Override
            public void failed(String msg) {
                if(mView!=null){
                    mView.authDetailFailed(msg);
                }

            }
        });
    }
}
