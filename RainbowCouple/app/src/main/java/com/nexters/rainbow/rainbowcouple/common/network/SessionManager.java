package com.nexters.rainbow.rainbowcouple.common.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.nexters.rainbow.rainbowcouple.auth.SignInActivity;
import com.nexters.rainbow.rainbowcouple.auth.UserDto;
import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;

import java.util.HashMap;

public class SessionManager {
    public static final String KEY_SESSION_TOKEN = "session_token";

    public static final String KEY_SESSION_USER_ID = "session_userId";

    // Sharedpref file name
    private static final String PREF_NAME = "RainbowAppPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    private static SessionManager instance;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Context _context;

    private int PRIVATE_MODE = 0;

    public static SessionManager getInstance(Context context) {
        if (ObjectUtils.isEmpty(instance)) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    private SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(UserDto userDto) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_SESSION_TOKEN, userDto.getToken());
        editor.putString(KEY_SESSION_USER_ID, userDto.getUserId());
        editor.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserInfo() {
        HashMap<String, String> userSession = new HashMap<>();
        userSession.put(KEY_SESSION_TOKEN, pref.getString(KEY_SESSION_TOKEN, null));
        userSession.put(KEY_SESSION_USER_ID, pref.getString(KEY_SESSION_USER_ID, null));

        return userSession;
    }

    public String getUserToken() {
        return pref.getString(KEY_SESSION_TOKEN, "");
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent intent = new Intent(_context, SignInActivity.class);

        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(intent);
    }

    /**
     * Quick check for login
     **/
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}
