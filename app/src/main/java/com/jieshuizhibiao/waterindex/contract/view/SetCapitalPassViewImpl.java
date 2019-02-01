package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/10.
 * Class Note:
 */

public interface SetCapitalPassViewImpl extends IBaseViewImpl {
    void onEdtContentsLegal();
    void onEdtContentsIllegal(Map<String, Boolean> verify);
    void onSetCapitalPassSuccess();
    void onSetCapitalPassFailed(String msg);
}
