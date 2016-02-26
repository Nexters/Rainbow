package com.nexters.rainbow.rainbowcouple.bill.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.common.BaseDialogFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leegain on 2016-02-17.
 */
public class BillSelectCategoryDialog extends BaseDialogFragment {

    private Activity activity;
    private View view;
    private Button addCategoryBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_category_select, container,false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ButterKnife.bind(this, view);



        return view;
    }


    @OnClick(R.id.actionBtnAddCategory)
    public void onClickAddCategory(){
        BillAddCategoryDialog addCategoryDialog = new BillAddCategoryDialog();
        addCategoryDialog.show(getFragmentManager(), "CreateAddCategory");
    }

    @OnClick(R.id.btndrink)
    public void onClickSelectDrink() {
        Intent intent = new Intent(activity, BillAddActivity.class);
    }

    @OnClick(R.id.btneat)
    public void onClickSelectEat() {
        Intent intent = new Intent(activity, BillAddActivity.class);
    }

    @OnClick(R.id.btngame)
    public void onClickSelectgame() {
        Intent intent = new Intent(activity, BillAddActivity.class);
    }

    @OnClick(R.id.btnmovie)
    public void onClickSelectMovie() {
        Intent intent = new Intent(activity, BillAddActivity.class);
    }

    @OnClick(R.id.btnshoppig)
    public void onClickSelectSopping() {
        Intent intent = new Intent(activity, BillAddActivity.class);
    }

}
