package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.MoveMoneryBean;
import com.jieshuizhibiao.waterindex.beans.request.MoveMoneryReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.MoveMoneryModel;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

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
