package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.SysConfigResponseBean;
import com.jieshuizhibiao.waterindex.beans.UploadFileResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;
import com.jieshuizhibiao.waterindex.contract.presenter.UploadFilePresenter;

public interface UploadFileViewImpl extends IBaseViewImpl {
    void onUploadFileSuccess(UploadFileResponseBean fileResponseBean);
    void onUploadFileFailed(String msg);
}
