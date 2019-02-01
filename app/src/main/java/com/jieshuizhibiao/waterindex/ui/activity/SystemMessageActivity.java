package com.jieshuizhibiao.waterindex.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.SystemMsgResponseBean;
import com.jieshuizhibiao.waterindex.contract.presenter.SystemMsgPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.ui.adapter.SystemMsgAdapter;
import com.jieshuizhibiao.waterindex.utils.LogUtils;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: fushizhe
 */
public class SystemMessageActivity extends BaseActivity implements CommonViewImpl {
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.title_bar)
    View title_bar;
    @BindView(R.id.rc_message_list)
    XRecyclerView messageList;
    @BindView(R.id.relative_hint)
    RelativeLayout relativeHint;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    private int currentPage = 1, pageSize = 5;
    private SystemMsgPresenter systemMsgPresenter;
    private SystemMsgAdapter systemMsgAdapter;
    private List<SystemMsgResponseBean.SystemMsgList> msgLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setImmersionStatus(this, title_bar);
        initView();

        systemMsgPresenter = new SystemMsgPresenter();
        systemMsgPresenter.attachView(this);


    }

    private void initView() {
        tv_title_center.setText("系统消息");
        systemMsgAdapter = new SystemMsgAdapter(SystemMessageActivity.this, msgLists, new SystemMsgAdapter.MessageClickListerer() {
            @Override
            public void onItemClick(int id) {

            }
        });
        messageList.setArrowImageView(R.drawable.iconfont_downgrey);
        messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.addItemDecoration(new SpaceItemDecoration(Util.px2px(this, 10, 1334)));
        messageList.setAdapter(systemMsgAdapter);
        messageList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;//后期数据需要分页时再用
                doReuqestMessage();
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                doReuqestMessage();
            }
        });
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_message);
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doReuqestMessage();
    }

    public void doReuqestMessage(){
        systemMsgPresenter.getSystemMsg(SystemMessageActivity.this);
        showLoadingDialog();
    }

    @OnClick({R.id.left_ll})
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.left_ll:
                goBack(view);
                break;

            default:
                break;
        }

    }


    @Override
    public void onRequestSuccess(Object bean) {
        dismissLoadingDialog();
        msgLists.clear();
        if (bean instanceof SystemMsgResponseBean){
            msgLists.addAll(((SystemMsgResponseBean) bean).getLists());
            if (msgLists.size() <= 0) {
                messageList.setVisibility(View.GONE);
                relativeHint.setVisibility(View.VISIBLE);
                tvDetail.setText("您还没有任何消息！");
                tvDetail.setTextColor(getResources().getColor(R.color.text_black));
                tvDetail.setEnabled(false);
            } else {
                systemMsgAdapter.notifyDataSetChanged();
                messageList.refreshComplete();
                messageList.setVisibility(View.VISIBLE);
                relativeHint.setVisibility(View.GONE);
                tvDetail.setEnabled(false);
            }

        }
    }

    @Override
    public void onRequestFailed(String msg) {
        dismissLoadingDialog();
        systemMsgAdapter.notifyDataSetChanged();
        messageList.refreshComplete();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doReuqestMessage();
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            outRect.bottom = space;
            if (parent.getChildPosition(view) != 0) {
                outRect.top = space;
            }
        }
    }
}
