package com.rishabh.github.finclusionhack.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.rishabh.github.finclusionhack.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


public abstract class BaseActivity extends RxAppCompatActivity {
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }

    protected void initializeToolbar(String title, boolean showBackButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);

            if (showBackButton && getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
    }

    /**
     * Toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Progress dialog
     */
    protected void showProgressDialog() {
        showProgressDialog("Loading..");
    }

    protected void showProgressDialog(String message) {
        mProgressDialog = ProgressDialog.show(this, "", message);
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    // Use this method to show all the errors within app lifecycle
    public void showError(Throwable error) {
        String errorMessage = error.getLocalizedMessage();
        errorMessage = TextUtils.isEmpty(errorMessage) ? "Unknown error" : errorMessage;
        new AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage(errorMessage)
                .setNegativeButton("Close", null)
                .show();
    }
}
