package com.jieshuizhibiao.waterindex.contract.view;


import com.jieshuizhibiao.waterindex.beans.GetUrlResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

public interface GetUrlViewImpl extends IBaseViewImpl {
    void onGetUrlSucc(GetUrlResponseBean getUrlResponseBean);
    void onGetUrlFail(String msg);
}
