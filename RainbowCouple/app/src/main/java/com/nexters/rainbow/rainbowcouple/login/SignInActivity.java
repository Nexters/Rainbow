package com.nexters.rainbow.rainbowcouple.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.nexters.rainbow.rainbowcouple.MainActivity;
import com.nexters.rainbow.rainbowcouple.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignIn)
    public void startSignInActivity() {
        Intent mainActivity = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    @OnClick(R.id.btnSignUp)
    public void startSignUpActivity() {
        Intent signUpActivity = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(signUpActivity);
    }
}
