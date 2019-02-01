package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/10.
 * Class Note:
 */

public interface ChangeCapitalPassViewImpl extends IBaseViewImpl {
    void onEdtContentsLegalChange();
    void onEdtContentsIllegalChange(Map<String, Boolean> verify);
    void onChangeCapitalPassSuccess();
    void onChangeCapitalPassFailed(String msg);
}
