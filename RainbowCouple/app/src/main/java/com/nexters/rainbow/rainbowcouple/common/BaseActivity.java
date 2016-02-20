package com.nexters.rainbow.rainbowcouple.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

public class BaseActivity extends FragmentActivity {

    protected ProgressDialog progressDialog;

    private boolean isFirstBackPressed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isFirstBackPressed = true;
        this.progressDialog = DialogManager.makeProgressDialog(this, Messages.PROGRESS_LOADING_MESSAGE);
    }

    protected <T> Observable<T> bind(Observable<T> observable) {
        return observable
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        progressDialog.show();
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (isFirstBackPressed) {
            super.onBackPressed();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirstBackPressed = false;
            }
        }, 2000);
    }
}
