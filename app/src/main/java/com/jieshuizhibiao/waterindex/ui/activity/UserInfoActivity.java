package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UserInfoResponseBean;
import com.jieshuizhibiao.waterindex.contract.model.UserInfoModel;
import com.jieshuizhibiao.waterindex.contract.presenter.UserInfoPresenter;
import com.jieshuizhibiao.waterindex.contract.view.UserInfoViewImpl;
import com.jieshuizhibiao.waterindex.event.SelectFragmentEvent;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;
import com.jieshuizhibiao.waterindex.utils.image.GlidImageManager;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class UserInfoActivity extends BaseActivity implements UserInfoViewImpl {

    @BindView(R.id.title_bar)
    View titleBar;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tv_user_login)
    TextView tvUserLogin;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.relative_user_detail)
    RelativeLayout relativeUserDetail;
    @BindView(R.id.tv_user_login_tel)
    TextView tvUserLoginTel;
    @BindView(R.id.tv_user_type)
    TextView tvUserType;
    @BindView(R.id.tv_change_pass)
    TextView tvUserPass;
    @BindView(R.id.tv_auth_status)
    TextView tvAuthStatus;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    private UserInfoPresenter userInfoPresenter;
    private AlertChainDialog alertChainDialog;
    private String userLogin = "" ,avatar_url = "";
    @OnClick({R.id.left_ll, R.id.tv_change_pass, R.id.tv_auth_status, R.id.btn_logout})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.left_ll:
                goBack(v);
                finish();
                break;
            case R.id.tv_change_pass:
                jump(ChangePassActivity.class);
                break;

            case R.id.tv_auth_status:
                jump(AuthActivity.class);
                break;
            case R.id.btn_logout:
                if(alertChainDialog!=null){
                    alertChainDialog.builder().setCancelable(false);
                    alertChainDialog.setTitle("提示消息")
                            .setMsg("确认退出当前账号")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loginOut();
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, titleBar);
        initView();

        userInfoPresenter = new UserInfoPresenter(new UserInfoModel());
        userInfoPresenter.attachView(this);

    }

    private void initView() {
        tvTitleCenter.setText("账户信息");
        userLogin = (String)SPUtil.get(this,SPUtil.USER_LOGIN,"user_login");
        tvUserLogin.setText(TextUtils.isEmpty(userLogin) ? "" :Util.hide4Phone(userLogin));
        alertChainDialog = new AlertChainDialog(this);
    }

    private void jump(Class<?> cls) {
        startActivity(new Intent(UserInfoActivity.this, cls));
    }


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_detail);
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doReuqest();
    }


    public void loginOut() {
        //一起操作
        //            SPUtil.delete(this,SPUtil.IS_LOGIN);
        SPUtil.delete(this, SPUtil.TOKEN);
        SPUtil.insert(UserInfoActivity.this, SPUtil.IS_LOGIN, false);//更改用户登录状态为未登录

        SPUtil.delete(this, SPUtil.USER_LOGIN);
        SPUtil.delete(this, SPUtil.UID);

        SPUtil.delete(this, SPUtil.ID);
        SPUtil.delete(this, SPUtil.IS_BLOCKED);
        SPUtil.delete(this, SPUtil.USER_LOGIN);
        SPUtil.delete(this, SPUtil.USER_NICKNAME);
        SPUtil.delete(this, SPUtil.TOKEN);


        jump(MainActivity.class);
        EventBus.getDefault().post(new SelectFragmentEvent("我的"));//显示personalFragment
//            EventBus.getDefault().post(new LogoutEvent("logout_main_personal_refresh_nickname"));//头像右侧用户名显示
//            EventBus.getDefault().post(new LogoutEvent("logout_main_transaction_reconnect"));//transaction重新请求刷新
        finish();
    }

    @Override
    public void onUserInfoSuccess(UserInfoResponseBean userInfoResponseBean) {
        dismissLoadingDialog();
        if (userInfoResponseBean != null) {
            avatar_url = userInfoResponseBean.getAvatar();
            SPUtil.insert(this, SPUtil.USER_AVATAR,avatar_url);
            tvCreateTime.setText(userInfoResponseBean.getCreate_time());
            tvUserLoginTel.setText(TextUtils.isEmpty(userInfoResponseBean.getUser_login()) ? Util.hide4Phone(userLogin): userInfoResponseBean.getUser_login());
            int userStatus = userInfoResponseBean.getIs_auth();
            if (userStatus == 0) {
                tvAuthStatus.setText("去认证");
                tvAuthStatus.setTextColor(getResources().getColor(R.color.primary_yellow));
                tvAuthStatus.setEnabled(true);
            } else if (userStatus == 1) {
                tvAuthStatus.setText("已认证");
                tvAuthStatus.setTextColor(getResources().getColor(R.color.text_black));
                tvAuthStatus.setEnabled(false);
            }
        }
    }

    @Override
    public void onUserInfoFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doReuqest();
        showAvatar();
    }

    public void doReuqest(){
        userInfoPresenter.getUserInfo(this);
        showLoadingDialog();
    }

    /**
     * 使用存储的头像地址
     */
    private void showAvatar() {

        String avatarUrl = (String) SPUtil.get(UserInfoActivity.this,SPUtil.USER_AVATAR,"");
        //显示头像
        GlidImageManager.getInstance().loadCircleImg(UserInfoActivity.this, avatarUrl, imgAvatar, R.mipmap.head, R.mipmap.head);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userInfoPresenter!=null){
            userInfoPresenter.detachView();
        }
    }
}
