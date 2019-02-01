package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.AuthDetailResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by songxiaotao on 2018/12/12.
 * Class Note:
 */

public interface AuthDetailViewImpl extends IBaseViewImpl {
    void authDetailSuccess(AuthDetailResponseBean authDetailResponseBean);
    void authDetailFailed(String msg);
}
