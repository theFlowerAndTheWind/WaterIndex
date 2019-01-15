package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: fushizhe
 */
public class AboutListActivity extends BaseActivity{
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.title_bar)
    View title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setImmersionStatus(this, title_bar);
        initView();
    }

    private void initView() {
        tv_title_center.setText("关于我们");
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_about_list);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.left_ll,R.id.img_title_left,R.id.btn_about})
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.img_title_left:
                goBack(view);
                break;
            case R.id.left_ll:
                goBack(view);
                break;

            default:
                break;
        }

    }



}