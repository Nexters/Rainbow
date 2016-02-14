package com.nexters.rainbow.rainbowcouple.bill.add;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.common.utils.StringUtils;
import com.nexters.rainbow.rainbowcouple.common.utils.TimeUtils;
import com.nexters.rainbow.rainbowcouple.common.widget.AppCompatEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillAddDialog extends DialogFragment {

    @Bind(R.id.editTextNewBillAmount) AppCompatEditText editTextBillAmount;
    @Bind(R.id.editTextNewBillCategory) AppCompatEditText editTextBillCategory;
    @Bind(R.id.editTextNewBillComment) AppCompatEditText editTextBillComment;

    private View rootView;

    private AddDialogDismissCallback dismissCallback = null;

    public interface AddDialogDismissCallback {
        void saveNewBill(Bill bill);
    }

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

    @OnClick(R.id.btnBillSave)
    void onClickConfirmButton() {
        if (dismissCallback == null) {
            dismiss();
        }
        if (!isValidInput()) {
            return;
        }

        dismissCallback.saveNewBill(new Bill(2016, 2, 8, "3ynZKDkeVEloO79JnocmI0OUUjyzRWIuKZcLpYCtFID5p1Pdys-1-RDhFShhiBn_",
                "Soyoon", "Shopping", 26000, "TESTING"));

        dismiss();
    }

    @OnClick(R.id.btnBillCancel)
    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setDismissCallback(AddDialogDismissCallback dismissCallback) {
        this.dismissCallback = dismissCallback;
    }

    private boolean isValidInput() {
        // TODO: 2016. 1. 16. 내용 입력이 반드시 필요한가?
        if (StringUtils.isEmpty(editTextBillAmount.getString())
                || StringUtils.isEmpty(editTextBillComment.getString())) {
            // TODO: 2016. 1. 16. 입력 요청 이벤트 주고 return 할 것
            return false;
        }
        return true;
    }
}
