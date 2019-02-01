package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.AppUpdateModel;
import com.jieshuizhibiao.waterindex.contract.view.AppUpdateViewImpl;

/**
 * Created by songxiaotao on 2019/1/22.
 * Class Note:
 */

public class AppUpdatePresenter extends BasePresenter<AppUpdateViewImpl> {

    private AppUpdateModel appUpdateModel;

    public AppUpdatePresenter(){}

    public AppUpdatePresenter(AppUpdateModel appUpdateModel){
        this.appUpdateModel = appUpdateModel;
    }

    public void appVersion(BaseActivity activity,String ver){
        if (appUpdateModel == null){
            appUpdateModel = new AppUpdateModel();
        }
        appUpdateModel.appVersion(activity, ver, new AppUpdateModel.AppVersionCallBack() {
            @Override
            public void success(Object bean) {
                if (mView!=null){
                    mView.onAppUpdateSuccess(bean);
                }
            }

            @Override
            public void failed(String msg) {
                if (mView!=null){
                    mView.onAppUpdateFailed(msg);
                }
            }
        });
    }
}
