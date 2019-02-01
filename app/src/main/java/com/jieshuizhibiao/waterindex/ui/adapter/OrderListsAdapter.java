package com.jieshuizhibiao.waterindex.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.beans.ListOrder;
import com.jieshuizhibiao.waterindex.ui.activity.TraderUnpayActivity;
import com.jieshuizhibiao.waterindex.utils.image.GlidImageManager;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListsAdapter extends RecyclerView.Adapter<OrderListsAdapter.OrderListViewHolder> {


    private Context context;
    private List<ListOrder> list;
    private onItemClickedListener listener;

    public OrderListsAdapter(Context context, List<ListOrder> list, onItemClickedListener listener) {
        this.context = context;
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.module_recycle_item_order_lists, parent, false);
        AutoUtils.auto(v);
        return new OrderListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        final ListOrder order = list.get(position);
        if (order == null) return;
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(order);
            }
        });
        GlidImageManager.getInstance().loadCircleImg(context, order.getOther_avatar(), holder.imgOtherAvatar, R.mipmap.head, R.mipmap.head);
        holder.tvOtherNickname.setText(order.getOther_nickname());
        holder.tvCreatetime.setText(order.getCreatetime());
        String my_action = order.getMy_action();
        if (my_action.contains("购买")) {
            holder.tvMyAction.setTextColor(context.getResources().getColor(R.color.primary_red));
        } else if (my_action.contains("出售")) {
            holder.tvMyAction.setTextColor(context.getResources().getColor(R.color.text_green));
        }
        holder.tvMyAction.setText(order.getMy_action());
        holder.tvTotal.setText(order.getTotal());
        holder.tvStatusText.setText(order.getStatus_text());
        String rmbStr = TraderUnpayActivity.formatRmb(order.getRmb(),"元");
        holder.tvRmb.setText(rmbStr);

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    class OrderListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.img_other_avatar)
        ImageView imgOtherAvatar;
        @BindView(R.id.tv_other_nickname)
        TextView tvOtherNickname;
        @BindView(R.id.tv_createtime)
        TextView tvCreatetime;
        @BindView(R.id.tv_my_action)
        TextView tvMyAction;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.tv_rmb)
        TextView tvRmb;
        @BindView(R.id.tv_status_text)
        TextView tvStatusText;

        public OrderListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onItemClickedListener {
        void onItemClicked(ListOrder order);
    }

}
