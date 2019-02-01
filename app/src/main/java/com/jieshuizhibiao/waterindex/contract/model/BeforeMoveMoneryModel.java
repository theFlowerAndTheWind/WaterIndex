package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.BeforeMvMoneyResponse;
import com.jieshuizhibiao.waterindex.beans.MoveMoneryBean;
import com.jieshuizhibiao.waterindex.beans.request.MoveMoneryReqParams;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:移交资产前确认
 */

public class BeforeMoveMoneryModel {

    public void beforeMoveMonery(BaseActivity activity, MoveMoneryReqParams reqParams, final BeforeMoveMoneryCallBack callBack) {
        HashMap<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(reqParams.getType())) {
            params.put("type", reqParams.getType());
        }
        if (!TextUtils.isEmpty(reqParams.getTotal())) {
            params.put("total", reqParams.getTotal());
        }
        if (!TextUtils.isEmpty(reqParams.getSafe_pw())) {
            params.put("safe_pw", reqParams.getSafe_pw());
        }
        RetrofitFactory.getInstance().createService()
                .beforeMvMoney(RequestUtil.getRequestHashBody(params, false))
                .compose(activity.<BaseEntity<BeforeMvMoneyResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<BeforeMvMoneyResponse>>io())
                .subscribe(new BaseObserver<BeforeMvMoneyResponse>(activity) {
                    @Override
                    protected void onSuccess(BeforeMvMoneyResponse beforeMvMoneyResponse) throws Exception {
                        callBack.success(beforeMvMoneyResponse);
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

    public interface BeforeMoveMoneryCallBack {
        void success(BeforeMvMoneyResponse beforeMvMoneyResponse);

        void failed(String msg);
    }
}
