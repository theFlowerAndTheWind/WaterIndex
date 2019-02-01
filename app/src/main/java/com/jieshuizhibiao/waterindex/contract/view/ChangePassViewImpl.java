package com.jieshuizhibiao.waterindex.contract.view;

import com.jieshuizhibiao.waterindex.contract.IBaseViewImpl;

import java.util.Map;

/**
 * Created by WanghongHe on 2018/12/10 18:57.
 */

public interface ChangePassViewImpl extends IBaseViewImpl {
    void onEdtContentsLegal();
    void onEdtContentsIllegal(Map<String, Boolean> verify);
    void changePassSuccess();
    void changePassFiled(String err);
}
