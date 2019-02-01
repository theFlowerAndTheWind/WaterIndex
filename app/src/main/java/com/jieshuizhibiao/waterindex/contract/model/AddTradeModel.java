package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.WaterIndexApplication;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.request.AddTradeReqParams;
import com.jieshuizhibiao.waterindex.beans.request.SetCaptialPassReqParams;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.ui.fragment.TranscationAllFragment;
import com.jieshuizhibiao.waterindex.utils.AccountValidatorUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:发布需求列表
 */

public class AddTradeModel {

    private Map<String, Boolean> verifyResult = new HashMap<String, Boolean>();
    private WaterIndexApplication context = WaterIndexApplication.getInstance();

    public Map<String, Boolean> verify(AddTradeReqParams params,String action/*,String transferNumber*/) {
        verifyResult.clear();
        if(!TextUtils.isEmpty(params.getSafe_pw()) && AccountValidatorUtil.isPassword(params.getSafe_pw())){
            verifyResult.put(context.getString(R.string.key_edt_release_capital_pwd), true);
        } else {
            verifyResult.put(context.getString(R.string.key_edt_release_capital_pwd), false);
        }

        if (!TextUtils.isEmpty(action) && action.equals("Sell")){
            if(!TextUtils.isEmpty(params.getCond_pay_timeout()) && Integer.parseInt(params.getCond_pay_timeout())<=30 && Integer.parseInt(params.getCond_pay_timeout())>=15){
                verifyResult.put(context.getString(R.string.key_edt_release_time_limit), true);
            }else{
                verifyResult.put(context.getString(R.string.key_edt_release_time_limit), false);
            }
        }

        if(!TextUtils.isEmpty(params.getTotal())){
            verifyResult.put(context.getString(R.string.key_edt_release_transaction_max), true);
        }else{
            verifyResult.put(context.getString(R.string.key_edt_release_transaction_max), false);
        }

        if(!TextUtils.isEmpty(params.getPay_min())){
            verifyResult.put(context.getString(R.string.key_edt_release_transaction_min), true);
        }else{
            verifyResult.put(context.getString(R.string.key_edt_release_transaction_min), false);
        }
        return verifyResult;
    }

    public void addTrade(BaseActivity activity, AddTradeReqParams params,String action,final AddTradeCallBack callBack){
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
            if (!TextUtils.isEmpty(action) && action.equals(TranscationAllFragment.EXTRA_SELL)){
                callBack.onEdtContentsIllegalSell(verifyResult);
                return;
            }else {
                callBack.onEdtContentsIllegalBuy(verifyResult);
                return;
            }
        }

        RetrofitFactory.getInstance().createService()
                .addTrade(RequestUtil.getRequestBeanBody(params,true))
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

    public interface AddTradeCallBack{
        void onEdtContentsLegal();
        void onEdtContentsIllegalSell(Map<String, Boolean> verify);
        void onEdtContentsIllegalBuy(Map<String, Boolean> verify);
        void success();
        void failed(String msg);
    }
}
