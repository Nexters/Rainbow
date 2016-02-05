package com.nexters.rainbow.rainbowcouple.login;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.nexters.rainbow.rainbowcouple.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignUpApply)
    public void applySignUp() {
        finish();
    }
}
