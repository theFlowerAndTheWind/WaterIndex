package com.jieshuizhibiao.waterindex.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.beans.ListTradeResponseBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * @author WanghongHe
 * @date 2019/1/19 16:17
 * 买卖需求
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.RecycleHolder>{

    private Context context;

    private List<ListTradeResponseBean.TradeList> list;
    private TransactionListener listener;

    public TransactionAdapter(Context context, List<ListTradeResponseBean.TradeList> list,final TransactionListener listener) {
        this.listener = listener;
        this.context = context;
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
    }

    @NonNull
    @Override
    public TransactionAdapter.RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_transaction_item,parent,false);
        AutoUtils.auto(view);
        return new TransactionAdapter.RecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.RecycleHolder holder, final int position) {
        holder.tvTransId.setText("需求编号："+(TextUtils.isEmpty(list.get(position).getT_sn()) ? "未知" :list.get(position).getT_sn()));
        holder.tvTransTime.setText("创建时间："+(TextUtils.isEmpty(list.get(position).getAdd_time()) ? "未知" :list.get(position).getAdd_time()));
        holder.tvTransMin.setText("最小交易量："+(TextUtils.isEmpty(list.get(position).getPay_min()) ? "0.00T" :list.get(position).getPay_min()));
        holder.tvTransOrderState.setText("状态："+(TextUtils.isEmpty(list.get(position).getStatus_text()) ? "未知" :list.get(position).getStatus_text()));
        holder.tvTransState.setText(list.get(position).getAction_type_text());
        if(list.get(position).getAction_type_text().equals("出售")){
            holder.tvTransState.setTextColor(context.getResources().getColor(R.color.text_green));
        }else {
            holder.tvTransState.setTextColor(context.getResources().getColor(R.color.text_red_dialog));
        }

        if(list.get(position).getCan_del()==1){//1可下架 0 不能下架
            holder.btnTransLowerShelf.setVisibility(View.VISIBLE);
            holder.btnTransLowerShelf.setBackground(context.getResources().getDrawable(R.drawable.btn_yellow_bg_selector));
        }else if(list.get(position).getCan_del()==0){
            holder.btnTransLowerShelf.setVisibility(View.GONE);
        }

        holder.tvTransPrecent.setText(list.get(position).getTotal()+"T (已完成"+list.get(position).getDone()+")");
        holder.btnTransLowerShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onLowerShelfClick(String.valueOf(list.get(position).getId()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecycleHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_transaction_order_state)
        TextView tvTransOrderState;
        @BindView(R.id.tv_transaction_min)
        TextView tvTransMin;
        @BindView(R.id.tv_transaction_state)
        TextView tvTransState;
        @BindView(R.id.tv_complete_precent)
        TextView tvTransPrecent;
        @BindView(R.id.btn_lower_shelf)
        Button btnTransLowerShelf;
        @BindView(R.id.tv_transaction_time)
        TextView tvTransTime;
        @BindView(R.id.tv_transaction_id)
        TextView tvTransId;
        @BindView(R.id.line)
        View line;
        public RecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface TransactionListener{
        void onLowerShelfClick(String id);
    }
}
