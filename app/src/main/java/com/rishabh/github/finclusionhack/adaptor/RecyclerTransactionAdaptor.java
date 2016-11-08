package com.rishabh.github.finclusionhack.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rishabh.github.finclusionhack.R;

import org.bom.android.container.models.banking.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by rishabh on 6/11/16.
 */

public class RecyclerTransactionAdaptor extends RecyclerView.Adapter<RecyclerTransactionAdaptor.ViewHolder> {

    private List<Transaction> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mAmount,mBalance,mDate,mDescription;
        public ViewHolder(View v) {
            super(v);
            mAmount= (TextView) v.findViewById(R.id.tv_amount);
            mBalance= (TextView) v.findViewById(R.id.tv_balance);
            mDate= (TextView) v.findViewById(R.id.tv_date);
            mDescription= (TextView) v.findViewById(R.id.tv_description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerTransactionAdaptor(List<Transaction> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerTransactionAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_item_transaction, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        double amount= mDataset.get(position).Amount;
        String am=String.format("%.2f",amount);

        holder.mAmount.setText(""+am);

        double balance= mDataset.get(position).Balance;
        String bal=String.format("%.2f",balance);

            holder.mBalance.setText(""+bal);


        String description=mDataset.get(position).Description;
        if(!description.isEmpty()){
            holder.mDescription.setText(""+description);
        }


        Date date= mDataset.get(position).Date;
        if(date!=null) {
            holder.mAmount.setText(""+date.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
