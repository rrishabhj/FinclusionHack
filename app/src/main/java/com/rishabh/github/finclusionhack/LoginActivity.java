package com.rishabh.github.finclusionhack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rishabh.github.finclusionhack.base.BaseActivity;
import com.rishabh.github.finclusionhack.fragment.BottomTabsActivity;
import com.rishabh.github.finclusionhack.utils.RxUtils;

import org.bom.android.container.models.BOM.BOMUser;
import org.bom.android.container.models.banking.BankingUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.account_number_textview)
    TextView mAccountNumberTextView;
    @BindView(R.id.pin_textview)
    TextView mPinTextView;
    @BindView(R.id.login_button)
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginButton.setEnabled(false);

        initializeToolbar("DEMO App", false);
        loadOrRegisterBOMUserIfNotExists();
    }

    @OnClick(R.id.login_button)
    void loginButtonClicked() {
        String accountNumber = mAccountNumberTextView.getText().toString();
        String pin = mPinTextView.getText().toString();

        if (TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(pin)) {
            Toast.makeText(this, "Account number and pin is required", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog("Logging in");

        App.getClientContainer().getBankingClient().loginObservable(accountNumber, pin)
                .compose(RxUtils.<BankingUser>applyDefaultSchedulers(this))
                .subscribe(new Action1<BankingUser>() {
                    @Override
                    public void call(BankingUser bankingUser) {
                        hideProgressDialog();

                        Intent intentToLaunch = new Intent(LoginActivity.this, BottomTabsActivity.class);
                        LoginActivity.this.startActivity(intentToLaunch);
                        LoginActivity.this.finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        hideProgressDialog();
                        LoginActivity.this.showError(error);
                    }
                });
    }

    private void loadOrRegisterBOMUserIfNotExists() {
        final SharedPreferences settings = this.getSharedPreferences("BOM_AUTH", 0);
        boolean hasRegisteredBOMUser = settings.contains("BOM_USERID");

        if (hasRegisteredBOMUser) {
            hideProgressDialog();

            String bomUserId = settings.getString("BOM_USERID", null);
            String bomInstallationId = settings.getString("BOM_INSTALLATIONID", null);
            App.getClientContainer().initializeBOMUser(bomUserId, bomInstallationId);
            mLoginButton.setEnabled(true);
        } else {
            /*  Need to call the registerBOMUserObservable() method on the getBOMClient() object to successfully create a BOM user
             */
            showProgressDialog("Registering BOM User");
            App.getClientContainer().getBOMClient().registerBOMUserObservable()
                    .compose(RxUtils.<BOMUser>applyDefaultSchedulers(this))
                    .subscribe(new Action1<BOMUser>() {
                        @Override
                        public void call(BOMUser bomUser) {
                            hideProgressDialog();

                            App.getClientContainer().initializeBOMUser(bomUser.getUserId(), bomUser.getInstallationId());

                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("BOM_USERID", bomUser.getUserId());
                            editor.putString("BOM_INSTALLATIONID", bomUser.getInstallationId());
                            editor.apply();

                            mLoginButton.setEnabled(true);
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
}