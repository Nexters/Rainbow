package com.nexters.rainbow.rainbowcouple.bill.add;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.bill.BillApi;
import com.nexters.rainbow.rainbowcouple.common.BaseDialogFragment;
import com.nexters.rainbow.rainbowcouple.common.Messages;
import com.nexters.rainbow.rainbowcouple.common.network.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;
import com.nexters.rainbow.rainbowcouple.common.utils.StringUtils;
import com.nexters.rainbow.rainbowcouple.common.utils.TimeUtils;
import com.nexters.rainbow.rainbowcouple.common.widget.AppCompatEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class BillAddDialog extends BaseDialogFragment {

    private static final String TAG_BILL_ADD_DIALOG = "bill_list_fragment";

    @Bind(R.id.editTextNewBillAmount) AppCompatEditText editTextBillAmount;
    @Bind(R.id.editTextNewBillCategory) AppCompatEditText editTextBillCategory;
    @Bind(R.id.editTextNewBillComment) AppCompatEditText editTextBillComment;

    private View rootView;

    private String SESSION_TOKEN = "3ynZKDkeVEloO79JnocmI0OUUjyzRWIuKZcLpYCtFID5p1Pdys-1-RDhFShhiBn_";
    private String SESSION_USER_NAME = "테스트_A";

    private AddDialogDismissCallback dismissCallback = null;

    public interface AddDialogDismissCallback {
        void notifySavedNewBill(Bill bill);
        void notifyError(Throwable throwable);
    }

    public static BillAddDialog newInstance() {
        BillAddDialog dialog = new BillAddDialog();
        dialog.setFragmentTag(TAG_BILL_ADD_DIALOG);
        return dialog;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btnBillSave)
    void onClickConfirmButton() {
        if (dismissCallback == null) {
            dismiss();
        }

        if (!isValidInput()) {
            return;
        }

        saveNewBill(BillAddForm.builder()
                .amount(Integer.parseInt(editTextBillAmount.getString()))
                .year(TimeUtils.getYearOfToday())
                .month(TimeUtils.getMonthOfToday())
                .day(TimeUtils.getDayOfToday())
                .category(editTextBillCategory.getString())
                .comment(editTextBillComment.getString())
                .build());
    }

    private Bill saveNewBill(BillAddForm form) {
        BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<Bill> billObservable = billApi.insertBill(SESSION_TOKEN, form);

        bind(billObservable)
                .subscribe(new Action1<Bill>() {
                    @Override
                    public void call(Bill bill) {
                        dismissCallback.notifySavedNewBill(bill);
                        dismiss();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dismissCallback.notifyError(throwable);
                        dismiss();
                    }
                });

        return Bill.builder()
                .year(TimeUtils.getYearOfToday())
                .month(TimeUtils.getMonthOfToday())
                .day(TimeUtils.getDayOfToday())
                .userSN(SESSION_TOKEN)
                .userName(SESSION_USER_NAME)
                .category(editTextBillCategory.getString())
                .amount(Integer.parseInt(editTextBillAmount.getString()))
                .comment(editTextBillComment.getString())
                .build();
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
        if (StringUtils.isEmpty(editTextBillComment.getString())) {
            DialogManager.showAlertDialog(getActivity(), Messages.BillError.BILL_COMMENT_EMPTY);
            return false;
        }

        if (StringUtils.isEmpty(editTextBillAmount.getString())) {
            DialogManager.showAlertDialog(getActivity(), Messages.BillError.BILL_AMOUNT_EMPTY);
            return false;
        }

        if (StringUtils.isEmpty(editTextBillCategory.getString())) {
            DialogManager.showAlertDialog(getActivity(), Messages.BillError.BILL_CATEGORY_EMPTY);
            return false;
        }

        return true;
    }
}
