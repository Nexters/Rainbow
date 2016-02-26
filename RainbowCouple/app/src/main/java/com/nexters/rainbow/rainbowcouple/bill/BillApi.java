package com.nexters.rainbow.rainbowcouple.bill;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import rx.Observable;

public interface BillApi {

    @GET("/rainbow/view_bills")
    Observable<List<Bill>> viewBillByDay(@Header("token") String token,
                                         @Query("groupSN") String groupSN,
                                         @Query("year") String year,
                                         @Query("month") String month,
                                         @Query("day") String day);

    @GET("/rainbow/view_bills_month")
    Observable<List<Bill>> viewBillByMonth(@Header("token") String token,
                                           @Query("groupSN") String groupSN,
                                           @Query("year") String year,
                                           @Query("month") String month);

    @GET("/rainbow/view_bills_year")
    Observable<List<Bill>> viewBillByYear(@Header("token") String token,
                                          @Query("groupSN") String groupSN,
                                          @Query("year") String year);

}
