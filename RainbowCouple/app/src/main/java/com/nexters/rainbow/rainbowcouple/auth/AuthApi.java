package com.nexters.rainbow.rainbowcouple.auth;

import com.nexters.rainbow.rainbowcouple.auth.signup.SignUpForm;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

public interface AuthApi {

    @POST("/rainbow/login")
    @FormUrlEncoded
    Observable<UserDto> login(@Field("user_id") String userId, @Field("password") String password);

    @POST("/rainbow/join")
    Observable<UserDto> signUp(@Body SignUpForm signUpForm);

    @POST("/rainbow/join_group")
    @FormUrlEncoded
    Observable<UserDto> joinGroup(@Header("token") String token,
                                  @Field("invite_code") String inviteCode);
}