package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserMoney;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.UserMoneryModel;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.SecondRequstViewImpl;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class UserMoneryPresenter extends BasePresenter<SecondRequstViewImpl> {

    private UserMoneryModel userMoneryModel;

    public UserMoneryPresenter(){}

    public UserMoneryPresenter(UserMoneryModel userMoneryModel){
        this.userMoneryModel = userMoneryModel;
    }

    public void userMonery(BaseActivity activity){
        if (userMoneryModel == null){
            userMoneryModel = new UserMoneryModel();
        }
        userMoneryModel.userMonery(activity, new UserMoneryModel.UserMoneryCallBack() {
            @Override
            public void success(UserMoney userMoney) {
                if (mView!=null){
                    mView.onSecondRequstSuccess(userMoney);
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    mView.onSecondRequstFailed(msg);
                }

            }
        });
    }
}
