package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.SetCaptialPassReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.SetCapitalPassModel;
import com.jieshuizhibiao.waterindex.contract.view.SetCapitalPassViewImpl;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/10.
 * Class Note:
 */

public class SetCapitalPassPresenter extends BasePresenter<SetCapitalPassViewImpl> {

    private SetCapitalPassModel setCapitalPassModel;

    public void SetCapitalPassPresenter(){}

    public void SetCapitalPassPresenter(SetCapitalPassModel setCapitalPassModel){
        this.setCapitalPassModel = setCapitalPassModel;
    }

    public void verfity(SetCaptialPassReqParams params){
        if(setCapitalPassModel==null){
            setCapitalPassModel = new SetCapitalPassModel();
        }
        setCapitalPassModel.verify(params);
    }

    public void SetCapitalPass(BaseActivity activity, SetCaptialPassReqParams params){
        if(setCapitalPassModel==null){
            setCapitalPassModel = new SetCapitalPassModel();
        }
        setCapitalPassModel.setCapitalPass(activity, params, new SetCapitalPassModel.SetCaptialPassCallBack() {
            @Override
            public void onEdtContentsLegal() {
                if (mView!=null){
                    mView.onEdtContentsLegal();
                }
            }

            @Override
            public void onEdtContentsIllegal(Map<String, Boolean> verify) {
                if (mView!=null){
                    mView.onEdtContentsIllegal(verify);
                }
            }

            @Override
            public void success() {
                if(mView!=null){
                    mView.onSetCapitalPassSuccess();
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    mView.onSetCapitalPassFailed(msg);
                }

            }
        });
    }
}
