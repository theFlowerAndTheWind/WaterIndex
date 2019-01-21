package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UploadFileResponseBean;
import com.jieshuizhibiao.waterindex.contract.model.callback.SecondRequestCallback;
import com.jieshuizhibiao.waterindex.contract.model.callback.ThirdRequestCallback;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadFileModel {

    public void uploadFile(BaseActivity activity, String token,File file,final UploadFileCallback callback){
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",file.getName(), RequestBody.create(
                MediaType.parse("application/octet-stream"),file
        ));
        RetrofitFactory.getInstance().createService()
                .uploadFile(token,"android",filePart)
                .compose(activity.<BaseEntity<UploadFileResponseBean>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<UploadFileResponseBean>>io())
                .subscribe(new BaseObserver<UploadFileResponseBean>() {
                    @Override
                    protected void onSuccess(UploadFileResponseBean uploadFileResponseBean) throws Exception {
                        callback.success(uploadFileResponseBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.failed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.failed(e.getMessage());
                            }
                        } else {
                            callback.failed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callback.failed(msg);
                    }
                });
    }

    public interface UploadFileCallback{
        void success(UploadFileResponseBean fileResponseBean);
        void failed(String msg);
    }
}
