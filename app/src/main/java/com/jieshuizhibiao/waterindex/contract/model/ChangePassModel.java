package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.WaterIndexApplication;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.AccountValidatorUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WanghongHe on 2018/12/10 12:57.
 * 修改密码
 */

public class ChangePassModel {

    private Map<String, Boolean> verifyResult = new HashMap<String, Boolean>();
    private WaterIndexApplication context = WaterIndexApplication.getInstance();

    public Map<String, Boolean> verify(String old_pass, String new_pass,String new_confirm_pass) {
        verifyResult.clear();
        if(!TextUtils.isEmpty(new_pass)&&new_pass.equals(new_confirm_pass)&& AccountValidatorUtil.isPassword(new_pass)&&AccountValidatorUtil.isPassword(new_confirm_pass)){
            verifyResult.put(context.getString(R.string.key_edt_change_new_pwd), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_change_new_pwd), false);
        }

        if(!TextUtils.isEmpty(old_pass) && AccountValidatorUtil.isPassword(old_pass)){
            verifyResult.put(context.getString(R.string.key_edt_change_old_pwd), true);
        }else{
            verifyResult.put(context.getString(R.string.key_edt_change_old_pwd), false);
        }

        return verifyResult;
    }

    public void changePass(BaseActivity activity, String old_pass, String new_pass, final ChangePassCallBack callBack){
        int Illegal = 0;
        for (Map.Entry<String, Boolean> entry : verifyResult.entrySet()) {
            final Boolean value = entry.getValue();
            if (!value) {
                Illegal += 1;
            }
        }
        if (Illegal == 0) {
            callBack.onEdtContentsLegal();
        } else {
            callBack.onEdtContentsIllegal(verifyResult);
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(old_pass)) {
            params.put("old_pass", old_pass);
        }
        if (!TextUtils.isEmpty(old_pass)) {
            params.put("new_pass", new_pass);
        }
        RetrofitFactory.getInstance().createService()
                .changePass(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity>io())
                .subscribe(new BaseObserver(activity) {
                    /**
                     * 返回成功
                     *
                     * @param o
                     * @throws Exception
                     */
                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        callBack.success();
                    }

                    /**
                     * 返回失败
                     *
                     * @param e
                     * @param isNetWorkError 是否是网络错误
                     * @throws Exception
                     */
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callBack.failed(HttpConfig.ERROR_MSG);
                            } else {
                                callBack.failed(e.getMessage());
                            }
                        } else {
                            callBack.failed("");
                        }

                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callBack.failed(msg);
                    }
                });
    }

    public interface ChangePassCallBack{
        void onEdtContentsLegal();
        void onEdtContentsIllegal(Map<String, Boolean> verify);
        void success();
        void failed(String msg);
    }
}
