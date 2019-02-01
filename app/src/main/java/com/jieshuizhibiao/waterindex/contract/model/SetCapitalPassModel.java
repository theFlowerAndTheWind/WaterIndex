package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.WaterIndexApplication;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.SetCaptialPassReqParams;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.AccountValidatorUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/10.
 * Class Note:设置资金密码
 */

public class SetCapitalPassModel {

    private Map<String, Boolean> verifyResult = new HashMap<String, Boolean>();
    private WaterIndexApplication context = WaterIndexApplication.getInstance();

    public Map<String, Boolean> verify(SetCaptialPassReqParams params) {
        verifyResult.clear();
        if(!TextUtils.isEmpty(params.getSafe_pw())&&params.getSafe_pw().equals(params.getSafe_pw_re())&&AccountValidatorUtil.isPassword(params.getSafe_pw())&&AccountValidatorUtil.isPassword(params.getSafe_pw_re())){
            verifyResult.put(context.getString(R.string.key_edt_name_pwd), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_name_pwd), false);
        }

        if(!TextUtils.isEmpty(params.getNick_name())){
            verifyResult.put(context.getString(R.string.key_edt_name_nike_name), true);
        }else{
            verifyResult.put(context.getString(R.string.key_edt_name_nike_name), false);
        }

        return verifyResult;
    }

    public void setCapitalPass(BaseActivity activity, SetCaptialPassReqParams setCaptialPassReqParams,final SetCaptialPassCallBack callBack){
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
        HashMap<String,Object> params = new HashMap<>();
        params.put("province", setCaptialPassReqParams.getProvince());
        params.put("city", setCaptialPassReqParams.getCity());
        params.put("user_name", setCaptialPassReqParams.getUser_name());
        params.put("id_no", setCaptialPassReqParams.getId_no());
        params.put("id_img_a", setCaptialPassReqParams.getId_img_a());
        params.put("id_img_b", setCaptialPassReqParams.getId_img_b());
        params.put("nick_name",setCaptialPassReqParams.getNick_name());
        params.put("safe_pw",setCaptialPassReqParams.getSafe_pw());
        params.put("safe_pw_re",setCaptialPassReqParams.getSafe_pw_re());
        RetrofitFactory.getInstance().createService()
                .setCapitalPass(RequestUtil.getRequestHashBody(params,false))
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

    public Map<String, Boolean> getVerifyResult() {
        return Collections.unmodifiableMap(verifyResult);
    }

    public interface SetCaptialPassCallBack{
        void onEdtContentsLegal();
        void onEdtContentsIllegal(Map<String, Boolean> verify);
        void success();
        void failed(String msg);
    }
}
