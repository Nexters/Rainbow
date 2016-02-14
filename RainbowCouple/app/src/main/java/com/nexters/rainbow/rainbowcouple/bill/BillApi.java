package com.nexters.rainbow.rainbowcouple.bill;

import com.nexters.rainbow.rainbowcouple.common.Response;

import java.util.List;

import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by soyoon on 2016. 2. 8..
 */
public interface BillApi {

    @GET("/rainbow/view_bills2")
    Observable<Response<List<Bill>>> viewBill(@Header("rs") String rs,
                                              @Query("groupSN") String groupSN,
                                              @Query("year") String year,
                                              @Query("month") String month,
                                              @Query("day") String day);

}
