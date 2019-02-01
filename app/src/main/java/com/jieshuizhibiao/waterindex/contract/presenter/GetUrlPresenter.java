package com.jieshuizhibiao.waterindex.contract.presenter;


import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.GetUrlResponseBean;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.GetUrlModel;
import com.jieshuizhibiao.waterindex.contract.view.GetUrlViewImpl;

public class GetUrlPresenter extends BasePresenter<GetUrlViewImpl> {
    private GetUrlModel getUrlModel;

    public GetUrlPresenter(GetUrlModel getUrlModel) {
        this.getUrlModel = getUrlModel;
    }

    public void getUrl(BaseActivity activity, String type){
        if(getUrlModel==null){
            getUrlModel=new GetUrlModel();
        }
        getUrlModel.getUrl(activity, type, new GetUrlModel.GetUrlCallback() {
            @Override
            public void onGetUrlSucc(GetUrlResponseBean getUrlResponseBean) {
                if(mView!=null) mView.onGetUrlSucc(getUrlResponseBean);
            }

            @Override
            public void onGetUrlFail(String msg) {
                if(mView!=null)mView.onGetUrlFail(msg);
            }
        });
    }
}
