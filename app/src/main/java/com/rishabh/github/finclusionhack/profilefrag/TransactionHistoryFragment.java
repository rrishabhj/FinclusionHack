package com.rishabh.github.finclusionhack.profilefrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.model.TransactionDetail;

import java.util.List;

/**
 * Created by rishabh on 6/11/16.
 */

public class TransactionHistoryFragment extends BaseFragment {

    RecyclerView recyclerView;
    List<TransactionDetail> transactionDetails;

    public static TransactionHistoryFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        TransactionHistoryFragment  fragment = new TransactionHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_transaction,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.rv_transaction);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // transaction adaptor
        //load transaction history
//        RecyclerTransactionAdaptor adaptor=new RecyclerTransactionAdaptor(transactionDetails);
//        recyclerView.setAdapter(adaptor);
        return view;
    }

}
