package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.AddPayMentTypeReqParams;
import com.jieshuizhibiao.waterindex.beans.request.ChangePayMentTypeReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.AddPayMentTypeModel;
import com.jieshuizhibiao.waterindex.contract.model.ChangePaymentTypeModel;
import com.jieshuizhibiao.waterindex.contract.view.ChangePaymentTypeViewImpl;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class ChangePaymentTypePresenter extends BasePresenter<ChangePaymentTypeViewImpl> {

    private ChangePaymentTypeModel changePaymentTypeModel;

    public ChangePaymentTypePresenter(){}

    public ChangePaymentTypePresenter(ChangePaymentTypeModel changePaymentTypeModel){
        this.changePaymentTypeModel = changePaymentTypeModel;
    }

    public void vertify(ChangePayMentTypeReqParams params){
        if (changePaymentTypeModel == null){
            changePaymentTypeModel = new ChangePaymentTypeModel();
        }
        changePaymentTypeModel.verify(params);
    }

    public void vertifyBank(ChangePayMentTypeReqParams params){
        if (changePaymentTypeModel == null){
            changePaymentTypeModel = new ChangePaymentTypeModel();
        }
        changePaymentTypeModel.vertifyBank(params);
    }

    public void changePaymentType(BaseActivity activity, final ChangePayMentTypeReqParams params, final String changeType){
        if (changePaymentTypeModel==null){
            changePaymentTypeModel = new ChangePaymentTypeModel();
        }
        changePaymentTypeModel.changePaymentType(activity, params,changeType, new ChangePaymentTypeModel.ChangePayMentTypeCallBack() {
            @Override
            public void onChangeContentsLegal() {
                if (mView!=null){
                    mView.onChangeContentsLegal();
                }
            }

            @Override
            public void onChangeEdtContentsIllegal(Map<String, Boolean> verify) {
                if (mView!=null && (changeType.equals(HttpConfig.WX_TYPE) || changeType.equals(HttpConfig.ZFB_TYPE))){
                    mView.onChangeEdtContentsIllegal(verify);
                }
            }

            @Override
            public void onChangeEdtContentsIllegalBank(Map<String, Boolean> verify) {
                if (mView!=null && changeType.equals(HttpConfig.BANK_TYPE)){
                    mView.onChangeEdtContentsIllegalBank(verify);
                }
            }

            @Override
            public void success() {
                if (mView!=null){
                    mView.onChangePaymentTyoeSuccess();
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    mView.onChangePaymentTypeFalied(msg);
                }

            }
        });
    }

}
