package com.nexters.rainbow.rainbowcouple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nexters.rainbow.rainbowcouple.auth.SignInActivity;

/**
 * Created by soyoon on 2016. 2. 26..
 */
public class SplashActivity extends Activity {

    private static long INTRO_LOADING_TIME = 1000;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                endIntro();
            }
        }, INTRO_LOADING_TIME);
    }

    private void endIntro() {
        //boolean isBeforeLogin = pref.getValue(SrPreference.KEY_USER_EMAIL, null) != null;
        boolean isBeforeLogin = true;

        //TODO 자동 로그인 기능
        if (isBeforeLogin) {
            gotoSignIn();
        } else {
            gotoMain();
        }
    }


    private void gotoSignIn() {
        Intent startIntent = new Intent(this, SignInActivity.class);
        startActivity(startIntent);
        finish();
    }


    private void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
