package com.rishabh.github.finclusionhack.home;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rishabh.github.finclusionhack.App;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.base.BaseBankingActivity;
import com.rishabh.github.finclusionhack.utils.AllowOnlyBomWhiteListedCharactersTextWatcher;
import com.rishabh.github.finclusionhack.utils.RxUtils;
import com.rishabh.github.finclusionhack.utils.SoftKeyboardUtils;

import org.bom.android.container.SDKUtils;
import org.bom.android.container.domain.enums.BankingPaymentType;
import org.bom.android.container.models.banking.Bank;
import org.bom.android.container.models.banking.BankAccount;
import org.bom.android.container.models.banking.BomResponse;
import org.bom.android.container.models.fundtransfer.FundTransferConfigs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class FundTransferActivity extends BaseBankingActivity {
    @BindView(R.id.content_scrollview)
    ScrollView mContentScrollView;
    @BindView(R.id.logs_textview)
    TextView mLogsTextView;
    @BindView(R.id.fundtransfer_button)
    Button mSubmitButton;
    @BindView(R.id.accounts_spinner)
    Spinner mSourceAccountsSpinner;
    @BindView(R.id.banks_spinner)
    Spinner mBanksSpinner;
    @BindView(R.id.amount_edittext)
    EditText mAmountEditText;
    @BindView(R.id.otp_edittext)
    EditText mOtpEditText;
    @BindView(R.id.recipient_account_number_edittext)
    EditText mRecipentAccountNumberEditText;
    @BindView(R.id.recipient_name_edittext)
    EditText mRecipientNameEditText;

    private static final String FIELD_NOTES_TO_SELF = "Sample notes to self";
    private static final String FIELD_NOTES_TO_RECIPIENT = "Sample notes to recipient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);
        ButterKnife.bind(this);

        //initializeToolbar("Fund Transfer", true);
        initializeFundTransfer();
        mRecipientNameEditText.addTextChangedListener(new AllowOnlyBomWhiteListedCharactersTextWatcher());
        mRecipentAccountNumberEditText.addTextChangedListener(new AllowOnlyBomWhiteListedCharactersTextWatcher());
    }

    private void initializeFundTransfer() {

        mSubmitButton.setEnabled(false);

        log("INFO: initializing Fund Transfer..");
        App.getClientContainer().getBankingClient().getFundTransferClient().initializeObservable()
                .compose(RxUtils.<FundTransferConfigs>applyDefaultSchedulers(this))
                .subscribe(new Action1<FundTransferConfigs>() {
                    @Override
                    public void call(FundTransferConfigs fundTransferConfigs) {
                        populateSourceAccounts();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        log("INFO: Fund Transfer initialization failed with error: %s", error.getLocalizedMessage());
                    }
                });
    }

    private void populateSourceAccounts() {
        App.getClientContainer().getBankingClient().getSourceAccounts(BankingPaymentType.FUND_TRANSFER)
                .compose(RxUtils.<List<BankAccount>>applyDefaultSchedulers(this))
                .subscribe(new Action1<List<BankAccount>>() {
                    @Override
                    public void call(List<BankAccount> bankAccounts) {
                        ArrayAdapter accountsAdapter = new ArrayAdapter<>(FundTransferActivity.this, R.layout.spinner_item, bankAccounts);
                        mSourceAccountsSpinner.setAdapter(accountsAdapter);
                        populateBanks();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        log("INFO: failed fetching bank accounts for Fund Transfer: %s", error.getLocalizedMessage());
                    }
                });
    }

    private void populateBanks() {
        App.getClientContainer().getBankingClient().getBanksObservable()
                .compose(RxUtils.<List<Bank>>applyDefaultSchedulers(this))
                .subscribe(new Action1<List<Bank>>() {
                    @Override
                    public void call(List<Bank> banks) {
                        ArrayAdapter bankAdapter = new ArrayAdapter<>(FundTransferActivity.this, R.layout.spinner_item, banks);
                        mBanksSpinner.setAdapter(bankAdapter);
                        mSubmitButton.setEnabled(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        log("INFO: failed fetching bank accounts for Fund Transfer: %s", error.getLocalizedMessage());
                    }
                });
    }

    @OnClick(R.id.fundtransfer_button)
    void initiateFundTransfer() {
        SoftKeyboardUtils.hideKeyboard(this);


        //clear logs
        mLogsTextView.setText("");

        String recipientAccountNumber = mRecipentAccountNumberEditText.getText().toString();
        String recipientName = mRecipientNameEditText.getText().toString();
        double amount = Double.parseDouble(mAmountEditText.getText().toString());
        Bank recipientBank = ((Bank) mBanksSpinner.getSelectedItem());

        //performing simple validations
        if (amount == 0 || TextUtils.isEmpty(recipientAccountNumber) || TextUtils.isEmpty(recipientName)) {
            log("ERROR: Invalid or incomplete fields");
            return;
        }

        mSubmitButton.setEnabled(false);

        BankAccount selectedSourceAccount = ((BankAccount) mSourceAccountsSpinner.getSelectedItem());



        String dialog=String.format("starting Fund Transfer with amount: %s, source account: %s, recipient account number: %s, " +
        "recipient name: %s, " +
                "recipient bank: %s, notes to self: %s, notes to recipient: %s \n\nperforming Fund Transfer..",String.valueOf(amount), selectedSourceAccount, SDKUtils.sanitizeText(recipientAccountNumber),
                recipientName, recipientBank,
                SDKUtils.sanitizeText(FIELD_NOTES_TO_SELF), SDKUtils.sanitizeText(FIELD_NOTES_TO_RECIPIENT));


//        log("INFO: starting Fund Transfer with amount: %s, source account: %s, recipient account number: %s, " +
//                        "recipient name: %s, " +
//                        "recipient bank: %s, notes to self: %s, notes to recipient: %s",
//                String.valueOf(amount), selectedSourceAccount, SDKUtils.sanitizeText(recipientAccountNumber),
//                recipientName, recipientBank,
//                SDKUtils.sanitizeText(FIELD_NOTES_TO_SELF), SDKUtils.sanitizeText(FIELD_NOTES_TO_RECIPIENT));

//        log("INFO: performing Fund Transfer..");

        App.getClientContainer().getBankingClient().getFundTransferClient()
                .fundTransferObservable(amount, selectedSourceAccount.AccountNumber, recipientAccountNumber, recipientName, recipientBank,
                        SDKUtils.sanitizeText(FIELD_NOTES_TO_SELF), SDKUtils.sanitizeText(FIELD_NOTES_TO_RECIPIENT))
                .compose(RxUtils.<BomResponse>applyDefaultSchedulers(this))
                .subscribe(new Action1<BomResponse>() {
                    public String m_Text;

                    @Override
                    public void call(BomResponse bomResponse) {
                        switch (bomResponse.Action) {
                            case SHOW_WAITING_FOR_SURECHECK_OR_OTP_SCREEN:
//                                log("ACTION: client app should SHOW UI or indicator that app is waiting for surecheck or OTP");
                                break;
                            case HIDE_WAITING_FOR_SURECHECK_SCREEN:
                                log("ACTION: client app should HIDE UI or indicator that app is waiting for surecheck or OTP");
                                break;
                            case REQUIRE_OTP:

                                log("ACTION: client app should display and prompt user to enter OTP");


                                //show UI for OTP entry
                                //and call confirmOTPObservable()

                                break;
                            case SUCCESS:
                                log("INFO: Fund Transfer is successful");


                                break;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        log("INFO: Fund Transfer failed with error: " + error.getLocalizedMessage());
                    }
                });
    }

    private void confirmFundTransferOTP(String OTP) {
        log("INFO: user enters otp: %s", OTP);
        log("INFO: verifying otp..");

        App.getClientContainer().getBankingClient().getFundTransferClient().confirmOTPObservable(OTP)
                .compose(RxUtils.<BomResponse>applyDefaultSchedulers(this))
                .subscribe(new Action1<BomResponse>() {
                    @Override
                    public void call(BomResponse bomResponse) {
                        switch (bomResponse.Action) {
                            case FAILED_OTP_SHOULD_RETRY:

                                new AlertDialog.Builder(FundTransferActivity.this)
                                         .setTitle("OTP")
                                        .setMessage("OTP Verification failed")
                                        .setNegativeButton("close",null)
                                        .setCancelable(false)
                                        .show();
                                log("INFO: OTP failed, prompt user to retry");
                                break;
                            case SUCCESS_OTP:

                                log("INFO: OTP verification is successful");
                                break;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        log("INFO: OTP failed with error: %s", error.getLocalizedMessage());
                    }
                });
    }

    /**
     * Utilities
     */
    private void log(String message, Object... args) {
        mLogsTextView.append(String.format(message, args));
        mLogsTextView.append("\n\n");

        mContentScrollView.scrollTo(0, mContentScrollView.getBottom());
    }
}