package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

import java.util.Map;

public interface FindPassViewImpl extends IBaseViewImpl {
    void onEdtContentsLegal();

    void onEdtContentsIllegal(Map<String, Boolean> verify);

    void onGetSmsSuccess();

    void onGetSmsFailed(String msg);

    void onFindPassSuccess();

    void onFindPassFaild(String msg);
}

