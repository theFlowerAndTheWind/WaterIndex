package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.ChangePassModel;
import com.jieshuizhibiao.waterindex.contract.view.ChangePassViewImpl;

import java.util.Map;

/**
 * Created by WanghongHe on 2018/12/10 18:56.
 * user_login 用户注册手机号
 */

public class ChangePassPresenter extends BasePresenter<ChangePassViewImpl> {

    private ChangePassModel changePassModel;

    public ChangePassPresenter(ChangePassModel changePassModel){
        this.changePassModel = changePassModel;
    }

    public void vertify(String old_pass, String new_pass,String new_confirm_pass){
        if(changePassModel==null){
            changePassModel = new ChangePassModel();
        }
        changePassModel.verify(old_pass,new_pass,new_confirm_pass);
    }

    public void changePass(BaseActivity activity, String old_pass, String new_pass){
        if(changePassModel==null){
            changePassModel = new ChangePassModel();
        }

        changePassModel.changePass(activity, old_pass, new_pass, new ChangePassModel.ChangePassCallBack() {
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
            public void success() {
                if(mView!=null){
                    mView.changePassSuccess();
                }
            }

            @Override
            public void failed(String msg) {
                if(mView!=null){
                    mView.changePassFiled(msg);
                }

            }
        });
    }
}
