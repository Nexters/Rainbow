package com.nexters.rainbow.rainbowcouple.login;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.POST;

public interface LoginApi {

    @POST("/rainbow/login") String login(@Field("user_id") String userId, @Field("password") String password);

    @POST("/rainbow/join") String signUp(@Body SignUpForm signUpForm);

}