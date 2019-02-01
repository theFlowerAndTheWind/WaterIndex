package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.WaterIndexApplication;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.ChangeCapitalPassReqParams;
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
 * Created by songxiaotao on 2019/1/10.
 * Class Note:修改资金密码
 */

public class ChangeCapitalPassModel {

    private Map<String, Boolean> verifyResult = new HashMap<String, Boolean>();
    private WaterIndexApplication context = WaterIndexApplication.getInstance();

    public Map<String, Boolean> vertify(ChangeCapitalPassReqParams params) {
        verifyResult.clear();
        if(!TextUtils.isEmpty(params.getOld_safe_pw()) && AccountValidatorUtil.isPassword(params.getOld_safe_pw())){
            verifyResult.put(context.getString(R.string.key_edt_capital_old_pwd), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_capital_old_pwd), false);
        }
        if(!TextUtils.isEmpty(params.getSafe_pw())&&params.getSafe_pw().equals(params.getSafe_pw_re())&&AccountValidatorUtil.isPassword(params.getSafe_pw())&&AccountValidatorUtil.isPassword(params.getSafe_pw_re())){
            verifyResult.put(context.getString(R.string.key_edt_capital_pwd), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_capital_pwd), false);
        }

        return verifyResult;
    }

    public void changeCapitalPass(BaseActivity activity, ChangeCapitalPassReqParams capitalPassReqParams,final ChangeCapitalPassCallBack callBack){
        int Illegal = 0;
        for (Map.Entry<String, Boolean> entry : verifyResult.entrySet()) {
            final Boolean value = entry.getValue();
            if (!value) {
                Illegal += 1;
            }
        }
        if (Illegal == 0) {
            callBack.onEdtContentsLegalChange();
        } else {
            callBack.onEdtContentsIllegalChange(verifyResult);
            return;
        }
        HashMap<String,Object> params = new HashMap<>();
        params.put("old_safe_pw",capitalPassReqParams.getOld_safe_pw());
        params.put("safe_pw",capitalPassReqParams.getSafe_pw());
        params.put("safe_pw_re",capitalPassReqParams.getSafe_pw_re());

        RetrofitFactory.getInstance().createService()
                .changeCapitalPass(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity>io())
                .subscribe(new BaseObserver(activity) {
                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        callBack.success();
                    }

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

    public interface ChangeCapitalPassCallBack{
        void onEdtContentsLegalChange();
        void onEdtContentsIllegalChange(Map<String, Boolean> verify);
        void success();
        void failed(String msg);
    }
}
