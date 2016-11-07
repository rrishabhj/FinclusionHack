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
    //@BindView(R.id.accountinfo_textview)
    TextView mAccountInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
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
                        mAccountInfoTextView.setText(App.getSharedGson().toJson(bankAccount));

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
