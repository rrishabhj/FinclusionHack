package com.rishabh.github.finclusionhack.apiai;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rishabh.github.finclusionhack.R;

import java.util.List;

/**
 * Created by rishabh on 7/11/16.
 */

public class RecyclerVIewAdaptor extends RecyclerView.Adapter<RecyclerVIewAdaptor.MyViewHolder> {


    private final List<String> data;

    public RecyclerVIewAdaptor(List<String> data) {
        this.data=data;
    }

    @Override
    public RecyclerVIewAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.messages.setText(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView messages;
        public MyViewHolder(View itemView) {
            super(itemView);
            messages= (TextView) itemView.findViewById(R.id.tv_message);
        }
    }
}
