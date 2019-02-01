package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.PersonalAuthReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.AuthModel;
import com.jieshuizhibiao.waterindex.contract.view.AuthViewImpl;

import java.util.Map;

public class AuthPresenter extends BasePresenter<AuthViewImpl> {

    private AuthModel model;

    public AuthPresenter(){}

    public AuthPresenter(AuthModel model) {
        this.model = model;
    }

    public void vertify(Object params){
        if(model==null){
            model=new AuthModel();
        }
        if (params instanceof PersonalAuthReqParams){
            model.verify(((PersonalAuthReqParams) params).getProvince(),((PersonalAuthReqParams) params).getCity()
            ,((PersonalAuthReqParams) params).getUser_name(),((PersonalAuthReqParams) params).getId_no()
            ,((PersonalAuthReqParams) params).getId_img_a(),((PersonalAuthReqParams) params).getId_img_b());
        }

    }
    public void auth(BaseActivity activity, Object params){
        if(model==null){
            model=new AuthModel();
        }

        model.auth(activity,params, new AuthModel.AuthCallback() {
            @Override
            public void onEdtContentsLegal() {
                if(mView!=null){
                    mView.onEdtContentsLegal();
                }
            }

            @Override
            public void onEdtContentsIllegal(Map<String, Boolean> verify) {
                if(mView!=null){
                    mView.onEdtContentsIllegal(verify);
                }
            }

            @Override
            public void onCompanyAuthSuccess() {
                if(mView!=null){
                    mView.onCompanyAuthSuccess();
                }
            }

            @Override
            public void onCompanyAuthFailed(String msg) {
                if(mView!=null){
                    mView.onCompanyAuthFailed(msg);
                }
            }

            @Override
            public void onPersonalAuthSuccess() {
                if(mView!=null){
                    mView.onPersonalAuthSuccess();
                }
            }

            @Override
            public void onPersonalAuthFailed(String msg) {
                if(mView!=null){
                    mView.onPersonalAuthFailed(msg);
                }
            }
        });
    }

}
