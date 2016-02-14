package com.nexters.rainbow.rainbowcouple.common.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;

import static android.content.DialogInterface.OnClickListener;

public class DialogManager {

    private static final String MESSAGE_OK = "확인";

    private AlertConfirmCallback alertConfirmCallback = null;
    public interface AlertConfirmCallback {
        void confirm();
        void cancel();
    }

    public static void showAlertDialog(Context context, String message) {
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(MESSAGE_OK, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.show();
    }

    public static void showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(MESSAGE_OK, listener);
        dialogBuilder.show();
    }

    public static void showConfirmDialog(Context context, String message,
                                         String confirmMessage, OnClickListener confirmListener,
                                         String cancelMessage, OnClickListener cancelListener) {
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(confirmMessage, confirmListener);
        dialogBuilder.setNegativeButton(cancelMessage, cancelListener);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public static ProgressDialog makeProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        return progressDialog;
    }
}
