package com.example.hwhan.coinmange;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.FieldPosition;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final List<CoinData> mDataList;


    public RecyclerAdapter(List<CoinData> dataList){
        mDataList = dataList;
    }
   
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_coin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CoinData item =mDataList.get(position);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.percent.setText(item.getPercent());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        TextView percent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.coinName);
            price = itemView.findViewById(R.id.coinPrice);
            percent =  itemView.findViewById(R.id.coinPercent);
        }
    }
}
