package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.SystemMsgResponseBean;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.SystemtMsgModel;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class SystemMsgPresenter extends BasePresenter<CommonViewImpl> {

    private SystemtMsgModel systemtMsgModel;

    public SystemMsgPresenter(){}

    public SystemMsgPresenter(SystemtMsgModel systemtMsgModel){
        this.systemtMsgModel = systemtMsgModel;
    }

    public void getSystemMsg(BaseActivity activity){
        if (systemtMsgModel == null){
            systemtMsgModel = new SystemtMsgModel();
        }
        systemtMsgModel.getSystemMsg(activity, new SystemtMsgModel.SystemMsgCallBack() {
            @Override
            public void success(SystemMsgResponseBean systemMsgResponseBean) {
                if (mView!=null){
                    mView.onRequestSuccess(systemMsgResponseBean);
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
