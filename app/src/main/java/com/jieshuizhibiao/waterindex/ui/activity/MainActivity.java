package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.ActivityManager;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.AppUpdateResponseBean;
import com.jieshuizhibiao.waterindex.contract.presenter.AppUpdatePresenter;
import com.jieshuizhibiao.waterindex.contract.view.AppUpdateViewImpl;
import com.jieshuizhibiao.waterindex.event.LoginStatusChangedEvent;
import com.jieshuizhibiao.waterindex.event.SelectFragmentEvent;
import com.jieshuizhibiao.waterindex.ui.fragment.OrderListsFragment;
import com.jieshuizhibiao.waterindex.ui.fragment.PersonalFragment;
import com.jieshuizhibiao.waterindex.ui.fragment.TradeIndexFragment;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class MainActivity extends BaseActivity implements AppUpdateViewImpl {

    @BindView(R.id.title_bar)
    View titleBar;
    @BindView(R.id.left_ll)
    LinearLayout left_ll;

    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.ll)
    public LinearLayout ll;
    @BindView(R.id.rg_main)
    public RadioGroup rg_main;
    @BindView(R.id.rb3)
    public RadioButton rb3;
    @BindView(R.id.rb4)
    public RadioButton rb4;
    @BindView(R.id.rb5)
    public RadioButton rb5;

    private Fragment currentFragment, tab1, tab2, tab3;
    private long mExitTime;
    private AppUpdatePresenter appUpdatePresenter;
    private AlertChainDialog alertChainDialog;
    private boolean isUpdate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, titleBar);
        ButterKnife.bind(this);
        initView();
        EventBus.getDefault().register(this);
        appUpdatePresenter = new AppUpdatePresenter();
        appUpdatePresenter.attachView(this);
        checkAppVer();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkAppVer();
    }

    /**
     * 初始化页面Ui
     */
    @Override
    public void initContentView() {
        setContentView(R.layout.activity_main);
        alertChainDialog = new AlertChainDialog(this);
    }


    /**
     * 网络状况改变情况下 重试刷新数据
     *
     * @param viewId
     */
    @Override
    public void onReNetRefreshData(int viewId) {

    }

    private void createFragments() {
        showTradeIndex();
    }


    private void initView() {
        left_ll.setVisibility(View.GONE);
        createFragments();
    }

    @OnClick({R.id.rb3, R.id.rb4, R.id.rb5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb3:
                showTradeIndex();
                break;
            case R.id.rb4:
                showOrderLists();
                break;
            case R.id.rb5:
                showPersonal();
                break;
            default:
                break;
        }
    }

    private void checkAppVer() {
        if (appUpdatePresenter == null) {
            appUpdatePresenter = new AppUpdatePresenter();
            appUpdatePresenter.attachView(this);
        }
        appUpdatePresenter.appVersion(this, Util.versionName(this));
    }


    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            if (currentFragment == null)
                transaction.add(R.id.activity_main_ll, targetFragment).commit();
            else
                transaction.hide(currentFragment).add(R.id.activity_main_ll, targetFragment).commit();
        } else {
//            transaction.hide(currentFragment).show(targetFragment).commit();//无法接收Event
            transaction.hide(currentFragment).show(targetFragment).commitAllowingStateLoss();
        }
        currentFragment = targetFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showCustomToastMsg("再按一次退出程序", 150);
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityManager.AppExit(mContext);
        }
    }


    /*****************by sxt****************/
    public void onEventMainThread(SelectFragmentEvent event) {
        if (event != null) {
            String title = event.getTitle();
            switch (title) {
                case "交易市场":
                    showTradeIndex();
                    EventBus.getDefault().post(new LoginStatusChangedEvent("login_status_changed_main_trade_index_reconnect"));
                    break;
                case "订单":
                    showOrderLists();
                    break;
                case "我的":
                    showPersonal();
                    break;
                default:
                    showTradeIndex();
                    break;
            }
        }
    }


    private void showTradeIndex() {
        rb3.setChecked(true);
        tv_title_center.setText("交易市场");
        if (tab1 == null) tab1 = new TradeIndexFragment();
        switchFragment(tab1);
    }

    private void showOrderLists() {
        rb4.setChecked(true);
        tv_title_center.setText("订单");
        if (tab2 == null) tab2 = new OrderListsFragment();
        switchFragment(tab2);
    }

    private void showPersonal() {
        rb5.setChecked(true);
        tv_title_center.setText("我的");
        if (tab3 == null) tab3 = new PersonalFragment();
        switchFragment(tab3);
    }

    private String getSp() {
        boolean spIsLogin = (boolean) SPUtil.get(this, SPUtil.IS_LOGIN, false);
        String spUser_login = (String) SPUtil.get(this, SPUtil.USER_LOGIN, "token");
        return new StringBuilder().append(spIsLogin).append(spUser_login).toString();
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (appUpdatePresenter != null) appUpdatePresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onAppUpdateSuccess(Object bean) {
        if (bean instanceof AppUpdateResponseBean) {
            String version = ((AppUpdateResponseBean) bean).getVer();
            if (TextUtils.isEmpty(version)) {
                isUpdate = false;
                return;
            } else {
                String[] versionService = version.split(".");
                String[] versionLocal = Util.versionName(this).split(".");
                int service = Integer.parseInt(versionService[0] + versionService[1] + versionService[2]);
                int local = Integer.parseInt(versionLocal[0] + versionLocal[1] + versionLocal[2]);
                if (TextUtils.isEmpty(version) && service > local) {
                    isUpdate = true;
                } else {
                    isUpdate = false;
                }
            }
        }
        if (isUpdate) {
            if (alertChainDialog != null) {
                if (alertChainDialog != null) {
                    alertChainDialog.builder().setCancelable(false);
                    alertChainDialog.setTitle("提示消息")
                            .setMsg(isUpdate ? "有新版可供更新" : "当前已是最新版本")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (isUpdate) {
                                        update();
                                    }
                                }


                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }
            }
        }
    }


    @Override
    public void onAppUpdateFailed(String msg) {
        ToastUtils.showCustomToast(msg, 1);
    }

    private void update() {
        //TODO 版本更新
    }

}