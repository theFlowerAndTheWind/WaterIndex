package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;
import android.util.Log;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.WaterIndexApplication;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.CompanyAuthReqParams;
import com.jieshuizhibiao.waterindex.beans.request.PersonalAuthReqParams;
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

public class AuthModel {
    private static final String TAG = "AuthModel";
    private Map<String, Boolean> verifyResult = new HashMap<String, Boolean>();
    private WaterIndexApplication context = WaterIndexApplication.getInstance();

    public Map<String, Boolean> verify(String province,String city,String userName,String idCard,String idImgA,String idImgB){
        verifyResult.clear();
        if (!TextUtils.isEmpty(userName)) {
            verifyResult.put(context.getString(R.string.key_edt_real_name), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_real_name), false);
        }
        if (!TextUtils.isEmpty(idCard) && AccountValidatorUtil.isIDCard(idCard)) {
            verifyResult.put(context.getString(R.string.key_edt_id_card), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_id_card), false);
        }
        if (!TextUtils.isEmpty(idImgA)) {
            verifyResult.put(context.getString(R.string.key_btn_id_img_a), true);
        } else {
            verifyResult.put(context.getString(R.string.key_btn_id_img_a), false);
        }
        if (!TextUtils.isEmpty(idImgB)) {
            verifyResult.put(context.getString(R.string.key_btn_id_img_b), true);
        } else {
            verifyResult.put(context.getString(R.string.key_btn_id_img_b), false);
        }
        return verifyResult;
    }

    public void auth(final BaseActivity activity,Object params, AuthCallback callback) {
        if(params instanceof CompanyAuthReqParams){//企业认证
            companyAuthor(activity, (CompanyAuthReqParams) params, callback);
        } else if (params instanceof PersonalAuthReqParams){//个人认证
            personalAuthor(activity, (PersonalAuthReqParams) params, callback);
        }
    }

    private void companyAuthor(final BaseActivity activity, CompanyAuthReqParams companyAuthReqParams, final AuthCallback callback) {
        int Illegal = 0;
        for (Map.Entry<String, Boolean> entry : verifyResult.entrySet()) {
            final Boolean value = entry.getValue();
            if (!value) {
                Illegal += 1;
            }
        }
        if (Illegal == 0) {
            callback.onEdtContentsLegal();
        } else {
            callback.onEdtContentsIllegal(verifyResult);
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("province", companyAuthReqParams.getProvince());
        params.put("city", companyAuthReqParams.getCity());
        params.put("company_name", companyAuthReqParams.getCompany_name());
        params.put("company_license_no", companyAuthReqParams.getCompany_license_no());
        params.put("company_license_img", companyAuthReqParams.getCompany_license_img());
        params.put("company_boss_name", companyAuthReqParams.getCompany_boss_name());
        params.put("company_boss_tel", companyAuthReqParams.getCompany_boss_tel());
        params.put("id_img_a", companyAuthReqParams.getId_img_a());
        params.put("id_img_b", companyAuthReqParams.getId_img_b());
        params.put("company_other_name", companyAuthReqParams.getCompany_other_name());
        params.put("company_other_tel", companyAuthReqParams.getCompany_other_tel());
        LogUtils.d(TAG,"发起企业认证请求");
        RetrofitFactory.getInstance().createService()
                .companyAuth(RequestUtil.getRequestHashBody(params, false))
                .compose(activity.<BaseEntity>bindToLifecycle())//绑定activity生命周期，防止内存溢出
                .compose(ObservableTransformerUtils.<BaseEntity>io())//选择线程
                .subscribe(new BaseObserver(activity) {

                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        callback.onCompanyAuthSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onCompanyAuthFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onCompanyAuthFailed(e.getMessage());
                            }
                        } else {
                            callback.onCompanyAuthFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callback.onCompanyAuthFailed(msg);
                    }
                });


    }

    private void personalAuthor(BaseActivity activity, PersonalAuthReqParams personalAuthReqParams, final AuthCallback callback) {
        int Illegal = 0;
        for (Map.Entry<String, Boolean> entry : verifyResult.entrySet()) {
            final Boolean value = entry.getValue();
            if (!value) {
                Illegal += 1;
            }
        }
        if (Illegal == 0) {
            callback.onEdtContentsLegal();
        } else {
            callback.onEdtContentsIllegal(verifyResult);
            return;
        }
        HashMap<String,Object> params = new HashMap<>();
        params.put("province", personalAuthReqParams.getProvince());
        params.put("city", personalAuthReqParams.getCity());
        params.put("user_name", personalAuthReqParams.getUser_name());
        params.put("id_no", personalAuthReqParams.getId_no());
        params.put("id_img_a", personalAuthReqParams.getId_img_a());
        params.put("id_img_b", personalAuthReqParams.getId_img_b());
        LogUtils.d(TAG,"发起个人认证请求");
        RetrofitFactory.getInstance().createService()
                .personalAuth(RequestUtil.getRequestHashBody(params, false))
                .compose(activity.<BaseEntity>bindToLifecycle())//绑定activity生命周期，防止内存溢出
                .compose(ObservableTransformerUtils.<BaseEntity>io())//选择线程
                .subscribe(new BaseObserver(activity) {

                    @Override
                    protected void onSuccess(Object o) throws Exception {
                        callback.onPersonalAuthSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onPersonalAuthFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onPersonalAuthFailed(e.getMessage());
                            }
                        } else {
                            callback.onPersonalAuthFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        super.onCodeError(code, msg);
                        callback.onPersonalAuthFailed(msg);
                    }
                });

    }

    public Map<String, Boolean> getVerifyResult() {
        return Collections.unmodifiableMap(verifyResult);
    }

    public interface AuthCallback {

        void onEdtContentsLegal();

        void onEdtContentsIllegal(Map<String, Boolean> verify);

        void onCompanyAuthSuccess();

        void onCompanyAuthFailed(String msg);

        void onPersonalAuthSuccess();

        void onPersonalAuthFailed(String msg);

    }
}
