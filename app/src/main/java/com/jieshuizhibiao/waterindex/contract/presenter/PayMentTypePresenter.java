package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.PayMentResponseBean;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.PayMentTypeModel;
import com.jieshuizhibiao.waterindex.contract.view.PayMentTypeViewImpl;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class PayMentTypePresenter extends BasePresenter<PayMentTypeViewImpl> {

    private PayMentTypeModel payMentTypeModel;

    public PayMentTypePresenter(){}

    public PayMentTypePresenter(PayMentTypeModel payMentTypeModel){
        this.payMentTypeModel = payMentTypeModel;
    }

    public void payMentType(BaseActivity activity){
        if (payMentTypeModel == null){
            payMentTypeModel = new PayMentTypeModel();
        }
        payMentTypeModel.payMentType(activity, new PayMentTypeModel.PayMentTypeCallBack() {
            @Override
            public void success(PayMentResponseBean payMentResponseBean) {
                if (mView!=null){
                    mView.onPayMentTypeSuccess(payMentResponseBean);
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    mView.onPayMentTypeFailed(msg);
                }

            }
        });
    }
}
