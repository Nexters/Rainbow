package com.nexters.rainbow.rainbowcouple.bill;

import com.nexters.rainbow.rainbowcouple.bill.add.BillAddForm;
import com.nexters.rainbow.rainbowcouple.bill.list.BillCondition;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface BillApi {

    @GET("/rainbow/view_bills")
    Observable<List<Bill>> viewBills(@Header("token") String token,
                                     @Query("ownerType") OwnerType ownerType,
                                     @Query("year") String year);

    @GET("/rainbow/view_bills")
    Observable<List<Bill>> viewBillByMonth(@Header("token") String token,
                                           @Query("ownerType") OwnerType ownerType,
                                           @Query("year") String year,
                                           @Query("month") String month);

    @GET("/rainbow/view_bills")
    Observable<List<Bill>> viewBillByDay(@Header("token") String token,
                                         @Query("ownerType") OwnerType ownerType,
                                         @Query("year") String year,
                                         @Query("month") String month,
                                         @Query("day") String day);

    @GET("/rainbow/view_bills_range")
    @FormUrlEncoded
    Observable<List<Bill>> viewBillBetweenDays(@Header("token") String token,
                                               @FieldMap BillCondition condition);

    @POST("/rainbow/insert_bill")
    Observable<Bill> insertBill(@Header("token") String token,
                                @Body BillAddForm billAddForm);

}
