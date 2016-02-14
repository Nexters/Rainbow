package com.nexters.rainbow.rainbowcouple.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class DebugLog {

    public static boolean DEBUG = true;

    private static final String TAG = "rainbow";

    /**
     * Log Level Error
     **/
    public static void e(String message) {
        if (DEBUG) Log.e(TAG, buildLogMessage(message));
    }

    /**
     * Log Level Warning
     **/
    public static void w(String message) {
        if (DEBUG) Log.w(TAG, buildLogMessage(message));
    }

    /**
     * Log Level Information
     **/
    public static void i(String message) {
        if (DEBUG) Log.i(TAG, buildLogMessage(message));
    }

    /**
     * Log Level Debug
     **/
    public static void d(String message) {
        if (DEBUG) Log.d(TAG, buildLogMessage(message));
    }

    /**
     * Log Level Verbose
     **/
    public static void v(String message) {
        if (DEBUG) Log.v(TAG, buildLogMessage(message));
    }

    public static String buildLogMessage(String message) {
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[4];
        return new StringBuffer().append("[")
                .append(traceElement.getFileName().replace(".java", ""))
                .append("::")
                .append(traceElement.getMethodName())
                .append("]")
                .append(message).toString();
    }

    public static boolean isDebugMode(Context context) {
        boolean debuggable;
        PackageManager packageManager = context.getPackageManager();

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            debuggable = ((applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        } catch (PackageManager.NameNotFoundException e) {
            debuggable = false;
        }

        return debuggable;
    }
}
