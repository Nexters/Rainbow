package com.nexters.rainbow.rainbowcouple.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.nexters.rainbow.rainbowcouple.MainActivity;
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

public class SignInActivity extends BaseActivity {

    @Bind(R.id.editTextUserId)
    AppCompatEditText editTextUserId;

    @Bind(R.id.editTextUserPassword)
    AppCompatEditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignIn)
    public void signIn() {
        if (hasEmptyField()) {
            return;
        }

        String userId = editTextUserId.getString();
        String password = editTextPassword.getString();

        AuthApi authApi = NetworkManager.getApi(AuthApi.class);
        Observable<Response<UserDto>> authObservable = authApi.login(userId, password);

        bind(authObservable).subscribe(new Action1<Response<UserDto>>() {
            @Override
            public void call(Response<UserDto> response) {
                DebugLog.d(response.getResult().toString());
                processLogin(response);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogManager.showAlertDialog(getApplicationContext(), throwable.getMessage());
                DebugLog.e(throwable.getMessage());
            }
        });
    }

    private void processLogin(Response<UserDto> response) {
        String message;
        switch (response.getErrorCode()) {
            case 0:
                //TODO : session에 유저 정보 넣기 ?
                Intent mainActivity = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(mainActivity);
                return;
            case 2:
                message = Messages.LoginError.INVALID_SESSION;
                break;
            case 6:
                message = Messages.LoginError.INVALID_ID_PASSWORD;
                break;
            default:
                message = Messages.BAD_REQUEST;
                break;
        }
        DialogManager.showAlertDialog(this, message);
    }

    private boolean hasEmptyField() {
        if (StringUtils.isEmpty(editTextUserId.getString())) {
            DialogManager.showAlertDialog(this, Messages.LoginError.EMPTY_LOGIN_ID);
            return true;
        }

        if (StringUtils.isEmpty(editTextPassword.getString())) {
            DialogManager.showAlertDialog(this, Messages.LoginError.EMPTY_LOGIN_PASSWORD);
            return true;
        }
        return false;
    }

    @OnClick(R.id.btnSignUp)
    public void startSignUpActivity() {
        Intent signUpActivity = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(signUpActivity);
    }
}
