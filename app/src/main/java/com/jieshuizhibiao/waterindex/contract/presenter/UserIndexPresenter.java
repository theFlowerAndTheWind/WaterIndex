package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserIndexResponseBean;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.UserIndexModel;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class UserIndexPresenter extends BasePresenter<CommonViewImpl> {

    private UserIndexModel userIndexModel;

    public UserIndexPresenter(){}

    public UserIndexPresenter(UserIndexModel userIndexModel){
        this.userIndexModel = userIndexModel;
    }

    public void userIndex(BaseActivity activity){
        if(userIndexModel == null){
            userIndexModel = new UserIndexModel();
        }
        userIndexModel.usetIndex(activity, new UserIndexModel.UserIndexCallBack() {
            @Override
            public void success(UserIndexResponseBean bean) {
                if (mView!=null){
                    mView.onRequestSuccess(bean);
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
