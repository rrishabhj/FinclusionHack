package com.rishabh.github.finclusionhack.profile;

import android.content.Intent;
import android.os.Bundle;

import com.rishabh.github.finclusionhack.App;
import com.rishabh.github.finclusionhack.LoginActivity;
import com.rishabh.github.finclusionhack.base.BaseBankingActivity;
import com.rishabh.github.finclusionhack.utils.RxUtils;

import org.bom.android.container.models.banking.BankingUser;

import rx.functions.Action1;

/**
 * Created by rishabh on 6/11/16.
 */

public class SignOut extends BaseBankingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgressDialog();
        App.getClientContainer().getBankingClient().logoutObservable()
                .compose(RxUtils.<BankingUser>applyDefaultSchedulers(this))
                .subscribe(new Action1<BankingUser>() {
                    @Override
                    public void call(BankingUser bankingUser) {
                        hideProgressDialog();
                        Intent intentToLaunch = new Intent(SignOut.this, LoginActivity.class);
                        startActivity(intentToLaunch);
                        finish();
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
