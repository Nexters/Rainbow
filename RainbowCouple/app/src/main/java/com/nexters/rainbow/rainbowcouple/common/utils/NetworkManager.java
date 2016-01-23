package com.nexters.rainbow.rainbowcouple.common.utils;

import com.nexters.rainbow.rainbowcouple.common.Constants;

import retrofit.Profiler;
import retrofit.RestAdapter;

public class NetworkManager {

    private static NetworkManager manager = null;

    private RestAdapter restAdapter;

    public static NetworkManager getInstance() {
        if (ObjectUtils.isEmpty(manager)) {
            manager = new NetworkManager();
        }

        return manager;
    }

    private NetworkManager() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setProfiler(new Profiler() {
                    @Override
                    public Object beforeCall() {
                        return null;
                    }

                    @Override
                    public void afterCall(RequestInformation requestInfo, long elapsedTime, int statusCode, Object beforeCallData) {
                        DebugLog.d("url : " + requestInfo.getBaseUrl() + requestInfo.getRelativePath()
                                + "\nmethod : " + requestInfo.getMethod()
                                + "\ncontentType : " + requestInfo.getContentType()
                                + "\ncontentLength : " + requestInfo.getContentLength()
                                + "\nelapsedTime : " + elapsedTime
                                + "\nstatusCode : " + statusCode
                                + "\nbeforeCallData : " + beforeCallData);
                    }
                })
                .build();
    }

    public RestAdapter getAdapter() {
        return restAdapter;
    }

    public static <T> T getApi(Class<T> api) {
        return getInstance().getAdapter().create(api);
    }
}