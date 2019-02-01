package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.SysConfigResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserMoney;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.CommonSysConfigModel;
import com.jieshuizhibiao.waterindex.contract.model.UserMoneryModel;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:
 */

public class CommonSysConfigPresenter extends BasePresenter<CommonViewImpl> {

    private CommonSysConfigModel sysConfigModel;

    public CommonSysConfigPresenter(){}

    public CommonSysConfigPresenter(CommonSysConfigModel sysConfigModel){
        this.sysConfigModel = sysConfigModel;
    }

    public void getSysConfig(BaseActivity activity){
        if (sysConfigModel == null){
            sysConfigModel = new CommonSysConfigModel();
        }
        sysConfigModel.getSysConfig(activity, new CommonSysConfigModel.CommonSysConfigCallBack() {
            @Override
            public void success(SysConfigResponseBean bean) {
                if (mView!=null){
                    mView.onRequestSuccess(bean);
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
