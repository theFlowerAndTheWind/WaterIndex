package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.InfoDetailRespoonseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by songxiaotao on 2018/12/13.
 * Class Note:
 */

public interface InfoDetailViewImpl extends IBaseViewImpl {
    void infoDetailSuccess(InfoDetailRespoonseBean infoDetailRespoonseBean);
    void infoDetailFailed(String msg);
}
