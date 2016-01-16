package com.nexters.rainbow.rainbowcouple.bill.add;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.nexters.rainbow.rainbowcouple.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillAddDialog extends DialogFragment {

    @Bind(R.id.textViewNewBillAmount) AppCompatEditText textViewBillAmount;
    @Bind(R.id.textViewNewBillComment) AppCompatEditText textViewBillComment;

    private View rootView;
    private DialogInterface.OnDismissListener dismissListener;

    public static BillAddDialog newInstance() {
        return new BillAddDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_bill_add, container, false);
        ButterKnife.bind(this, rootView);

        setCancelable(true);

        return rootView;
    }

// TODO: 2016. 1. 16. DB에 데이터 저장.. snackbar는 테스트 용 .
    @OnClick(R.id.btnBillSave)
    void onClickConfirmButton() {
        if (dismissListener != null) {
            dismissListener.onDismiss(getDialog());
        }
        dismiss();
    }

    @OnClick(R.id.btnBillCancel)
    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }
}
