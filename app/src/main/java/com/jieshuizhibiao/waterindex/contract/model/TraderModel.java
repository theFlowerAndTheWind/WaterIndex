package com.jieshuizhibiao.waterindex.contract.model;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.appeal.AppealResponse;
import com.jieshuizhibiao.waterindex.beans.cancel.CancelResponse;
import com.jieshuizhibiao.waterindex.beans.paid.BuyerPaidResponse;
import com.jieshuizhibiao.waterindex.beans.paid.SellerPaidResponse;
import com.jieshuizhibiao.waterindex.beans.succ.SuccResponse;
import com.jieshuizhibiao.waterindex.beans.unpay.BuyerUnpayResponse;
import com.jieshuizhibiao.waterindex.beans.unpay.SellerUnpayResponse;
import com.jieshuizhibiao.waterindex.contract.model.callback.CommonCallback;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.utils.LogUtils;

import java.util.HashMap;

public class TraderModel {

    public void buyerUnpay(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .buyerUnpay(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<BuyerUnpayResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<BuyerUnpayResponse>>io())
                .subscribe(new BaseObserver<BuyerUnpayResponse>(activity) {
                    @Override
                    protected void onSuccess(BuyerUnpayResponse buyerUnpayResponse) throws Exception {
                        callback.onRequestSuccess(buyerUnpayResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void sellerUnpay(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .sellerUnpay(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<SellerUnpayResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<SellerUnpayResponse>>io())
                .subscribe(new BaseObserver<SellerUnpayResponse>(activity) {
                    @Override
                    protected void onSuccess(SellerUnpayResponse sellerUnpayResponse) throws Exception {
                        callback.onRequestSuccess(sellerUnpayResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void buyerPaid(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .buyerPaid(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<BuyerPaidResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<BuyerPaidResponse>>io())
                .subscribe(new BaseObserver<BuyerPaidResponse>(activity) {
                    @Override
                    protected void onSuccess(BuyerPaidResponse buyerPaidResponse) throws Exception {
                        callback.onRequestSuccess(buyerPaidResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void sellerPaid(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .sellerPaid(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<SellerPaidResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<SellerPaidResponse>>io())
                .subscribe(new BaseObserver<SellerPaidResponse>(activity) {
                    @Override
                    protected void onSuccess(SellerPaidResponse sellerPaidResponse) throws Exception {
                        callback.onRequestSuccess(sellerPaidResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void buyerAppeal(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .buyerAppeal(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<AppealResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<AppealResponse>>io())
                .subscribe(new BaseObserver<AppealResponse>(activity) {
                    @Override
                    protected void onSuccess(AppealResponse appealResponse) throws Exception {
                        callback.onRequestSuccess(appealResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void sellerAppeal(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .sellerAppeal(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<AppealResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<AppealResponse>>io())
                .subscribe(new BaseObserver<AppealResponse>(activity) {
                    @Override
                    protected void onSuccess(AppealResponse appealResponse) throws Exception {
                        callback.onRequestSuccess(appealResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void buyerSucc(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .buyerSucc(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<SuccResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<SuccResponse>>io())
                .subscribe(new BaseObserver<SuccResponse>(activity) {
                    @Override
                    protected void onSuccess(SuccResponse succResponse) throws Exception {
                        callback.onRequestSuccess(succResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void sellerSucc(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .sellerSucc(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<SuccResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<SuccResponse>>io())
                .subscribe(new BaseObserver<SuccResponse>(activity) {
                    @Override
                    protected void onSuccess(SuccResponse succResponse) throws Exception {
                        callback.onRequestSuccess(succResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void buyerCancel(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .buyerCancel(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<CancelResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<CancelResponse>>io())
                .subscribe(new BaseObserver<CancelResponse>(activity) {
                    @Override
                    protected void onSuccess(CancelResponse cancelResponse) throws Exception {
                        callback.onRequestSuccess(cancelResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    public void sellerCancel(BaseActivity activity, long order_id, final CommonCallback callback) {

        RetrofitFactory.getInstance().createService()
                .sellerCancel(RequestUtil.getRequestHashBody(initParams(order_id), false))
                .compose(activity.<BaseEntity<CancelResponse>>bindToLifecycle())
                .compose(ObservableTransformerUtils.<BaseEntity<CancelResponse>>io())
                .subscribe(new BaseObserver<CancelResponse>(activity) {
                    @Override
                    protected void onSuccess(CancelResponse cancelResponse) throws Exception {
                        callback.onRequestSuccess(cancelResponse);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (e != null && e.getMessage() != null) {
                            if (isNetWorkError) {
                                LogUtils.e(e.getMessage());
                                callback.onRequestFailed(HttpConfig.ERROR_MSG);
                            } else {
                                callback.onRequestFailed(e.getMessage());
                            }
                        } else {
                            callback.onRequestFailed("");
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String msg) throws Exception {
                        callback.onRequestFailed(msg);
                    }
                });

    }

    private HashMap<String, Object> initParams(long order_id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("order_id", String.valueOf(order_id));
        return params;
    }


}
