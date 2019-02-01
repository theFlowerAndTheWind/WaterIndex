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
import com.jieshuizhibiao.waterindex.beans.TradeIndex;
import com.jieshuizhibiao.waterindex.utils.image.GlidImageManager;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TradeIndexAdapter extends RecyclerView.Adapter<TradeIndexAdapter.TradeIndexHolder> {

    private Context context;
    private List<TradeIndex> list;
    private String type;//2购买节水 1出售节水
    private OnUserOrderListener listener;

    public TradeIndexAdapter(Context context,List<TradeIndex> list,String type,OnUserOrderListener listener) {
        this.context = context;
        if (list == null) {
            list = new ArrayList<>();
        } else {
            this.list = list;
        }
        this.type=type;
        this.listener=listener;
    }

    @NonNull
    @Override
    public TradeIndexHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_recycle_item_trade_index, parent, false);
        AutoUtils.auto(view);

        return new TradeIndexHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeIndexHolder holder, int position) {
        final TradeIndex tradeIndex = list.get(position);
        if(tradeIndex!=null){
            if(type.equals("1")){
                holder.tvPayMin1.setText("最小出售量：");
                holder.tvDoTrade.setText("出售");
            }else if(type.equals("2")){
                holder.tvPayMin1.setText("最小购买量：");
                holder.tvDoTrade.setText("去购买");
            }
            GlidImageManager.getInstance().loadCircleImg(context,tradeIndex.getAvatar(),holder.imgAvatar,R.mipmap.head,R.mipmap.head);
            holder.tvUserNickname.setText(tradeIndex.getUser_nickname());
            holder.tvSold.setText(tradeIndex.getSold());
            holder.tvSoldRate.setText(tradeIndex.getSold_rate());
            holder.tvTotal.setText(tradeIndex.getTotal());
            holder.tvPayMin.setText(tradeIndex.getPay_min());
            if(tradeIndex.getPay_type_bank_card().equals("1")){
                holder.imgPayTypeBankCard.setVisibility(View.VISIBLE);
            }else if(tradeIndex.getPay_type_bank_card().equals("0")){
                holder.imgPayTypeBankCard.setVisibility(View.GONE);
            }
            if(tradeIndex.getPay_type_alipay().equals("1")){
                holder.imgPayTypeAlipay.setVisibility(View.VISIBLE);
            }else if(tradeIndex.getPay_type_bank_card().equals("0")){
                holder.imgPayTypeAlipay.setVisibility(View.GONE);
            }
            if (tradeIndex.getPay_type_wechat().equals("1")) {
                holder.imgPayTypeWechat.setVisibility(View.VISIBLE);
            } else if (tradeIndex.getPay_type_bank_card().equals("0")) {
                holder.imgPayTypeWechat.setVisibility(View.GONE);
            }
            holder.llGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCreateOrder(tradeIndex);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TradeIndexHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_user_nickname)
        TextView tvUserNickname;
        @BindView(R.id.tv_sold_rate)
        TextView tvSoldRate;
        @BindView(R.id.tv_sold_rate_)
        TextView tvSoldRate_;
        @BindView(R.id.vertical_divider)
        View verticalDivider;
        @BindView(R.id.tv_sold)
        TextView tvSold;
        @BindView(R.id.tv_total_)
        TextView tvTotal1;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.img_pay_type_wechat)
        ImageView imgPayTypeWechat;
        @BindView(R.id.img_pay_type_alipay)
        ImageView imgPayTypeAlipay;
        @BindView(R.id.img_pay_type_bank_card)
        ImageView imgPayTypeBankCard;
        @BindView(R.id.tv_pay_min_)
        TextView tvPayMin1;
        @BindView(R.id.tv_pay_min)
        TextView tvPayMin;
        @BindView(R.id.ll_go)
        LinearLayout llGo;
        @BindView(R.id.tv_do_trade)
        TextView tvDoTrade;
        public TradeIndexHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnUserOrderListener{
        void onCreateOrder(TradeIndex tradeIndex);
    }
}
