package com.nexters.rainbow.rainbowcouple.bill.add;

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
import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;
import com.nexters.rainbow.rainbowcouple.common.utils.StringUtils;
import com.nexters.rainbow.rainbowcouple.common.utils.TimeUtils;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class BillAddActivity extends BaseActivity {

    @Bind(R.id.rlCategory) RelativeLayout rlBilCategory;
    @Bind(R.id.rlInputCategory) RelativeLayout rlInputCategory;
    @Bind(R.id.etNewBillAmount) EditText etBillAmount;
    @Bind(R.id.etNewBillComment) EditText etBillComment;
    @Bind(R.id.tvNewBillDate) TextView tvBillDate;
    @Bind(R.id.ivCategoryIcon) ImageView ivBillCategoryIcon;
    @Bind(R.id.tvNewBillCategory) TextView tvBillCategory;
    @Bind(R.id.tvNewBillAmount) TextView tvBillAmount;
    @Bind(R.id.tvNewBillComment) TextView tvBillComment;
    @Bind(R.id.tvDrink) TextView textDrink;
    @Bind(R.id.tvMeal) TextView textMeal;
    @Bind(R.id.tvShopping) TextView textShopping;
    @Bind(R.id.tvMovie) TextView textMovie;
    @Bind(R.id.tvGame) TextView textGame;
    @Bind(R.id.tvUserCategory) TextView textUserCategory;
    @Bind(R.id.iconDrink) ImageButton iconDrink;
    @Bind(R.id.iconMeal) ImageButton iconMeal;
    @Bind(R.id.iconShopping) ImageButton iconShopping;
    @Bind(R.id.iconMovie) ImageButton iconMovie;
    @Bind(R.id.iconGame) ImageButton iconGame;
    @Bind(R.id.iconUserCategory) ImageButton iconUserCategory;

    private SessionManager sessionManager;
    private Date viewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);

        ButterKnife.bind(this);

        sessionManager = SessionManager.getInstance(this);

        Long milliDate = getIntent().getLongExtra("date", 0L);
        initDate(milliDate);
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
        if (comment.isEmpty()) {
            comment = "추가 설명을 입력하세요.";
        }
        tvBillComment.setText(comment);
    }

    private void typeAmount() {
        String amount = etBillAmount.getText().toString();
        if (amount.isEmpty()) {
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
                .year(TimeUtils.getYearOfDate(viewDate))
                .month(TimeUtils.getMonthOfDate(viewDate))
                .day(TimeUtils.getDayOfDate(viewDate))
                .category(tvBillCategory.getText().toString())
                .comment(etBillComment.getText().toString())
                .build());
    }

    private void initDate(Long milliDate) {
        if (ObjectUtils.isEmpty(milliDate)) {
            viewDate = TimeUtils.getToday();
        } else {
            viewDate = TimeUtils.getDateFromMillis(milliDate);
        }
        tvBillDate.setText(TimeUtils.getDateToString(viewDate));
    }

    private void saveNewBill(BillAddForm form) {
        BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<Bill> billObservable = billApi.insertBill(sessionManager.getUserToken(), form);

        bind(billObservable)
                .subscribe(new Action1<Bill>() {
                    @Override
                    public void call(Bill bill) {
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        exitActivity();
                    }
                });
    }

    @OnClick(R.id.btnBillCancel)
    void exitActivity() {
        this.onBackPressed();
    }

    private boolean isValidInput() {

        if (StringUtils.isEmpty(etBillAmount.getText().toString())) {
            DialogManager.showAlertDialog(this, Messages.BILL_AMOUNT_EMPTY);
            return false;
        }

        return true;
    }

    void resetCategory() {
        iconDrink.setBackgroundResource(R.drawable.input_ico_drink01);
        iconMeal.setBackgroundResource(R.drawable.input_ico_meal01);
        iconShopping.setBackgroundResource(R.drawable.input_ico_shopping01);
        iconMovie.setBackgroundResource(R.drawable.input_ico_movie01);
        iconGame.setBackgroundResource(R.drawable.input_ico_game01);

        textDrink.setTextColor(getResources().getColor(R.color.color_black));
        textMeal.setTextColor(getResources().getColor(R.color.color_black));
        textShopping.setTextColor(getResources().getColor(R.color.color_black));
        textMovie.setTextColor(getResources().getColor(R.color.color_black));
        textGame.setTextColor(getResources().getColor(R.color.color_black));
    }

    @OnClick(R.id.iconDrink)
    void selectDrink() {
        resetCategory();
        iconDrink.setBackgroundResource(R.drawable.input_ico_drink01_press);
        textDrink.setTextColor(getResources().getColor(R.color.color_bill_category_drink));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_drink02);
        tvBillCategory.setText(R.string.string_drink);
    }


    @OnClick(R.id.iconMeal)
    void selectMeal() {
        resetCategory();
        iconMeal.setBackgroundResource(R.drawable.input_ico_meal01_press);
        textMeal.setTextColor(getResources().getColor(R.color.color_bill_category_meal));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_meal02);
        tvBillCategory.setText(R.string.string_meal);
    }

    @OnClick(R.id.iconShopping)
    void selectShopping() {
        resetCategory();
        iconShopping.setBackgroundResource(R.drawable.input_ico_shopping01_press);
        textShopping.setTextColor(getResources().getColor(R.color.color_bill_category_shopping));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_shopping02);
        tvBillCategory.setText(R.string.string_shopping);
    }

    @OnClick(R.id.iconMovie)
    void selectMovie() {
        resetCategory();
        iconMovie.setBackgroundResource(R.drawable.input_ico_movie01_press);
        textMovie.setTextColor(getResources().getColor(R.color.color_bill_category_movie));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_movie02);
        tvBillCategory.setText(R.string.string_movie);
    }

    @OnClick(R.id.iconGame)
    void selectGame() {
        resetCategory();
        iconGame.setBackgroundResource(R.drawable.input_ico_game01_press);
        textGame.setTextColor(getResources().getColor(R.color.color_bill_category_game));
        ivBillCategoryIcon.setImageResource(R.drawable.ico_game02);
        tvBillCategory.setText(R.string.string_game);
    }

}