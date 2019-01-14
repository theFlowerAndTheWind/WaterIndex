package com.quanminjieshui.waterindex.contract.presenter;

import com.quanminjieshui.waterindex.base.BaseActivity;
import com.quanminjieshui.waterindex.beans.MoveMoneryBean;
import com.quanminjieshui.waterindex.beans.request.MoveMoneryReqParams;
import com.quanminjieshui.waterindex.contract.BasePresenter;
import com.quanminjieshui.waterindex.contract.model.MoveMoneryModel;
import com.quanminjieshui.waterindex.contract.view.CommonViewImpl;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class MoveMoneryPresenter extends BasePresenter<CommonViewImpl> {

    private MoveMoneryModel moveMoneryModel;

    public MoveMoneryPresenter(){}

    public MoveMoneryPresenter(MoveMoneryModel moveMoneryModel){
        this.moveMoneryModel = moveMoneryModel;
    }

    public void moveMonery(BaseActivity activity, MoveMoneryReqParams params){
        if (moveMoneryModel == null){
            moveMoneryModel = new MoveMoneryModel();
        }
        moveMoneryModel.moveMonery(activity, params, new MoveMoneryModel.MoveMoneryCallBack() {
            @Override
            public void success(MoveMoneryBean moveMoneryBean) {
                if (mView!=null){
                    mView.onRequestSuccess(moveMoneryBean);
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