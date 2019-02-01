package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.AuthDetailResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserInfoResponseBean;
import com.jieshuizhibiao.waterindex.contract.model.AuthDetailModel;
import com.jieshuizhibiao.waterindex.contract.model.UserInfoModel;
import com.jieshuizhibiao.waterindex.contract.presenter.AuthDetailPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.UserInfoPresenter;
import com.jieshuizhibiao.waterindex.contract.view.AuthDetailViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.UserInfoViewImpl;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: fushizhe
 */
public class UserConfirmActivity extends BaseActivity implements UserInfoViewImpl {
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.title_bar)
    View title_bar;

    @BindView(R.id.ll_did_not)
    LinearLayout llDidNot;
    @BindView(R.id.ll_ing)
    LinearLayout llIng;
    @BindView(R.id.ll_done)
    LinearLayout llDone;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_id_no)
    TextView tvIdNo;
    UserInfoPresenter userInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoPresenter = new UserInfoPresenter(new UserInfoModel());
        userInfoPresenter.attachView(this);
        StatusBarUtil.setImmersionStatus(this, title_bar);
        initView();
    }

    private void initView() {
        tv_title_center.setText("身份认证");
        //默认显示未认证页面
        llDidNot.setVisibility(View.VISIBLE);
        llIng.setVisibility(View.GONE);
        llDone.setVisibility(View.GONE);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_confirm);
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doUserInfoReuqest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doUserInfoReuqest();
    }

    public void doUserInfoReuqest(){
        userInfoPresenter.getUserInfo(UserConfirmActivity.this);
        showLoadingDialog();
    }

    @OnClick({R.id.left_ll, R.id.img_title_left, R.id.tv_do_auth})
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.img_title_left:
            case R.id.left_ll:
                goBack(view);
                finish();
                break;
            case R.id.tv_do_auth:
                startActivity(new Intent(UserConfirmActivity.this, AuthActivity.class));
                break;

            default:
                break;
        }

    }

    private void selectLayout(UserInfoResponseBean userInfoResponseBean) {
        if (userInfoResponseBean != null) {
            int isAuth = userInfoResponseBean.getIs_auth();
            //0 未认证|被驳回 1认证审核中 3通过
            if (isAuth == 0) {
                llDidNot.setVisibility(View.VISIBLE);
                llIng.setVisibility(View.GONE);
                llDone.setVisibility(View.GONE);
            } else if (isAuth == 1) {
                llDidNot.setVisibility(View.GONE);
                llIng.setVisibility(View.VISIBLE);
                llDone.setVisibility(View.GONE);
            } else {
                llDidNot.setVisibility(View.GONE);
                llIng.setVisibility(View.GONE);
                llDone.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onUserInfoSuccess(UserInfoResponseBean userInfoResponseBean) {
        dismissLoadingDialog();
        selectLayout(userInfoResponseBean);
    }

    @Override
    public void onUserInfoFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userInfoPresenter!=null){
            userInfoPresenter.detachView();
        }
    }
}
