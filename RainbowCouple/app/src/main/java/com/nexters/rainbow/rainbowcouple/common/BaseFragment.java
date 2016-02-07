package com.nexters.rainbow.rainbowcouple.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;

import rx.Observable;
import rx.functions.Action0;

/**
 * fragment tag 관리용으로 만든 BaseFragment
 */
public class BaseFragment extends Fragment {

    protected ProgressDialog progressDialog;

    private String tag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.progressDialog = DialogManager.makeProgressDialog(getActivity(), Messages.PROGRESS_LOADING_MESSAGE);
    }

    public String getFragmentTag() {
        return tag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.tag = fragmentTag;
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
