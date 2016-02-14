package com.nexters.rainbow.rainbowcouple.bill;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import rx.Observable;

public interface BillApi {

    @GET("/rainbow/view_bills2")
    Observable<List<Bill>> viewBill(@Header("token") String token,
                                              @Query("groupSN") String groupSN,
                                              @Query("year") String year,
                                              @Query("month") String month,
                                              @Query("day") String day);

}
