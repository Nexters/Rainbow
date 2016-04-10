package com.nexters.rainbow.rainbowcouple.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

import com.nexters.rainbow.rainbowcouple.MainActivity;
import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.auth.signup.SignUpManageActivity;
import com.nexters.rainbow.rainbowcouple.common.BaseActivity;
import com.nexters.rainbow.rainbowcouple.common.Messages;
import com.nexters.rainbow.rainbowcouple.common.network.ExceptionHandler;
import com.nexters.rainbow.rainbowcouple.common.network.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.network.SessionManager;
import com.nexters.rainbow.rainbowcouple.common.utils.DebugLog;
import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;
import com.nexters.rainbow.rainbowcouple.common.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class SignInActivity extends BaseActivity {

    @Bind(R.id.editTextUserId) EditText editTextUserId;
    @Bind(R.id.editTextUserPassword) EditText editTextPassword;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in);

        DebugLog.DEBUG = DebugLog.isDebugMode(this);

        ButterKnife.bind(this);

        sessionManager = SessionManager.getInstance(this);
    }

    @OnClick(R.id.btnSignIn)
    public void signIn() {
        if (hasEmptyField()) {
            return;
        }

        String userId = editTextUserId.getText().toString();
        String password = editTextPassword.getText().toString();

        AuthApi authApi = NetworkManager.getApi(AuthApi.class);
        Observable<UserDto> authObservable = authApi.login(userId, password);
        bind(authObservable).subscribe(new Action1<UserDto>() {
            @Override
            public void call(UserDto userDto) {
                DebugLog.d(userDto.toString());
                processLogin(userDto);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                new ExceptionHandler(SignInActivity.this).handle(throwable);
            }
        });
    }

    private void processLogin(UserDto userDto) {
        sessionManager.createLoginSession(userDto);

        Intent mainActivity = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    private boolean hasEmptyField() {
        if (StringUtils.isEmpty(editTextUserId.getText().toString())) {
            DialogManager.showAlertDialog(this, Messages.EMPTY_LOGIN_ID);
            return true;
        }

        if (StringUtils.isEmpty(editTextPassword.getText().toString())) {
            DialogManager.showAlertDialog(this, Messages.EMPTY_LOGIN_PASSWORD);
            return true;
        }
        return false;
    }

    @OnClick(R.id.btnSignUp)
    public void startSignUpActivity() {
        Intent signUpActivity = new Intent(SignInActivity.this, SignUpManageActivity.class);
        signUpActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(signUpActivity);
    }
}
