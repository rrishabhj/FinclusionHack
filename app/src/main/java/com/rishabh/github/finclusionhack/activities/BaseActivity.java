package com.rishabh.github.finclusionhack.activities;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.rishabh.github.finclusionhack.R;


abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

//    protected void initializeToolbar(String title, boolean showBackButton) {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView textView = (TextView) toolbar.findViewById(R.id.title_textview);
//        LinearLayout toolBarView = (LinearLayout) toolbar.findViewById(R.id.toolbar_layout);
//
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//
//            toolBarView.setGravity(View.FOCUS_LEFT);
//
//            if (textView != null)
//                textView.setText(title);
//
//            if (showBackButton && getSupportActionBar() != null) {
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                getSupportActionBar().setDisplayShowHomeEnabled(true);
//                getSupportActionBar().setDisplayShowCustomEnabled(true);
//                getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//                toolBarView.setGravity(View.TEXT_ALIGNMENT_CENTER);
//            }
//        }
//


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

    protected void showMessage(String message) {
        new AlertDialog.Builder(this, R.style.HackathonAppTheme_Dialog_Alert)
                .setTitle(getString(R.string.system_message_header))
                .setMessage(message)
                .setPositiveButton(getString(R.string.BTN_CLOSE).toUpperCase(), null)
                .create()
                .show();
    }
}
