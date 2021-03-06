package com.jieshuizhibiao.waterindex.ui.widget.twowaytable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;

/**
 * Created by songxiaotao on 2018/12/24.
 * Class Note:
 */

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.MyViewHolder> {
    private String[] titles;
    private Context context;

    public TopAdapter(String[] titles, Context context) {
        this.titles = titles;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.table_item_top, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.titleText.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
        }
    }
}

