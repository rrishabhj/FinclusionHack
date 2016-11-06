package com.rishabh.github.finclusionhack.home;

import android.os.Bundle;
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
import com.rishabh.github.finclusionhack.utils.RxUtils;
import com.rishabh.github.finclusionhack.utils.SoftKeyboardUtils;

import org.bom.android.container.SDKUtils;
import org.bom.android.container.data.api.clients.ImtClient;
import org.bom.android.container.domain.enums.BankingPaymentType;
import org.bom.android.container.models.banking.BankAccount;
import org.bom.android.container.models.banking.BomResponse;
import org.bom.android.container.models.cashsend.IMTConfigs;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ImtActivity extends BaseBankingActivity {
    @BindView(R.id.content_scrollview)
    ScrollView mContentScrollView;
    @BindView(R.id.logs_textview)
    TextView mLogsTextView;
    @BindView(R.id.amount_edittext)
    EditText mAmountEditText;
    @BindView(R.id.recipient_phone_number_edittext)
    EditText mRecipientPhoneNumberEditText;
    @BindView(R.id.accounts_spinner)
    Spinner mSourceAccountsSpinner;
    @BindView(R.id.pin_edittext)
    EditText mPinEditText;
    @BindView(R.id.otp_edittext)
    EditText mOtpEditText;
    @BindView(R.id.imt_button)
    Button mInitiateImtButton;
    @BindView(R.id.amount_instructions_textview)
    TextView mAmountInstructionsTextView;
    @BindView(R.id.recipient_name_edittext)
    EditText mRecipientNameEditText;

    private static final String FIELD_NOTES = "Test notes";

    private IMTConfigs mImtConfigs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imt);
        ButterKnife.bind(this);

        initializeToolbar("Instant Money Transfer", true);
        initializeIMT();
    }

    private void initializeIMT() {
        mInitiateImtButton.setEnabled(false);
        log("INFO: initializing IMT..");
        ImtClient imtClient = App.getClientContainer().getBankingClient().getImtClient();

        imtClient.initializeObservable()
                .compose(RxUtils.<IMTConfigs>applyDefaultSchedulers(this))
                .subscribe(new Action1<IMTConfigs>() {
                    @Override
                    public void call(IMTConfigs imtConfigs) {
                        mImtConfigs = imtConfigs;
                        mAmountInstructionsTextView.setText(String.format(Locale.US,
                                "IMT amount minimum: %.2f, maximum: %.2f, multiples: %.2f",
                                imtConfigs.ImtTransactionMin, imtConfigs.ImtTransactionMax,
                                imtConfigs.ImtAmountMultiples));
                        populateSourceAccounts();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        log("INFO: IMT initialization failed with error: %s", error.getLocalizedMessage());
                    }
                });
    }

    private void populateSourceAccounts() {
        App.getClientContainer().getBankingClient().getSourceAccounts(BankingPaymentType.IMT)
                .compose(RxUtils.<List<BankAccount>>applyDefaultSchedulers(this))
                .subscribe(new Action1<List<BankAccount>>() {
                    @Override
                    public void call(List<BankAccount> bankAccounts) {
                        ArrayAdapter accountsAdapter = new ArrayAdapter<>(ImtActivity.this, R.layout.spinner_item, bankAccounts);
                        mSourceAccountsSpinner.setAdapter(accountsAdapter);
                        mInitiateImtButton.setEnabled(true);

                        log("INFO: IMT ready");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        log("INFO: failed fetching bank accounts for IMT: %s", error.getLocalizedMessage());
                    }
                });
    }

    @OnClick(R.id.imt_button)
    public void IMTButtonClicked() {
        SoftKeyboardUtils.hideKeyboard(this);

        mLogsTextView.setText("");

        double amount = Double.parseDouble(mAmountEditText.getText().toString());
        String sourceAccountNumber = ((BankAccount) mSourceAccountsSpinner.getSelectedItem()).AccountNumber;
        String recipientPhoneNumber = mRecipientPhoneNumberEditText.getText().toString();
        String pin = mPinEditText.getText().toString();
        String recipientName = mRecipientNameEditText.getText().toString();

        if (amount < mImtConfigs.ImtTransactionMin || amount > mImtConfigs.ImtTransactionMax ||
                (amount % mImtConfigs.ImtAmountMultiples != 0)) {
            log("ERROR: invalid IMT amount, follow the instructions for the amount");
            return;
        }

        if (TextUtils.isEmpty(recipientName)) {
            log("ERROR: recipient name is required");
            return;
        }

        mInitiateImtButton.setEnabled(false);

        log("INFO: starting IMT with amount: %s, source account: %s, recipient phone number: %s, " +
                        "recipient full name: %s, IMT pin: %s, notes: %s",
                String.valueOf(amount), sourceAccountNumber, SDKUtils.sanitizeText(recipientPhoneNumber),
                SDKUtils.sanitizeText(recipientName), pin, SDKUtils.sanitizeText(FIELD_NOTES));

        log("INFO: performing IMT..");
        App.getClientContainer().getBankingClient().getImtClient()
                .imtObservable(amount, sourceAccountNumber, recipientPhoneNumber, recipientName, pin, FIELD_NOTES)
                .compose(RxUtils.<BomResponse>applyDefaultSchedulers(this))
                .subscribe(new Action1<BomResponse>() {
                    @Override
                    public void call(BomResponse bomResponse) {
                        switch (bomResponse.Action) {
                            case SHOW_WAITING_FOR_SURECHECK_OR_OTP_SCREEN:
                                log("ACTION: client app should SHOW UI or indicator that app is waiting for surecheck or OTP");
                                break;
                            case HIDE_WAITING_FOR_SURECHECK_SCREEN:
                                log("ACTION: client app should HIDE UI or indicator that app is waiting for surecheck or OTP");
                                break;
                            case REQUIRE_OTP:
                                log("ACTION: client app should display and prompt user to enter OTP");
                                //show UI for OTP entry
                                //and call confirmOTPObservable()
                                confirmImtOtp();
                                break;
                            case SUCCESS:
                                log("INFO: IMT is successful");
                                break;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        log("INFO: IMT failed with error: " + error.getLocalizedMessage());
                    }
                });
    }

    private void confirmImtOtp() {
        String OTP = mOtpEditText.getText().toString();
        log("INFO: user enters otp: %s", OTP);
        log("INFO: verifying otp..");
        App.getClientContainer().getBankingClient().getImtClient().confirmOTPObservable(OTP)
                .compose(RxUtils.<BomResponse>applyDefaultSchedulers(this))
                .subscribe(new Action1<BomResponse>() {
                    @Override
                    public void call(BomResponse bomResponse) {
                        switch (bomResponse.Action) {
                            case FAILED_OTP_SHOULD_RETRY:
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