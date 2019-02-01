package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UploadFileResponseBean;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.UploadFileModel;
import com.jieshuizhibiao.waterindex.contract.model.callback.SecondRequestCallback;
import com.jieshuizhibiao.waterindex.contract.view.UploadFileViewImpl;

import java.io.File;

public class UploadFilePresenter extends BasePresenter<UploadFileViewImpl> {

    private UploadFileModel uploadFileModel;

    public UploadFilePresenter(){}

    public UploadFilePresenter(UploadFileModel uploadFileModel) {
        this.uploadFileModel = uploadFileModel;
    }

    public void uploadFile(BaseActivity activity, String token,File file){
        if(uploadFileModel==null){
            uploadFileModel=new UploadFileModel();
        }
        uploadFileModel.uploadFile(activity, token,file,new UploadFileModel.UploadFileCallback() {

            @Override
            public void success(UploadFileResponseBean fileResponseBean) {
                if(mView!=null){
                    mView.onUploadFileSuccess(fileResponseBean);
                }
            }

            @Override
            public void failed(String msg) {
                if(mView!=null){
                    mView.onUploadFileFailed(msg);
                }
            }
        });
    }

}
