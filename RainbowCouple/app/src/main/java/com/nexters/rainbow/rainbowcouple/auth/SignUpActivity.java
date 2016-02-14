package com.nexters.rainbow.rainbowcouple.auth;

import android.os.Bundle;
import android.view.Window;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.common.BaseActivity;
import com.nexters.rainbow.rainbowcouple.common.Messages;
import com.nexters.rainbow.rainbowcouple.common.Response;
import com.nexters.rainbow.rainbowcouple.common.utils.DebugLog;
import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;
import com.nexters.rainbow.rainbowcouple.common.utils.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.utils.StringUtils;
import com.nexters.rainbow.rainbowcouple.common.widget.AppCompatEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class SignUpActivity extends BaseActivity {

    @Bind(R.id.editTextSignUpUserId)
    AppCompatEditText editTextUserId;

    @Bind(R.id.editTextSignUpUserName)
    AppCompatEditText editTextUserName;

    @Bind(R.id.editTextSignUpUserPassword)
    AppCompatEditText editTextUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignUpApply)
    public void applySignUp() {

        if (hasEmptyField()) {
            return;
        }

        SignUpForm signUpForm = SignUpForm.builder()
                .userId(editTextUserId.getString())
                .userName(editTextUserName.getString())
                .password(editTextUserPassword.getString());

        processSignUp(signUpForm);
    }

    private boolean hasEmptyField() {
        if (StringUtils.isEmpty(editTextUserId.getString())) {
            DialogManager.showAlertDialog(this, Messages.SignUpError.EMPTY_USER_ID);
            return true;
        }

        if (StringUtils.isEmpty(editTextUserName.getString())) {
            DialogManager.showAlertDialog(this, Messages.SignUpError.EMPTY_USER_NAME);
            return true;
        }

        if (StringUtils.isEmpty(editTextUserPassword.getString())) {
            DialogManager.showAlertDialog(this, Messages.SignUpError.EMPTY_USER_PASSWORD);
            return true;
        }

        //TODO : 그 외 input validation은 서버에서 에러 받기
        return false;
    }

    private void processSignUp(SignUpForm signUpForm) {
        AuthApi authApi = NetworkManager.getApi(AuthApi.class);
        Observable<Response> authObservable = authApi.signUp(signUpForm);
        bind(authObservable).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {
                DebugLog.d(response.toString());
                finish();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogManager.showAlertDialog(getApplicationContext(), throwable.getMessage());
                DebugLog.e(throwable.getMessage());
            }
        });
    }
}
