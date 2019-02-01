package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.WaterIndexApplication;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.AddPayMentTypeReqParams;
import com.jieshuizhibiao.waterindex.beans.request.ChangePayMentTypeReqParams;
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
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class ChangePaymentTypeModel {

    private Map<String, Boolean> verifyResult = new HashMap<String, Boolean>();
    private WaterIndexApplication context = WaterIndexApplication.getInstance();

    public Map<String, Boolean> verify(ChangePayMentTypeReqParams params) {
        verifyResult.clear();
        if(!TextUtils.isEmpty(params.getQrcode())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_qrcode), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_pay_type_qrcode), false);
        }
        if(!TextUtils.isEmpty(params.getUser_name())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_name), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_pay_type_name), false);
        }
        if(!TextUtils.isEmpty(params.getAccount_name())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_account), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_pay_type_account), false);
        }
        if(!TextUtils.isEmpty(params.getSafe_pw()) && AccountValidatorUtil.isPassword(params.getSafe_pw())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_capital_pwd), true);
        }else{
            verifyResult.put(context.getString(R.string.key_edt_pay_type_capital_pwd), false);
        }

        return verifyResult;
    }

    public Map<String, Boolean> vertifyBank(ChangePayMentTypeReqParams params){
        verifyResult.clear();
        if(!TextUtils.isEmpty(params.getUser_name())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_name), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_pay_type_name), false);
        }
        if(!TextUtils.isEmpty(params.getBank_name())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_bank_deposit), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_pay_type_bank_deposit), false);
        }
        if(!TextUtils.isEmpty(params.getBank_detail_name())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_bank_deposit_branch), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_pay_type_bank_deposit_branch), false);
        }
        if(!TextUtils.isEmpty(params.getAccount_name())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_bank_number), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_pay_type_bank_number), false);
        }
        if(!TextUtils.isEmpty(params.getSafe_pw()) && AccountValidatorUtil.isPassword(params.getSafe_pw())){
            verifyResult.put(context.getString(R.string.key_edt_pay_type_capital_pwd), true);
        }else{
            verifyResult.put(context.getString(R.string.key_edt_pay_type_capital_pwd), false);
        }

        return verifyResult;
    }

    public void changePaymentType(BaseActivity activity, ChangePayMentTypeReqParams params, final String changeType,final ChangePayMentTypeCallBack callBack){
        int Illegal = 0;
        for (Map.Entry<String, Boolean> entry : verifyResult.entrySet()) {
            final Boolean value = entry.getValue();
            if (!value) {
                Illegal += 1;
            }
        }
        if (Illegal == 0) {
            callBack.onChangeContentsLegal();
        } else if(changeType.equals(HttpConfig.BANK_TYPE)){
            callBack.onChangeEdtContentsIllegalBank(verifyResult);
            return;
        } else {
            callBack.onChangeEdtContentsIllegal(verifyResult);
            return;
        }

        RetrofitFactory.getInstance().createService()
                .changePayMentType(RequestUtil.getRequestBeanBody(params,true))
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

    public interface ChangePayMentTypeCallBack{
        void onChangeContentsLegal();
        void onChangeEdtContentsIllegal(Map<String, Boolean> verify);
        void onChangeEdtContentsIllegalBank(Map<String, Boolean> verify);
        void success();
        void failed(String msg);
    }
}
