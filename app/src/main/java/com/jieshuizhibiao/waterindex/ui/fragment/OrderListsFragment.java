package com.jieshuizhibiao.waterindex.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.ui.adapter.OrderListsViewpagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderListsFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    private Unbinder unbinder;

    public String[] titles = new String[]{"全部", "进行中", "已完成", "已取消", "申诉处理"};
    private ArrayList<OrderListsTabFragment> fragments = new ArrayList<>();
    private OrderListsViewpagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_lists, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {

        for (int i = 0; i < titles.length; i++) {
            OrderListsTabFragment fragment = new OrderListsTabFragment();
            fragment.setTitle(titles[i],getBaseActivity());
            fragments.add(fragment);
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(viewpager, false);
        adapter = new OrderListsViewpagerAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        viewpager.setAdapter(adapter);
    }


    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
