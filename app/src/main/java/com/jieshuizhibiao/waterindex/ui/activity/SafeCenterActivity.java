package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.Util;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by songxiaotao on 2019/1/14.
 * Class Note:安全中心
 */

public class SafeCenterActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    View title_bar;
    @BindView(R.id.left_ll)
    LinearLayout left_ll;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.tv_user_login_tel)
    TextView tvLoginNumber;

    private boolean isLogin=false;
    private String user_login;//用户登录手机号，作用同isLogin
    private AlertChainDialog alertChainDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, title_bar);
        initData();
        initView();
    }

    private void initData() {
        isLogin= (boolean) SPUtil.get(SafeCenterActivity.this,SPUtil.IS_LOGIN,false);
        user_login=(String)SPUtil.get(SafeCenterActivity.this,SPUtil.USER_LOGIN,"user_login");
    }

    private void initView() {
        tv_title_center.setText("安全中心");
        tvLoginNumber.setText(Util.hide4Phone(user_login));
        alertChainDialog = new AlertChainDialog(SafeCenterActivity.this);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_safe_center);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.left_ll,R.id.tv_change_pass,R.id.tv_set_capital_pass})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_ll:
                goBack(v);
                finish();
                break;
            case R.id.tv_change_pass:
                if (!Util.isFastDoubleClick()){
                    jumpActivity(ChangePassActivity.class);
                }
                break;
            case R.id.tv_set_capital_pass:
                if (!Util.isFastDoubleClick()){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("action","SafeCenterActivity");
                    intent.putExtras(bundle);
                    intent.setClass(SafeCenterActivity.this,SetCapitalPassActivity.class);
                    startActivity(intent);
                }
                break;

            default:break;
        }
    }

    public void jumpActivity(Class<?> cla){
        startActivity(new Intent(SafeCenterActivity.this,cla));
    }
}
