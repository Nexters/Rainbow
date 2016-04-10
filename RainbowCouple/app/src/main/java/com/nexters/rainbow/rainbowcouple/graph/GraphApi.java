package com.nexters.rainbow.rainbowcouple.graph;

import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.bill.OwnerType;
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

/**
 * Created by soyoon on 2016. 4. 9..
 */
public interface GraphApi {
    @GET("/rainbow/statistics_bills")
    Observable<List<BillStatics>> viewStaticsBills(@Header("token") String token,
                                     @Query("ownerType") OwnerType ownerType,
                                     @Query("year") String year);

    @GET("/rainbow/statistics_bills")
    Observable<List<BillStatics>> viewStaticsBillByMonth(@Header("token") String token,
                                           @Query("ownerType") OwnerType ownerType,
                                           @Query("year") String year,
                                           @Query("month") String month);


}
