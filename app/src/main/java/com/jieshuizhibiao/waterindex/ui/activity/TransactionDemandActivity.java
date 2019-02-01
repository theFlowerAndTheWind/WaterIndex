package com.jieshuizhibiao.waterindex.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.ListTradeResponseBean;
import com.jieshuizhibiao.waterindex.beans.request.ListTradeReqParams;
import com.jieshuizhibiao.waterindex.contract.presenter.ListTradePresenter;
import com.jieshuizhibiao.waterindex.contract.view.ListTradeViewImpl;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.ui.adapter.TableViewpagerAdapter;
import com.jieshuizhibiao.waterindex.ui.fragment.TranscationAllFragment;
import com.jieshuizhibiao.waterindex.ui.fragment.TranscationBuyFragment;
import com.jieshuizhibiao.waterindex.ui.fragment.TranscationSellFragment;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by songxiaotao on 2019/1/14.
 * Class Note:买卖需求
 */

public class TransactionDemandActivity extends BaseActivity implements ListTradeViewImpl {

    @BindView(R.id.left_ll)
    LinearLayout leftLl;
    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.transcation_demand_tablayout)
    TabLayout transcationTabLayout;
    @BindView(R.id.transcation_demand_viewpager)
    ViewPager transcationViewpager;


    private TableViewpagerAdapter adapter;
    private String[] titles=new String[]{"全部","购买","出售"};
    private List<Fragment> fragments=new ArrayList<>();
    private ListTradePresenter listTradePresenter;
    private int pageSize = 5, currentPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, titleBar);

        listTradePresenter = new ListTradePresenter();
        listTradePresenter.attachView(this);
        initView();
    }

    private void initView() {
        tvTitleCenter.setText("买卖需求");
        initTransaction();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_transaction_demand);
    }

    public void doReuqest(){
        ListTradeReqParams params = new ListTradeReqParams();
        params.setPage(String.valueOf(currentPage));
        params.setPage_size(String.valueOf(pageSize));
        params.setType(HttpConfig.TRANSCATION_RELEASE_All);
        listTradePresenter.getListTrade(TransactionDemandActivity.this,params);
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doReuqest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doReuqest();
    }

    @OnClick({R.id.left_ll})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.left_ll:
                goBack(view);
                break;
            default:
                break;
        }

    }

    public void initTransaction(){
        fragments.add(new TranscationAllFragment());
        fragments.add(new TranscationBuyFragment());
        fragments.add(new TranscationSellFragment());

        adapter = new TableViewpagerAdapter(getSupportFragmentManager(),fragments,titles);
        transcationViewpager.setAdapter(adapter);
        transcationViewpager.setCurrentItem(0);//默认选中第一项
        transcationTabLayout.setupWithViewPager(transcationViewpager,false);
        transcationViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0://默示什么都没做
                        break;
                    case 1://默认正在滑动
                        break;
                    case 2://默认滑动完毕
                        break;
                    default:break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        transcationTabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position= (int) view.getTag();
                boolean isSelect = transcationTabLayout.getTabAt(position).isSelected();
                if (position==0 && isSelect){
                    transcationViewpager.setCurrentItem(0);
                }else if (position==1 && isSelect){
                    transcationViewpager.setCurrentItem(1);
                }else {
                    TabLayout.Tab tab = transcationTabLayout.getTabAt(position);
                    if (tab != null) {
                        tab.select();
                    }
                    transcationViewpager.setCurrentItem(2);
                }

            }
        });
    }

    @Override
    public void onListTradeSuccess(ListTradeResponseBean bean) {

    }

    @Override
    public void onloadMoreListTrade(List<ListTradeResponseBean.TradeList> tradeLists) {

    }

    @Override
    public void onRefreshListTrade(List<ListTradeResponseBean.TradeList> tradeLists) {

    }

    @Override
    public void onLoadListTradeError(boolean isRefresh, String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listTradePresenter!=null){
            listTradePresenter.detachView();
        }
    }


}
