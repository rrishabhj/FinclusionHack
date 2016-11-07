package com.rishabh.github.finclusionhack.apiai;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rishabh.github.finclusionhack.R;

/**
 * Created by rishabh on 7/11/16.
 */

public class MessageRecyclerView extends RecyclerView.Adapter<MessageRecyclerView.MyViewHolder> {


    public MessageRecyclerView(String message){


    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list_message_item, parent,
                false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
