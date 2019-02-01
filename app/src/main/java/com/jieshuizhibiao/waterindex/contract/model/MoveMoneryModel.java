package com.jieshuizhibiao.waterindex.contract.model;

import android.text.TextUtils;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
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
 * Class Note:移交资产
 */

public class MoveMoneryModel {

    public void moveMonery(BaseActivity activity, MoveMoneryReqParams moveMoneryReqParams,final MoveMoneryCallBack callBack){
        HashMap<String,Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(moveMoneryReqParams.getType())){
            params.put("type",moveMoneryReqParams.getType());
        }
        if (!TextUtils.isEmpty(moveMoneryReqParams.getTotal())){
            params.put("total",moveMoneryReqParams.getTotal());
        }
        if (!TextUtils.isEmpty(moveMoneryReqParams.getSafe_pw())){
            params.put("safe_pw",moveMoneryReqParams.getSafe_pw());
        }

        RetrofitFactory.getInstance().createService()
                .moveMonery(RequestUtil.getRequestHashBody(params,false))
                .compose(activity.<BaseEntity<MoveMoneryBean>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<MoveMoneryBean>>io())
                .subscribe(new BaseObserver<MoveMoneryBean>(activity) {
                    @Override
                    protected void onSuccess(MoveMoneryBean moveMoneryBean) throws Exception {
                        callBack.success(moveMoneryBean);
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

    public interface MoveMoneryCallBack{
        void success(MoveMoneryBean moveMoneryBean);
        void failed(String msg);
    }
}
