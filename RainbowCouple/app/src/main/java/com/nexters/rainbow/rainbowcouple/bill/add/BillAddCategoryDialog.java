package com.nexters.rainbow.rainbowcouple.bill.add;

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
public class BillAddCategoryDialog extends BaseDialogFragment {

    private View view;
    private Button btnCreate;
    private Button btnCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_category_add, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        btnCreate = (Button) view.findViewById(R.id.btnCategoryCreate);
        btnCancel = (Button)view.findViewById(R.id.btnCategoryCancel);



        return view;
    }
}
