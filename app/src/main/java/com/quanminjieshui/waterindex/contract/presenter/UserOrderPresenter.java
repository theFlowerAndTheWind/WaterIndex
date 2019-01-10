package com.quanminjieshui.waterindex.contract.presenter;

import com.quanminjieshui.waterindex.base.BaseActivity;
import com.quanminjieshui.waterindex.beans.SysConfigResponseBean;
import com.quanminjieshui.waterindex.contract.BasePresenter;
import com.quanminjieshui.waterindex.contract.model.UserOrderModel;
import com.quanminjieshui.waterindex.contract.model.callback.CommomCallback;
import com.quanminjieshui.waterindex.contract.model.callback.SencondRequestCallback;
import com.quanminjieshui.waterindex.contract.view.CommonViewImpl;

public class UserOrderPresenter extends BasePresenter<CommonViewImpl> {
    private UserOrderModel userOrderModel;

    public UserOrderPresenter(UserOrderModel userOrderModel) {
        this.userOrderModel = userOrderModel;
    }

    public void createOrder(BaseActivity activity, String trade_id, String total) {
        if (userOrderModel == null) {
            userOrderModel = new UserOrderModel();
        }

        userOrderModel.createOrder(activity, trade_id, total, new CommomCallback() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (mView != null) {
                    mView.onRequestSuccess(bean);
                }
            }

            @Override
            public void onRequestFailed(String msg) {
                if (mView != null) {
                    mView.onRequestFailed(msg);
                }
            }
        });
    }

}
