package com.nexters.rainbow.rainbowcouple.bill.add;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.bill.BillApi;
import com.nexters.rainbow.rainbowcouple.common.BaseDialogFragment;
import com.nexters.rainbow.rainbowcouple.common.Messages;
import com.nexters.rainbow.rainbowcouple.common.network.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.network.SessionManager;
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

    @Bind(R.id.tvNewBillDate) TextView tvBillDate;
    @Bind(R.id.rlCategory) RelativeLayout rlBilCategory;
    @Bind(R.id.ivCategoryIcon) ImageView ivBillCategoryIcon;
    @Bind(R.id.tvNewBillCategory) TextView tvBillCategory;
    @Bind(R.id.tvNewBillAmount) TextView tvBillAmount;
    @Bind(R.id.tvNewBillComment) TextView tvBillComment;
    @Bind(R.id.etNewBillAmount) EditText etBillAmount;
    @Bind(R.id.etNewBillComment) EditText etBillComment;

    private SessionManager sessionManager;

    private View rootView;

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
        sessionManager = SessionManager.getInstance(getActivity());

        return rootView;
    }

    private void initDialog() {
        tvBillDate.setText(TimeUtils.getYearOfToday() + "/" + TimeUtils.getMonthOfToday() + "/" + TimeUtils.getDayOfToday());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.rlCategory)
    void inputCategory() {
        tvBillComment.setVisibility(View.VISIBLE);
        tvBillAmount.setVisibility(View.VISIBLE);
        rlBilCategory.setVisibility(View.GONE);
    }

    @OnClick(R.id.tvNewBillAmount)
    void inputAmount() {
        tvBillAmount.setVisibility(View.GONE);
        tvBillComment.setVisibility(View.VISIBLE);
        rlBilCategory.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.tvNewBillComment)
    void inputComment() {
        tvBillComment.setVisibility(View.GONE);
        tvBillAmount.setVisibility(View.VISIBLE);
        rlBilCategory.setVisibility(View.VISIBLE);
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
                .amount(Integer.parseInt(etBillAmount.getText().toString()))
                .year(TimeUtils.getYearOfToday())
                .month(TimeUtils.getMonthOfToday())
                .day(TimeUtils.getDayOfToday())
                .category(tvBillCategory.getText().toString())
                .comment(etBillComment.getText().toString())
                .build());
    }

    private void saveNewBill(BillAddForm form) {
        BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<Bill> billObservable = billApi.insertBill(sessionManager.getUserToken(), form);

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
//        if (StringUtils.isEmpty(etBillComment.getText().toString())) {
//            DialogManager.showAlertDialog(getActivity(), Messages.BillError.BILL_COMMENT_EMPTY);
//            return false;
//        }

        if (StringUtils.isEmpty(etBillAmount.getText().toString())) {
            DialogManager.showAlertDialog(getActivity(), Messages.BillError.BILL_AMOUNT_EMPTY);
            return false;
        }

//        if (StringUtils.isEmpty(tvBillCategory.getText().toString())) {
//            DialogManager.showAlertDialog(getActivity(), Messages.BillError.BILL_CATEGORY_EMPTY);
//            return false;
//        }

        return true;
    }
}
