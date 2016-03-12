package com.nexters.rainbow.rainbowcouple.bill.add;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.bill.BillApi;
import com.nexters.rainbow.rainbowcouple.common.BaseActivity;
import com.nexters.rainbow.rainbowcouple.common.Messages;
import com.nexters.rainbow.rainbowcouple.common.network.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.network.SessionManager;
import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;
import com.nexters.rainbow.rainbowcouple.common.utils.StringUtils;
import com.nexters.rainbow.rainbowcouple.common.utils.TimeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class BillAddActivity extends BaseActivity {
    @Bind(R.id.tvNewBillDate)TextView tvBillDate;
    @Bind(R.id.rlCategory)RelativeLayout rlBilCategory;
    @Bind(R.id.ivCategoryIcon)ImageView ivBillCategoryIcon;
    @Bind(R.id.tvNewBillCategory) TextView tvBillCategory;
    @Bind(R.id.tvNewBillAmount) TextView tvBillAmount;
    @Bind(R.id.tvNewBillComment) TextView tvBillComment;
    @Bind(R.id.etNewBillAmount)EditText etBillAmount;
    @Bind(R.id.etNewBillComment) EditText etBillComment;
    @Bind(R.id.rlInputCategory) RelativeLayout rlInputCategory;
    @Bind(R.id.iconDrink) ImageButton iconDrink;
    @Bind(R.id.iconMeal) ImageButton iconMeal;
    @Bind(R.id.iconShopping) ImageButton iconShopping;
    @Bind(R.id.iconMovie) ImageButton iconMovie;
    @Bind(R.id.iconGame) ImageButton iconGame;
    @Bind(R.id.iconUserCategory) ImageButton iconUserCategory;
    @Bind(R.id.textDrink) TextView textDrink;
    @Bind(R.id.textMeal) TextView textMeal;
    @Bind(R.id.textShopping) TextView textShopping;
    @Bind(R.id.textMovie) TextView textMovie;
    @Bind(R.id.textGame) TextView textGame;
    @Bind(R.id.textUserCategory) TextView textUserCategory;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        ButterKnife.bind(this);
        sessionManager = SessionManager.getInstance(this);
        initDate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.rlCategory)
    void inputCategory() {
        rlBilCategory.setVisibility(View.GONE);
        tvBillAmount.setVisibility(View.VISIBLE);
        tvBillComment.setVisibility(View.VISIBLE);

        typeAmount();
        typeComment();

        tvBillAmount.setText(etBillAmount.getText().toString());

        tvBillAmount.setBackgroundResource(R.drawable.input_bg01_mid);
        tvBillComment.setBackgroundResource(R.drawable.input_bg01_bottom);

        etBillAmount.setVisibility(View.GONE);
        etBillComment.setVisibility(View.GONE);
        rlInputCategory.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvNewBillAmount)
    void inputAmount() {
        tvBillAmount.setVisibility(View.GONE);
        rlBilCategory.setVisibility(View.VISIBLE);
        tvBillComment.setVisibility(View.VISIBLE);

        typeComment();

        rlBilCategory.setBackgroundResource(R.drawable.input_bg01_mid);
        tvBillComment.setBackgroundResource(R.drawable.input_bg01_bottom);

        etBillAmount.setVisibility(View.VISIBLE);
        etBillComment.setVisibility(View.GONE);
        rlInputCategory.setVisibility(View.GONE);

    }

    private void typeComment() {
        String comment = etBillComment.getText().toString();
        if(comment.isEmpty()) {
            comment = "추가 설명을 입력하세요.";
        }
        tvBillComment.setText(comment);
    }

    private void typeAmount() {
        String amount = etBillAmount.getText().toString();
        if(amount.isEmpty()) {
            amount = "사용 금액을 입력하세요.";
        }
        tvBillAmount.setText(amount);
    }

    @OnClick(R.id.tvNewBillComment)
    void inputComment() {

        tvBillComment.setVisibility(View.GONE);
        tvBillAmount.setVisibility(View.VISIBLE);
        rlBilCategory.setVisibility(View.VISIBLE);

        typeAmount();

        rlBilCategory.setBackgroundResource(R.drawable.input_bg01_mid);
        tvBillAmount.setBackgroundResource(R.drawable.input_bg01_bottom);

        etBillAmount.setVisibility(View.GONE);
        etBillComment.setVisibility(View.VISIBLE);
        rlInputCategory.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnBillSave)
    void onClickConfirmButton() {

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

    private void initDate() {
        tvBillDate.setText(TimeUtils.getYearOfToday() + "/" + TimeUtils.getMonthOfToday() + "/" + TimeUtils.getDayOfToday());

    }

    private void saveNewBill(BillAddForm form) {

        BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<Bill> billObservable = billApi.insertBill(sessionManager.getUserToken(), form);

        bind(billObservable)
                .subscribe(new Action1<Bill>() {
                    @Override
                    public void call(Bill bill) {
//                        dismissCallback.notifySavedNewBill(bill);
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        dismissCallback.notifyError(throwable);
                        exitActivity();
                    }
                });
    }

    @OnClick(R.id.btnBillCancel)
    void exitActivity() {
        this.onBackPressed();
    }

    private boolean isValidInput() {
//        if (StringUtils.isEmpty(etBillComment.getText().toString())) {
//            DialogManager.showAlertDialog(getActivity(), Messages.BillError.BILL_COMMENT_EMPTY);
//            return false;
//        }

        if (StringUtils.isEmpty(etBillAmount.getText().toString())) {
            DialogManager.showAlertDialog(this, Messages.BillError.BILL_AMOUNT_EMPTY);
            return false;
        }

//        if (StringUtils.isEmpty(tvBillCategory.getText().toString())) {
//            DialogManager.showAlertDialog(getActivity(), Messages.BillError.BILL_CATEGORY_EMPTY);
//            return false;
//        }

        return true;
    }

    void resetCategory() {
        iconDrink.setBackgroundResource(R.drawable.input_ico_drink01);
        iconMeal.setBackgroundResource(R.drawable.input_ico_meal01);
        iconShopping.setBackgroundResource(R.drawable.input_ico_shopping01);
        iconMovie.setBackgroundResource(R.drawable.input_ico_movie01);
        iconGame.setBackgroundResource(R.drawable.input_ico_game01);

        textDrink.setTextColor(Color.parseColor("#000000"));
        textMeal.setTextColor(Color.parseColor("#000000"));
        textShopping.setTextColor(Color.parseColor("#000000"));
        textMovie.setTextColor(Color.parseColor("#000000"));
        textGame.setTextColor(Color.parseColor("#000000"));
    }

    @OnClick(R.id.iconDrink)
    void selectDrink() {
        resetCategory();
        iconDrink.setBackgroundResource(R.drawable.input_ico_drink01_press);
        textDrink.setTextColor(Color.parseColor("#FEBF02"));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_drink02);
        tvBillCategory.setText(R.string.string_drink);
    }


    @OnClick(R.id.iconMeal)
    void selectMeal() {
        resetCategory();
        iconMeal.setBackgroundResource(R.drawable.input_ico_meal01_press);
//        SpannableString spanString = new SpannableString("식사");
//        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
//        textMeal.setText(spanString);
        textMeal.setTextColor(Color.parseColor("#FF7D63"));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_meal02);
        tvBillCategory.setText(R.string.string_meal);
    }

    @OnClick(R.id.iconShopping)
    void selectShopping() {
        resetCategory();
        iconShopping.setBackgroundResource(R.drawable.input_ico_shopping01_press);
        textShopping.setTextColor(Color.parseColor("#50C086"));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_shopping02);
        tvBillCategory.setText(R.string.string_shopping);
    }

    @OnClick(R.id.iconMovie)
    void selectMovie() {
        resetCategory();
        iconMovie.setBackgroundResource(R.drawable.input_ico_movie01_press);
        textMovie.setTextColor(Color.parseColor("#1AC5FA"));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_movie02);
        tvBillCategory.setText(R.string.string_movie);
    }

    @OnClick(R.id.iconGame)
    void selectGame() {
        resetCategory();
        iconGame.setBackgroundResource(R.drawable.input_ico_game01_press);
        textGame.setTextColor(Color.parseColor("#B174B1"));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_game02);
        tvBillCategory.setText(R.string.string_game);
    }


}