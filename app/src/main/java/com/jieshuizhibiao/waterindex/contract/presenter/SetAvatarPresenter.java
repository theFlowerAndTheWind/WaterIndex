package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.SetAvatarReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.SetAvatarModel;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class SetAvatarPresenter extends BasePresenter<CommonViewImpl> {

    private SetAvatarModel setAvatarModel;

    public SetAvatarPresenter(){}

    public SetAvatarPresenter(SetAvatarModel setAvatarModel){
        this.setAvatarModel = setAvatarModel;
    }

    public void setAvatar(BaseActivity activity, final SetAvatarReqParams params){
        if (setAvatarModel == null){
            setAvatarModel = new SetAvatarModel();
        }
        setAvatarModel.setAvatarImg(activity, params, new SetAvatarModel.SetAvatarCallBack() {
            @Override
            public void success() {
                if (mView!=null){
                    mView.onRequestSuccess(params);
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
