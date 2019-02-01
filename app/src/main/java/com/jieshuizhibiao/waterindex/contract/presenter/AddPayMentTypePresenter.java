package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.AddPayMentTypeReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.AddPayMentTypeModel;
import com.jieshuizhibiao.waterindex.contract.view.AddPayMentTypeViewImpl;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class AddPayMentTypePresenter extends BasePresenter<AddPayMentTypeViewImpl> {

    private AddPayMentTypeModel addPayMentTypeModel;

    public AddPayMentTypePresenter(){}

    public AddPayMentTypePresenter(AddPayMentTypeModel addPayMentTypeModel){
        this.addPayMentTypeModel = addPayMentTypeModel;
    }

    public void vertify(AddPayMentTypeReqParams params){
        if (addPayMentTypeModel == null){
            addPayMentTypeModel = new AddPayMentTypeModel();
        }
        addPayMentTypeModel.verify(params);
    }

    public void vertifyBank(AddPayMentTypeReqParams params){
        if (addPayMentTypeModel == null){
            addPayMentTypeModel = new AddPayMentTypeModel();
        }
        addPayMentTypeModel.vertifyBank(params);
    }

    public void addPayMentType(BaseActivity activity, final AddPayMentTypeReqParams params){
        if (addPayMentTypeModel == null){
            addPayMentTypeModel = new AddPayMentTypeModel();
        }
        addPayMentTypeModel.addPayMentType(activity, params, new AddPayMentTypeModel.AddPayMentTypeCallBack() {

            @Override
            public void onAddEdtContentsLegal() {
                if (mView!=null){
                    mView.onAddEdtContentsLegal();
                }
            }

            @Override
            public void onAddEdtContentsIllegal(Map<String, Boolean> verify) {
                if (mView!=null && (params.getType().equals(HttpConfig.WX_TYPE) || params.getType().equals(HttpConfig.ZFB_TYPE))){
                    mView.onAddEdtContentsIllegal(verify);
                }
            }

            @Override
            public void onAddEdtContentsIllegalBank(Map<String, Boolean> verify) {
                if (mView!=null && params.getType().equals(HttpConfig.BANK_TYPE)){
                    mView.onAddEdtContentsIllegalBank(verify);
                }
            }

            @Override
            public void success() {
                if (mView!=null){
                    mView.onAddPaymentTypeSuccess();
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    mView.onAddPaymentTypeFailed(msg);
                }

            }
        });
    }
}
