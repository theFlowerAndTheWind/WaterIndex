package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.ChangeCapitalPassReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.ChangeCapitalPassModel;
import com.jieshuizhibiao.waterindex.contract.view.ChangeCapitalPassViewImpl;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/10.
 * Class Note:
 */

public class ChangeCapitalPassPresenter extends BasePresenter<ChangeCapitalPassViewImpl> {

    private ChangeCapitalPassModel changeCapitalPassModel;

    public void ChangeCapitalPassPresenter(){}

    public void ChangeCapitalPassPresenter(ChangeCapitalPassModel changeCapitalPassModel){
        this.changeCapitalPassModel = changeCapitalPassModel;
    }

    public void verfity(ChangeCapitalPassReqParams params){
        if(changeCapitalPassModel==null){
            changeCapitalPassModel = new ChangeCapitalPassModel();
        }
        changeCapitalPassModel.vertify(params);
    }

    public void changeCapitalPass(BaseActivity activity, ChangeCapitalPassReqParams params){
        if(changeCapitalPassModel==null){
            changeCapitalPassModel = new ChangeCapitalPassModel();
        }
        changeCapitalPassModel.changeCapitalPass(activity, params, new ChangeCapitalPassModel.ChangeCapitalPassCallBack() {
            @Override
            public void onEdtContentsLegalChange() {
                if(mView!=null){
                    mView.onEdtContentsLegalChange();
                }
            }

            @Override
            public void onEdtContentsIllegalChange(Map<String, Boolean> verify) {
                if(mView!=null){
                    mView.onEdtContentsIllegalChange(verify);
                }
            }

            @Override
            public void success() {
                if(mView!=null){
                    mView.onChangeCapitalPassSuccess();
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    mView.onChangeCapitalPassFailed(msg);
                }

            }
        });
    }
}
