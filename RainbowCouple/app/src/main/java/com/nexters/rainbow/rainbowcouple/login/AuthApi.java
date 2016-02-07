package com.nexters.rainbow.rainbowcouple.login;

import com.nexters.rainbow.rainbowcouple.common.Response;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface AuthApi {

    @FormUrlEncoded
    @POST("/rainbow/login")
    Observable<Response<UserDto>> login(@Field("user_id") String userId, @Field("password") String password);

    @POST("/rainbow/join") String signUp(@Body SignUpForm signUpForm);

}