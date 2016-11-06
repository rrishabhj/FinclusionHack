package com.rishabh.github.finclusionhack.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rishabh.github.finclusionhack.App;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.adaptor.RecyclerTransactionAdaptor;
import com.rishabh.github.finclusionhack.base.BaseBankingActivity;
import com.rishabh.github.finclusionhack.utils.RxUtils;

import org.bom.android.container.models.banking.Transaction;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by rishabh on 6/11/16.
 */

public class TransactionHistoryActivity extends BaseBankingActivity {

    RecyclerView recyclerView;
    List<Transaction> transaction_dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView= (RecyclerView) findViewById(R.id.rv_transaction);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // transaction adaptor
        //load transaction history
        fetchTransactionsHistory();

    }

    private void fetchTransactionsHistory() {
        showProgressDialog();

        App.getClientContainer().getBankingClient().getTransactionsHistoryObservable()
                .compose(RxUtils.<List<Transaction>>applyDefaultSchedulers(this))
                .subscribe(new Action1<List<Transaction>>() {
                    @Override
                    public void call(List<Transaction> transactions) {
                        hideProgressDialog();
                        setTransactions(transactions);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        hideProgressDialog();
                        showError(error);
                    }
                });
    }


    private void setTransactions(List<Transaction> transactions) {
       transaction_dataset=transactions;
        RecyclerTransactionAdaptor adaptor=new RecyclerTransactionAdaptor(transaction_dataset);
        recyclerView.setAdapter(adaptor);
    }



}
