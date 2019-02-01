package com.jieshuizhibiao.waterindex.contract.presenter;

import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.AccountDetailResponseBean;
import com.jieshuizhibiao.waterindex.contract.BasePresenter;
import com.jieshuizhibiao.waterindex.contract.model.AccountDetailModel;
import com.jieshuizhibiao.waterindex.contract.view.AccountDetailViewImpl;

/**
 * Created by songxiaotao on 2018/12/12.
 * Class Note:
 */

public class AccountDetailPresenter extends BasePresenter<AccountDetailViewImpl> {

    private AccountDetailModel accountDetailModel;

    public AccountDetailPresenter(){}

    public AccountDetailPresenter(AccountDetailModel accountDetailModel){
        this.accountDetailModel = accountDetailModel;
    }

    public void getAccountDetail(BaseActivity activity){
        if(accountDetailModel == null){
            accountDetailModel = new AccountDetailModel();
        }
        accountDetailModel.getAccountDetail(activity, new AccountDetailModel.AccountDetailCallBack() {
            @Override
            public void success(AccountDetailResponseBean accountDetailResponseBean) {
                if(mView!=null){
                    mView.onAccountDetailSuccess(accountDetailResponseBean);
                }
            }

            @Override
            public void failed(String msg) {
                if(mView!=null){
                    mView.onAccountDetailFailed(msg);
                }

            }
        });
    }
}
