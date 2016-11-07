package com.rishabh.github.finclusionhack.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.models.AccountInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cypac on 26/10/16.
 */

public class AccountsListAdapter extends RecyclerView.Adapter<AccountsListAdapter.ViewHolder> {
    private List<AccountInfo> mAccounts;
    private AccountsListListener mAccountsListListener;

    public interface AccountsListListener {
        void onAccountSelected(AccountInfo accountInfo);
    }

    public AccountsListAdapter(AccountsListListener listener) {
        mAccountsListListener = listener;
        mAccounts = new ArrayList<>();
    }

    @Override
    public AccountsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_account_info, parent, false);
        return new AccountsListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AccountsListAdapter.ViewHolder holder, int position) {
        AccountInfo accountInfo = mAccounts.get(position);

        holder.nameTextView.setText(accountInfo.AadhaarName);
        holder.numberTextView.setText(accountInfo.AadhaarNo);
        holder.mAccountInfo = accountInfo;
    }

    @Override
    public int getItemCount() {
        return mAccounts == null ? 0 : mAccounts.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        TextView numberTextView;

        private AccountInfo mAccountInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textview);
            numberTextView = (TextView) itemView.findViewById(R.id.aadhaar_number_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mAccountsListListener != null)
                mAccountsListListener.onAccountSelected(mAccountInfo);
        }
    }

    public void showAccounts(List<AccountInfo> accounts) {
        if (accounts != null) {
            if (mAccounts != null)
                mAccounts.addAll(accounts);
            else
                mAccounts = accounts;
            this.notifyDataSetChanged();
        }
    }

    public void showAccount(AccountInfo accountInfo) {
        if (accountInfo != null) {
            if (mAccounts != null)
                mAccounts.add(accountInfo);
            else {
                mAccounts = new ArrayList<>();
                mAccounts.add(accountInfo);
            }
            this.notifyDataSetChanged();
        }
    }

    public void clearAccounts(){
        if (mAccounts != null)
            mAccounts.clear();
        else {
            mAccounts = new ArrayList<>();
        }
    }
}
