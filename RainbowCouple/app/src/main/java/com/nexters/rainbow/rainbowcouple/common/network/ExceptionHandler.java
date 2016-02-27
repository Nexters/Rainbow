package com.nexters.rainbow.rainbowcouple.common.network;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.nexters.rainbow.rainbowcouple.common.Messages;
import com.nexters.rainbow.rainbowcouple.common.utils.DebugLog;
import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;
import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;

import java.io.InputStreamReader;
import java.nio.charset.Charset;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExceptionHandler {

    private FragmentActivity activity;
    private Gson gson;

    public ExceptionHandler(FragmentActivity activity) {
        this.activity = activity;
        this.gson = new Gson();
    }

    public void handle(Throwable throwable) {
        if (throwable instanceof Exception) {
            Log.e("EXCEPTION", throwable.getMessage(), throwable);
            return;
        }

        showAlertDialog(getErrorMessage(throwable));
    }

    private void showAlertDialog(String message) {
        if (ObjectUtils.isEmpty(activity) || activity.isFinishing()) {
            return;
        }

        DialogManager.showAlertDialog(activity, "오류", message, null);
    }

    private String getErrorMessage(Throwable throwable) {
        if (throwable instanceof RetrofitError) {
            return getRetrofitError((RetrofitError) throwable);
        }

        return String.format(Messages.UNKNOWN_ERROR, throwable.getMessage());
    }

    private String getRetrofitError(RetrofitError error) {
        switch (error.getKind()) {
            case NETWORK:
                return String.format(Messages.NetworkError.NETWORK_ERROR, error.getMessage());

            case HTTP:
                Response response = error.getResponse();
                DebugLog.e(getRetrofitErrorMessage(response));
                return String.format(Messages.NetworkError.HTTP_SERVER_ERROR, getRetrofitErrorMessage(response));

            default:
                return String.format(Messages.NetworkError.UNKNOWN_NETWORK_ERROR, error.getMessage());
        }
    }

    private String getRetrofitErrorMessage(Response response) {
        try {
            InputStreamReader streamReader = new InputStreamReader(response.getBody().in(), Charset.forName("utf-8"));
            ErrorResponse errorResponse = gson.fromJson(streamReader, ErrorResponse.class);
            return errorResponse.getErrorMessage();
        } catch (Exception e) {
            return "";
        }
    }
}
