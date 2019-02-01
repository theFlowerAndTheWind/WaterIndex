package com.jieshuizhibiao.waterindex.ui.adapter;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.beans.SystemMsgResponseBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songxiaotao on 2019/1/14.
 * Class Note:
 */
public class SystemMsgAdapter extends RecyclerView.Adapter<SystemMsgAdapter.ViewHolder>{

    private Context context;
    private List<SystemMsgResponseBean.SystemMsgList> list;
    private MessageClickListerer listerer;
    public SystemMsgAdapter(Context context, List<SystemMsgResponseBean.SystemMsgList> list,MessageClickListerer listerer) {
        this.context = context;
        this.listerer = listerer;
        if (list == null) {
            list = new ArrayList<>();
        } else {
            this.list = list;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_message_item, parent, false);
        AutoUtils.auto(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMessageTitle.setText(TextUtils.isEmpty(list.get(position).getTitle()) ? "标题" :list.get(position).getTitle());
        holder.tvMessageTime.setText(list.get(position).getAdd_time());
        holder.tvMessageContent.setText(list.get(position).getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listerer!=null){
                    listerer.onItemClick(list.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message_title)
        TextView tvMessageTitle;
        @BindView(R.id.message_time)
        TextView tvMessageTime;
        @BindView(R.id.message_coutent)
        TextView tvMessageContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MessageClickListerer{
        void onItemClick(int id);
    }
}
