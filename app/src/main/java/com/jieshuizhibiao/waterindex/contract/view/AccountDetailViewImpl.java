package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.AccountDetailResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by songxiaotao on 2018/12/12.
 * Class Note:
 */

public interface AccountDetailViewImpl extends IBaseViewImpl {

    void onAccountDetailSuccess(AccountDetailResponseBean accountDetailResponseBean);
    void onAccountDetailFailed(String msg);
}
