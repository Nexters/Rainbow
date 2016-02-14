package com.nexters.rainbow.rainbowcouple.auth.signup;

import android.os.Bundle;
import android.view.Window;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.common.BaseActivity;

public class SignUpManageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        SignUpFragment signUpFragment = SignUpFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.signUpContentPanel, signUpFragment, signUpFragment.getFragmentTag())
                .commitAllowingStateLoss();
    }

}
