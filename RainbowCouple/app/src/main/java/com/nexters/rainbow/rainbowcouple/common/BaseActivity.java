package com.nexters.rainbow.rainbowcouple.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;

import rx.Observable;
import rx.functions.Action0;

public class BaseActivity extends FragmentActivity {

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.progressDialog = DialogManager.makeProgressDialog(this, Messages.PROGRESS_LOADING_MESSAGE);
    }

    protected <T> Observable<T> bind(Observable<T> observable) {
        return observable
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
}
