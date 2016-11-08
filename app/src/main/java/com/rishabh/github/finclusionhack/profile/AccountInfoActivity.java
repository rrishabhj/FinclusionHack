package com.rishabh.github.finclusionhack.profile;

import android.os.Bundle;
import android.widget.TextView;

import com.rishabh.github.finclusionhack.App;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.base.BaseBankingActivity;
import com.rishabh.github.finclusionhack.utils.RxUtils;

import org.bom.android.container.models.banking.BankAccount;

import butterknife.ButterKnife;
import rx.functions.Action1;

public class AccountInfoActivity extends BaseBankingActivity {
    TextView accname,accNum,accBal,acctype,accBaoughtForwardBal,accCurrenForBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        accname= (TextView) findViewById(R.id.tv_name);
        accNum= (TextView) findViewById(R.id.tv_accNum);
        accBal= (TextView) findViewById(R.id.tv_bal);
        acctype= (TextView) findViewById(R.id.tv_acctype);
        accBaoughtForwardBal= (TextView) findViewById(R.id.tv_boughtforbal);
        accCurrenForBal= (TextView) findViewById(R.id.tv_accCurrBal);
        ButterKnife.bind(this);

        initializeToolbar("Account Info", true);

        fetchAccountInfo();
    }

    private void fetchAccountInfo() {
        showProgressDialog();

        App.getClientContainer().getBankingClient().getAccountInfoObservable()
                .compose(RxUtils.<BankAccount>applyDefaultSchedulers(this))
                .subscribe(new Action1<BankAccount>() {
                    @Override
                    public void call(BankAccount bankAccount) {
                        hideProgressDialog();
//                        mAccountInfoTextView.setText(App.getSharedGson().toJson(bankAccount));
                        accname.setText(""+bankAccount.AccountName);
                        accNum.setText(""+bankAccount.AccountNumber);
                        accBal.setText(""+bankAccount.AvailableBalance);
                        acctype.setText(""+bankAccount.AccountType);
                        accBaoughtForwardBal.setText(""+bankAccount.BroughtForwardBalance);
                        accCurrenForBal.setText(""+bankAccount.CurrentBalance);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        hideProgressDialog();
                        showError(error);
                    }
                });
    }
}
