package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.beans.UserDetailResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserIndexResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserInfoResponseBean;
import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

/**
 * Created by songxiaotao on 2018/12/12.
 * Class Note:
 */

public interface UserInfoViewImpl extends IBaseViewImpl {
    void onUserInfoSuccess(UserInfoResponseBean userInfoResponseBean);
    void onUserInfoFailed(String msg);
}
