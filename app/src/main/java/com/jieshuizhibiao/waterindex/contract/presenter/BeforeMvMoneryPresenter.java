package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.BeforeMvMoneyResponse;
import com.jieshuizhibiao.waterindex.beans.MoveMoneryBean;
import com.jieshuizhibiao.waterindex.beans.request.MoveMoneryReqParams;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.BeforeMoveMoneryModel;
import com.jieshuizhibiao.waterindex.contract.model.MoveMoneryModel;
import com.jieshuizhibiao.waterindex.contract.view.BeforeMvMoneryViewImpl;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class BeforeMvMoneryPresenter extends BasePresenter<BeforeMvMoneryViewImpl> {

    private BeforeMoveMoneryModel beforeMoveMoneryModel;

    public BeforeMvMoneryPresenter() {
    }

    public BeforeMvMoneryPresenter(BeforeMoveMoneryModel beforeMoveMoneryModel) {
        this.beforeMoveMoneryModel = beforeMoveMoneryModel;
    }

    public void beforMoveMonery(BaseActivity activity, MoveMoneryReqParams params) {
        if (beforeMoveMoneryModel == null) {
            beforeMoveMoneryModel = new BeforeMoveMoneryModel();
        }
        beforeMoveMoneryModel.beforeMoveMonery(activity, params, new BeforeMoveMoneryModel.BeforeMoveMoneryCallBack() {
            @Override
            public void success(BeforeMvMoneyResponse beforeMvMoneyResponse) {
                if (mView != null) {
                    mView.onBeforeMvMonerySuccess(beforeMvMoneyResponse);
                }
            }

            @Override
            public void failed(String msg) {
                if (mView != null) {
                    mView.onBeforeMvMoneryFailed(msg);
                }
            }
        });
    }
}
