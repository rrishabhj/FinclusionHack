package com.rishabh.github.finclusionhack.base;

import android.content.Intent;
import android.widget.Toast;

import com.rishabh.github.finclusionhack.App;
import com.rishabh.github.finclusionhack.LoginActivity;

import org.bom.android.container.domain.enums.HeartbeatEvent;

import rx.functions.Action1;

public abstract class BaseBankingActivity extends BaseActivity {
    /* Created this Activity to handle two things that are *required on banking in general
    *   1. Timeout event of the banking session in order to close all the banking activities when a timeout event occurs
    *   2. KeepAlive event need to be triggered using keepAlive() method on the getHeartbeat() object to make sure the user is not being logged out when banking activities are being used
    */
    @Override
    protected void onStart() {
        super.onStart();

        /*  Need to listen to the events on getHeartbeat() object using the eventsObservable() method
        *   HeartbeatEvent.TIMEOUT - This will be returned to notice the user's banking session timeout event
        * */
        App.getClientContainer().getHeartbeat().eventsObservable()
                .compose(this.<HeartbeatEvent>bindToLifecycle())
                .subscribe(new Action1<HeartbeatEvent>() {
                    @Override
                    public void call(HeartbeatEvent heartbeatEvent) {
                        if (heartbeatEvent == HeartbeatEvent.TIMEOUT) {
                            Toast.makeText(BaseBankingActivity.this, "Banking session has timeout", Toast.LENGTH_LONG).show();
                            Intent intentToLaunch = new Intent(BaseBankingActivity.this, LoginActivity.class);
                            BaseBankingActivity.this.startActivity(intentToLaunch);
                            BaseBankingActivity.this.finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        showError(error);
                    }
                });
    }

    @Override
    public void onUserInteraction() {

        /*  Need to listen to the events on getHeartbeat() object using the eventsObservable() method
        *   HeartbeatEvent.TIMEOUT - This will be returned to notice the user's banking session timeout event
        * */
        if (App.getClientContainer() != null && App.getClientContainer().getHeartbeat() != null)
            App.getClientContainer().getHeartbeat().keepAlive();
    }
}
