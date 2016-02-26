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

        activity = getActivity();
        view = inflater.inflate(R.layout.dialog_category_select, container,false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        addCategoryBtn = (Button) view.findViewById(R.id.actionBtnAddCategory);

        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillAddCategoryDialog addCategoryDialog = new BillAddCategoryDialog();
                addCategoryDialog.show(getFragmentManager(),"CreateAddCategory");
            }
        });



        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BillAddActivity.class);
            }
        };

        return view;
    }
}
