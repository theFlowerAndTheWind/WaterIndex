package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.TraderDoAppealModel;
import com.jieshuizhibiao.waterindex.contract.model.callback.SecondRequestCallback;
import com.jieshuizhibiao.waterindex.contract.view.SecondRequstViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.UploadFileViewImpl;

/**
 * 卖家申诉，卖家申诉
 */
public class TraderDoAppealPresenter extends BasePresenter<SecondRequstViewImpl> {

    private TraderDoAppealModel traderDoAppealModel;

    public TraderDoAppealPresenter(TraderDoAppealModel traderDoAppealModel) {
        this.traderDoAppealModel = traderDoAppealModel;
    }

    public void sellerDoAppeal(BaseActivity activity, long order_id, String detail) {
        if (traderDoAppealModel == null) {
            traderDoAppealModel = new TraderDoAppealModel();
        }
        traderDoAppealModel.sellerDoAppeal(activity, order_id, detail, new SecondRequestCallback() {
            @Override
            public void onSecondRequstSuccess(Object bean) {
                if (mView != null) {
                    mView.onSecondRequstSuccess(bean);
                }
            }

            @Override
            public void onSecondRequstFailed(String msg) {
                if (mView != null) {
                    mView.onSecondRequstFailed(msg);
                }
            }
        });
    }

    public void buyerDoAppeal(BaseActivity activity, long order_id, String detail) {
        if (traderDoAppealModel == null) {
            traderDoAppealModel = new TraderDoAppealModel();
        }
        traderDoAppealModel.buyerDoAppeal(activity, order_id, detail, new SecondRequestCallback() {
            @Override
            public void onSecondRequstSuccess(Object bean) {
                if (mView != null) {
                    mView.onSecondRequstSuccess(bean);
                }
            }

            @Override
            public void onSecondRequstFailed(String msg) {
                if (mView != null) {
                    mView.onSecondRequstFailed(msg);
                }
            }
        });
    }
}
